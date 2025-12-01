package io.github.shkschneider.awesome.extras

import io.github.shkschneider.awesome.Awesome
import io.github.shkschneider.awesome.core.AwesomeRegistries
import io.github.shkschneider.awesome.core.AwesomeUtils
import io.github.shkschneider.awesome.core.ext.positions
import io.github.shkschneider.awesome.custom.Minecraft
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.block.AbstractPlantBlock
import net.minecraft.block.AbstractPlantStemBlock
import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.block.Fertilizable
import net.minecraft.block.GrassBlock
import net.minecraft.block.PlantBlock
import net.minecraft.block.ShapeContext
import net.minecraft.block.SugarCaneBlock
import net.minecraft.client.render.RenderLayer
import net.minecraft.entity.Entity
import net.minecraft.entity.vehicle.BoatEntity
import net.minecraft.fluid.FluidState
import net.minecraft.fluid.Fluids
import net.minecraft.item.BoneMealItem
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.server.world.ServerWorld
import net.minecraft.state.property.Properties
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Box
import net.minecraft.util.math.Direction
import net.minecraft.util.math.random.Random
import net.minecraft.util.shape.VoxelShape
import net.minecraft.util.shape.VoxelShapes
import net.minecraft.world.BlockView
import net.minecraft.world.World
import net.minecraft.world.WorldView

class LilyPad : AbstractPlantBlock(
    FabricBlockSettings.copyOf(Blocks.LILY_PAD).collidable(false).nonOpaque().ticksRandomly(),
    Direction.UP,
    VoxelShapes.fullCube(),
    true
) {

    init {
        val block = AwesomeRegistries.blockWithItem(AwesomeUtils.identifier("lily_pad"), this, Awesome.GROUP).block
        if (Minecraft.isClient) AwesomeRegistries.blockRenderer(block, RenderLayer.getCutout())
    }

    override fun getStem(): AbstractPlantStemBlock = (Blocks.KELP as AbstractPlantStemBlock) // :nothingtoseehere:

    override fun getPickStack(world: BlockView, pos: BlockPos, state: BlockState): ItemStack =
        ItemStack(this, 1)

    override fun getOutlineShape(state: BlockState, world: BlockView, pos: BlockPos, context: ShapeContext): VoxelShape =
        createCuboidShape(2.0, 0.0, 2.0, 14.0, 14.0, 14.0)

    override fun onEntityCollision(state: BlockState, world: World, pos: BlockPos, entity: Entity) {
        if (world is ServerWorld && entity is BoatEntity) {
            world.breakBlock(BlockPos(pos), true, entity)
        }
    }

    override fun canPlaceAt(state: BlockState, world: WorldView, pos: BlockPos): Boolean =
        world.getFluidState(pos).fluid == Fluids.WATER && world.getFluidState(pos.up()).fluid == Fluids.EMPTY

    override fun getFluidState(state: BlockState): FluidState = Fluids.WATER.getStill(false)

    override fun canAttachTo(state: BlockState): Boolean = false

    override fun randomTick(state: BlockState, world: ServerWorld, blockPos: BlockPos, random: Random) {
        if (world.isClient) return
        Box(blockPos).expand(1.0).positions().forEach { pos ->
            tick(world, world.getBlockState(pos), pos, random)
        }
    }

    @Suppress("DEPRECATION")
    private fun tick(world: ServerWorld, state: BlockState, pos: BlockPos, random: Random) {
        val repeats = listOf(
            Properties.AGE_1_MAX,
            Properties.AGE_2_MAX,
            Properties.AGE_3_MAX,
            Properties.AGE_5_MAX,
            Properties.AGE_7_MAX,
            Properties.AGE_15_MAX,
            Properties.AGE_25_MAX,
        ).random()
        when (state.block) {
            is LilyPad, is GrassBlock -> return
            // sugar_cane
            is SugarCaneBlock -> repeat(repeats) {
                (state.block as SugarCaneBlock).randomTick(world.getBlockState(pos), world, pos, random)
            }
            // saplings, wheat...
            is Fertilizable -> repeat(repeats) {
                BoneMealItem.useOnFertilizable(ItemStack(Items.BONE_MEAL, 1), world, pos)
            }
            // nether_wart...
            is PlantBlock -> {
                if (state.block.hasRandomTicks(state)) repeat(repeats) {
                    (state.block as PlantBlock).randomTick(world.getBlockState(pos), world, pos, random)
                }
            }
        }
    }

    override fun randomDisplayTick(state: BlockState, world: World, pos: BlockPos, random: Random) {
        BoneMealItem.createParticles(world, pos, 1)
    }

}

package io.github.shkschneider.awesome.extras

import io.github.shkschneider.awesome.core.AwesomeBlock
import io.github.shkschneider.awesome.core.AwesomeLogger
import io.github.shkschneider.awesome.core.AwesomeRegistries
import io.github.shkschneider.awesome.core.AwesomeSounds
import io.github.shkschneider.awesome.core.AwesomeUtils
import io.github.shkschneider.awesome.core.ext.copy
import io.github.shkschneider.awesome.custom.Minecraft
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.block.CaveVines
import net.minecraft.block.ShapeContext
import net.minecraft.client.item.TooltipContext
import net.minecraft.client.render.RenderLayer
import net.minecraft.entity.ItemEntity
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.ai.pathing.NavigationType
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.loot.context.LootContext
import net.minecraft.sound.BlockSoundGroup
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import net.minecraft.util.ActionResult
import net.minecraft.util.Formatting
import net.minecraft.util.Hand
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.math.MathHelper
import net.minecraft.util.shape.VoxelShape
import net.minecraft.world.BlockView
import net.minecraft.world.World
import net.minecraft.world.WorldView

class Rope : AwesomeBlock(
    id = AwesomeUtils.identifier("rope"),
    settings = FabricBlockSettings.copyOf(Blocks.VINE).sounds(BlockSoundGroup.VINE)
        .luminance(4)
        .noCollision()
        .breakInstantly(),
) {

    // FIXME while breaking, hits blocks behind

    init {
        if (Minecraft.isClient) AwesomeRegistries.blockRenderer(this, RenderLayer.getCutout())
    }

    override fun appendTooltip(stack: ItemStack, world: BlockView?, tooltip: MutableList<Text>, context: TooltipContext) {
        tooltip.add(TranslatableText(AwesomeUtils.translatable("block", id.path, "hint")).formatted(Formatting.GRAY))
    }

    override fun getOutlineShape(state: BlockState, world: BlockView, pos: BlockPos, context: ShapeContext): VoxelShape =
        CaveVines.SHAPE

    override fun isTranslucent(state: BlockState, world: BlockView, pos: BlockPos): Boolean = true

    override fun canPlaceAt(state: BlockState, world: WorldView, pos: BlockPos): Boolean =
        (world.getBlockState(pos.up()).isSideSolidFullSquare(world, pos, Direction.DOWN) && world.getBlockState(pos).isAir)
                || (world.getBlockState(pos.up()).block == this && world.getBlockState(pos).isAir)

    override fun canPathfindThrough(state: BlockState, world: BlockView, pos: BlockPos, type: NavigationType): Boolean = false

    override fun getDroppedStacks(state: BlockState?, builder: LootContext.Builder?): MutableList<ItemStack> = mutableListOf()

    override fun onUse(state: BlockState, world: World, pos: BlockPos, player: PlayerEntity, hand: Hand, hit: BlockHitResult): ActionResult {
        if (world.getBlockState(pos).block == this) {
            unroll(world, pos, player)
        }
        return ActionResult.success(world.isClient)
    }

    override fun onPlaced(world: World, pos: BlockPos, state: BlockState, placer: LivingEntity?, itemStack: ItemStack) {
        super.onPlaced(world, pos, state, placer, itemStack)
        if (world.isClient) return
        (placer as? PlayerEntity)?.let { player ->
            unroll(world, pos, player)
        }
    }

    private fun unroll(world: World, blockPos: BlockPos, player: PlayerEntity) {
        val stack = player.mainHandStack
        var pos = blockPos
        while (pos.y > world.bottomY) {
            pos = pos.down()
            if (world.getBlockState(pos).block == this) continue
            if (!world.getBlockState(pos).isAir || stack.isEmpty) break
            AwesomeLogger.debug("Rope: unrolling @ $pos")
            world.setBlockState(pos, this.defaultState)
            // 1.19 world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(player, this.defaultState))
            stack.count -= 1
            AwesomeSounds(world to pos, AwesomeSounds.vinesPlaced, volume = MathHelper.nextBetween(world.random, 0.8F, 1.2F))
        }
    }

    override fun onBreak(world: World, pos: BlockPos, state: BlockState, player: PlayerEntity) {
        if (world.isClient) return
        rollup(world, pos)
    }

    private fun rollup(world: World, blockPos: BlockPos) {
        val state = Blocks.AIR.defaultState
        val origin = blockPos.copy()
        var pos = blockPos
        while (pos.y > world.bottomY) {
            pos = pos.down()
            if (world.getBlockState(pos).block != this) break
            AwesomeLogger.debug("Rope: rollup $pos @ $origin")
            world.setBlockState(pos, state)
            // 1.19 world.emitGameEvent(GameEvent.BLOCK_DESTROY, pos, GameEvent.Emitter.of(state))
            drop(world, origin)
            AwesomeSounds(world to pos, AwesomeSounds.vinesBroken, volume = MathHelper.nextBetween(world.random, 0.8F, 1.2F))
        }
    }

    private fun drop(world: World, pos: BlockPos) {
        world.spawnEntity(ItemEntity(world, pos.x + 0.5, pos.y + 0.5, pos.z + 0.5, ItemStack(this.asItem(), 1)).apply {
            setToDefaultPickupDelay()
        })
    }

}

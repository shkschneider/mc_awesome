package io.github.shkschneider.awesome.core

import io.github.shkschneider.awesome.custom.Minecraft
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.block.ShapeContext
import net.minecraft.item.ItemPlacementContext
import net.minecraft.state.StateManager
import net.minecraft.state.property.Properties
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.shape.VoxelShape
import net.minecraft.world.BlockView
import net.minecraft.world.World

abstract class AwesomeDetector<BE : AwesomeBlockEntity>(
    id: String,
) : AwesomeBlockWithEntity<BE>(
    AwesomeUtils.identifier(id), FabricBlockSettings.copyOf(Blocks.DAYLIGHT_DETECTOR),
) {

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) {
        super.appendProperties(builder.add(Properties.POWER))
    }

    override fun getPlacementState(ctx: ItemPlacementContext): BlockState =
        super.getPlacementState(ctx).with(Properties.POWER, 0)

    override fun getOutlineShape(state: BlockState, world: BlockView, pos: BlockPos, context: ShapeContext): VoxelShape =
        createCuboidShape(0.0, 0.0, 0.0, 16.0, 6.0, 16.0) // net.minecraft.block.DaylightDetectorBlock.SHAPE

    override fun tick(world: World, pos: BlockPos, state: BlockState, blockEntity: BE) {
        if (world.time % Minecraft.TICKS != 0L) return
        world.setBlockState(pos, state.with(Properties.POWER, detect(world, pos, state, blockEntity)))
    }

    abstract fun detect(world: World, pos: BlockPos, state: BlockState, blockEntity: BE): Int

    override fun emitsRedstonePower(state: BlockState?): Boolean = true

    override fun getWeakRedstonePower(state: BlockState, world: BlockView, pos: BlockPos, direction: Direction): Int =
        state.get(Properties.POWER)

    abstract override fun createBlockEntity(pos: BlockPos, state: BlockState): BE

}

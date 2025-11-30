package io.github.shkschneider.awesome.machines

import io.github.shkschneider.awesome.Awesome
import io.github.shkschneider.awesome.core.AwesomeBlockEntity
import io.github.shkschneider.awesome.core.AwesomeBlockWithEntity
import io.github.shkschneider.awesome.core.AwesomeInputs
import io.github.shkschneider.awesome.core.AwesomeUtils
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.client.item.TooltipContext
import net.minecraft.item.ItemPlacementContext
import net.minecraft.item.ItemStack
import net.minecraft.state.StateManager
import net.minecraft.state.property.Properties
import net.minecraft.text.Text
import net.minecraft.util.BlockMirror
import net.minecraft.util.BlockRotation
import net.minecraft.util.math.BlockPos
import net.minecraft.world.BlockView
import net.minecraft.world.World

abstract class AwesomeMachineBlock<BE : AwesomeBlockEntity.WithInventory, SH : AwesomeMachineScreenHandler<BE>>(
    protected val machine: AwesomeMachine<BE, SH>,
    settings: FabricBlockSettings = FabricBlockSettings.copyOf(Blocks.FURNACE),
) : AwesomeBlockWithEntity.WithInventory<BE>(
    id = AwesomeUtils.identifier(machine.id),
    settings = settings,
    group = Awesome.GROUP,
) {

    override fun getPlacementState(ctx: ItemPlacementContext): BlockState = defaultState
        .with(Properties.HORIZONTAL_FACING, if (AwesomeInputs.alt()) ctx.playerFacing else ctx.playerFacing.opposite)
        .with(Properties.LIT, false)

    override fun rotate(state: BlockState, rotation: BlockRotation): BlockState =
        state.with(Properties.HORIZONTAL_FACING, rotation.rotate(state.get(Properties.HORIZONTAL_FACING)))

    override fun mirror(state: BlockState, mirror: BlockMirror): BlockState =
        state.rotate(mirror.getRotation(state.get(Properties.HORIZONTAL_FACING)))

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) {
        builder
            .add(Properties.HORIZONTAL_FACING)
            .add(Properties.LIT)
    }

    override fun appendTooltip(stack: ItemStack, world: BlockView?, tooltip: MutableList<Text>, options: TooltipContext) {
        tooltip.addAll(tooltips(stack))
    }

    abstract fun tooltips(stack: ItemStack): List<Text>

    override fun createBlockEntity(pos: BlockPos, state: BlockState): BE =
        blockEntity(machine, pos, state)

    abstract fun blockEntity(machine: AwesomeMachine<BE, SH>, pos: BlockPos, state: BlockState): BE

    override fun tick(world: World, pos: BlockPos, state: BlockState, blockEntity: BE) {
        if (world.isClient) return
        machine.tick(world, pos, state, blockEntity)
    }

}

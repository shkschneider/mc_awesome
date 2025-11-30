package io.github.shkschneider.awesome.machines.placer

import io.github.shkschneider.awesome.core.ext.getEnchantmentLevel
import io.github.shkschneider.awesome.custom.InputOutput
import io.github.shkschneider.awesome.custom.Minecraft
import io.github.shkschneider.awesome.machines.AwesomeMachine
import io.github.shkschneider.awesome.machines.AwesomeMachineBlock
import io.github.shkschneider.awesome.machines.AwesomeMachineScreen
import net.minecraft.block.BlockState
import net.minecraft.enchantment.Enchantments
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.item.BlockItem
import net.minecraft.server.world.ServerWorld
import net.minecraft.state.property.Properties
import net.minecraft.text.Text
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class Placer : AwesomeMachine<PlacerBlock.Entity, PlacerScreen.Handler>(
    id = "placer",
    io = InputOutput(inputs = 1, outputs = 1),
    properties = PROPERTIES + 1,
) {

    override fun block(): AwesomeMachineBlock<PlacerBlock.Entity, PlacerScreen.Handler> =
        PlacerBlock(this)

    override fun screenHandler(syncId: Int, playerInventory: PlayerInventory): PlacerScreen.Handler =
        PlacerScreen.Handler(this, null, syncId, playerInventory)

    override fun screen(handler: PlacerScreen.Handler, playerInventory: PlayerInventory, title: Text): AwesomeMachineScreen<PlacerBlock.Entity, PlacerScreen.Handler> =
        PlacerScreen(this, handler, playerInventory, title)

    override fun tick(world: World, pos: BlockPos, state: BlockState, blockEntity: PlacerBlock.Entity) {
        if (world.isClient) return
        if (blockEntity.getOutputSlot().second.isEmpty) { blockEntity.progress = 0 ; return }
        // ...
        blockEntity.progress++
        if (blockEntity.progress >= blockEntity.duration) {
            place(blockEntity, world as ServerWorld, state, pos)
        }
    }

    private fun place(blockEntity: PlacerBlock.Entity, world: ServerWorld, state: BlockState, pos: BlockPos) {
        val output = blockEntity.getOutputSlot().second
        (output.item as? BlockItem)?.let { blockItem ->
            val front = pos.offset(blockEntity.getPropertyState(state, Properties.HORIZONTAL_FACING))
            if (world.getBlockState(front).isAir) {
                world.setBlockState(front, blockItem.block.defaultState)
                // 1.19 world.emitGameEvent(GameEvent.BLOCK_PLACE, pos, GameEvent.Emitter.of(null, blockItem.block.defaultState))
                output.count -= 1
                blockEntity.markDirty()
            }
        }
        blockEntity.progress = 0
        blockEntity.efficiency = 1 + blockEntity.getEnchantmentSlot().second.getEnchantmentLevel(Enchantments.EFFICIENCY)
        blockEntity.duration = Minecraft.TICKS / blockEntity.efficiency
    }

}

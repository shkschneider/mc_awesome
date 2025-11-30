package io.github.shkschneider.awesome.machines.breaker

import io.github.shkschneider.awesome.core.ext.getEnchantmentLevel
import io.github.shkschneider.awesome.custom.InputOutput
import io.github.shkschneider.awesome.custom.Minecraft
import io.github.shkschneider.awesome.machines.AwesomeMachine
import io.github.shkschneider.awesome.machines.AwesomeMachineBlock
import io.github.shkschneider.awesome.machines.AwesomeMachineScreen
import net.minecraft.block.BlockState
import net.minecraft.enchantment.Enchantments
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.server.world.ServerWorld
import net.minecraft.state.property.Properties
import net.minecraft.text.Text
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class Breaker : AwesomeMachine<BreakerBlock.Entity, BreakerScreen.Handler>(
    id = "breaker",
    io = InputOutput(inputs = 1),
    properties = PROPERTIES + 1,
) {

    override fun block(): AwesomeMachineBlock<BreakerBlock.Entity, BreakerScreen.Handler> =
        BreakerBlock(this)

    override fun screenHandler(syncId: Int, playerInventory: PlayerInventory): BreakerScreen.Handler =
        BreakerScreen.Handler(this, null, syncId, playerInventory)

    override fun screen(handler: BreakerScreen.Handler, playerInventory: PlayerInventory, title: Text): AwesomeMachineScreen<BreakerBlock.Entity, BreakerScreen.Handler> =
        BreakerScreen(this, handler, playerInventory, title)

    override fun tick(world: World, pos: BlockPos, state: BlockState, blockEntity: BreakerBlock.Entity) {
        if (world.isClient) return
        // ...
        blockEntity.progress++
        if (blockEntity.progress >= blockEntity.duration) {
            place(blockEntity, world as ServerWorld, state, pos)
        }
    }

    private fun place(blockEntity: BreakerBlock.Entity, world: ServerWorld, state: BlockState, pos: BlockPos) {
        val front = pos.offset(blockEntity.getPropertyState(state, Properties.HORIZONTAL_FACING))
        if (!world.getBlockState(front).isAir) {
            world.breakBlock(front, true)
        }
        blockEntity.progress = 0
        blockEntity.efficiency = 1 + blockEntity.getEnchantmentSlot().second.getEnchantmentLevel(Enchantments.EFFICIENCY)
        blockEntity.duration = Minecraft.TICKS / blockEntity.efficiency
    }

}

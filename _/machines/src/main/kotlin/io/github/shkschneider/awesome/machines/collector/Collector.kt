package io.github.shkschneider.awesome.machines.collector

import com.google.common.base.Predicates
import io.github.shkschneider.awesome.core.ext.getEnchantmentLevel
import io.github.shkschneider.awesome.core.ext.getStacks
import io.github.shkschneider.awesome.custom.InputOutput
import io.github.shkschneider.awesome.machines.AwesomeMachine
import io.github.shkschneider.awesome.machines.AwesomeMachineBlock
import io.github.shkschneider.awesome.machines.AwesomeMachineScreen
import net.minecraft.block.BlockState
import net.minecraft.enchantment.Enchantments
import net.minecraft.entity.ItemEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.text.Text
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Box
import net.minecraft.world.World

class Collector : AwesomeMachine<CollectorBlock.Entity, CollectorScreen.Handler>(
    id = "collector",
    io = InputOutput(inputs = 1, outputs = 9),
    properties = PROPERTIES + 1,
) {

    override fun block(): AwesomeMachineBlock<CollectorBlock.Entity, CollectorScreen.Handler> =
        CollectorBlock(this)

    override fun screenHandler(syncId: Int, playerInventory: PlayerInventory): CollectorScreen.Handler =
        CollectorScreen.Handler(this, null, syncId, playerInventory)

    override fun screen(handler: CollectorScreen.Handler, playerInventory: PlayerInventory, title: Text): AwesomeMachineScreen<CollectorBlock.Entity, CollectorScreen.Handler> =
        CollectorScreen(this, handler, playerInventory, title)

    override fun tick(world: World, pos: BlockPos, state: BlockState, blockEntity: CollectorBlock.Entity) {
        if (world.isClient) return
        if (blockEntity.getStacks().all { it.isEmpty }) blockEntity.off() else blockEntity.on()
        blockEntity.efficiency = 1 + blockEntity.getEnchantmentSlot().second.getEnchantmentLevel(Enchantments.EFFICIENCY)
        val box = Box(pos).expand(blockEntity.efficiency.toDouble())
        world.getEntitiesByClass(ItemEntity::class.java, box, Predicates.alwaysTrue())
            .stream().map { it as ItemEntity }.filter { it.isOnGround }.forEach { itemEntity ->
                if (blockEntity.insert(itemEntity.stack).isEmpty) {
                    itemEntity.discard()
                }
                blockEntity.markDirty()
            }
    }

}

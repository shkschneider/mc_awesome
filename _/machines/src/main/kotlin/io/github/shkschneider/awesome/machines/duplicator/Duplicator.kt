package io.github.shkschneider.awesome.machines.duplicator

import com.google.common.base.Predicates
import io.github.shkschneider.awesome.AwesomeMachines
import io.github.shkschneider.awesome.core.ext.getEnchantmentLevel
import io.github.shkschneider.awesome.core.ext.toBox
import io.github.shkschneider.awesome.custom.InputOutput
import io.github.shkschneider.awesome.custom.Minecraft
import io.github.shkschneider.awesome.items.Imprisoner
import io.github.shkschneider.awesome.machines.AwesomeMachine
import io.github.shkschneider.awesome.machines.AwesomeMachineBlock
import io.github.shkschneider.awesome.machines.AwesomeMachineScreen
import net.minecraft.block.BlockState
import net.minecraft.enchantment.Enchantments
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.server.world.ServerWorld
import net.minecraft.text.Text
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class Duplicator : AwesomeMachine<DuplicatorBlock.Entity, DuplicatorScreen.Handler>(
    id = "duplicator",
    io = InputOutput(inputs = 3),
    properties = PROPERTIES + 1,
) {

    override fun block(): AwesomeMachineBlock<DuplicatorBlock.Entity, DuplicatorScreen.Handler> =
        DuplicatorBlock(this)

    override fun screenHandler(syncId: Int, playerInventory: PlayerInventory): DuplicatorScreen.Handler =
        DuplicatorScreen.Handler(this, null, syncId, playerInventory)

    override fun screen(handler: DuplicatorScreen.Handler, playerInventory: PlayerInventory, title: Text): AwesomeMachineScreen<DuplicatorBlock.Entity, DuplicatorScreen.Handler> =
        DuplicatorScreen(this, handler, playerInventory, title)

    override fun tick(world: World, pos: BlockPos, state: BlockState, blockEntity: DuplicatorBlock.Entity) {
        if (world.isClient) return
        blockEntity.efficiency = 1 + blockEntity.getEnchantmentSlot().second.getEnchantmentLevel(Enchantments.EFFICIENCY)
        if (blockEntity.fuel == 0) {
            if (blockEntity.getFuelSlot().second.count > 0) {
                blockEntity.getFuelSlot().second.count--
                blockEntity.fuel += AwesomeMachines.fuel.time
                blockEntity.on()
            } else {
                blockEntity.off()
                return
            }
        }
        if (!blockEntity.getInputSlot().second.isEmpty) {
            if (blockEntity.fuel == 0) {
                if (blockEntity.getFuelSlot().second.count > 0) {
                    blockEntity.getFuelSlot().second.count--
                    blockEntity.fuel += AwesomeMachines.fuel.time
                    blockEntity.on()
                } else {
                    blockEntity.off()
                    return
                }
            }
            if (blockEntity.duration > 0) blockEntity.progress++
            if (blockEntity.duration == 0) {
                blockEntity.duration = Minecraft.TICKS
                blockEntity.progress = 0
            } else if (blockEntity.progress >= blockEntity.duration) {
                spawn(blockEntity, world as ServerWorld, pos.up(blockEntity.efficiency))
            }
        }
        if (blockEntity.fuel == 0) blockEntity.off()
    }

    private fun spawn(blockEntity: DuplicatorBlock.Entity, world: ServerWorld, pos: BlockPos) {
        if (world.getEntitiesByClass(LivingEntity::class.java, pos.toBox(radius = 1.0), Predicates.alwaysTrue()).isEmpty()) {
            (blockEntity.getInputSlot().second.item as? Imprisoner)
                ?.spawn(world, pos, blockEntity.getInputSlot().second.nbt)
        }
        blockEntity.progress = 0
        blockEntity.efficiency = 1 + blockEntity.getEnchantmentSlot().second.getEnchantmentLevel(Enchantments.EFFICIENCY)
        blockEntity.duration = Minecraft.TICKS
    }

}

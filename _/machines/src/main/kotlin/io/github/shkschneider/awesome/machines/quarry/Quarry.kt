package io.github.shkschneider.awesome.machines.quarry

import io.github.shkschneider.awesome.AwesomeMachines
import io.github.shkschneider.awesome.core.ext.getEnchantmentLevel
import io.github.shkschneider.awesome.core.ext.isFull
import io.github.shkschneider.awesome.custom.InputOutput
import io.github.shkschneider.awesome.custom.Minecraft
import io.github.shkschneider.awesome.machines.AwesomeMachine
import io.github.shkschneider.awesome.machines.AwesomeMachineBlock
import io.github.shkschneider.awesome.machines.AwesomeMachineScreen
import net.minecraft.block.BlockState
import net.minecraft.enchantment.Enchantments
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.item.ItemStack
import net.minecraft.text.Text
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import java.util.Random

class Quarry : AwesomeMachine<QuarryBlock.Entity, QuarryScreen.Handler>(
    id = "quarry",
    io = InputOutput(inputs = 2, outputs = 1),
    properties = PROPERTIES + 2,
) {

    val recipes = QuarryRecipes()

    override fun block(): AwesomeMachineBlock<QuarryBlock.Entity, QuarryScreen.Handler> =
        QuarryBlock(this)

    override fun screenHandler(syncId: Int, playerInventory: PlayerInventory): QuarryScreen.Handler =
        QuarryScreen.Handler(this, null, syncId, playerInventory)

    override fun screen(handler: QuarryScreen.Handler, playerInventory: PlayerInventory, title: Text): AwesomeMachineScreen<QuarryBlock.Entity, QuarryScreen.Handler> =
        QuarryScreen(this, handler, playerInventory, title)

    override fun tick(world: World, pos: BlockPos, state: BlockState, blockEntity: QuarryBlock.Entity) {
        if (world.isClient) return
        blockEntity.efficiency = 1 + blockEntity.getEnchantmentSlot().second.getEnchantmentLevel(Enchantments.EFFICIENCY)
        blockEntity.fortune = 1 + blockEntity.getEnchantmentSlot().second.getEnchantmentLevel(Enchantments.FORTUNE)
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
        if (!blockEntity.getOutputSlot().second.isFull) {
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
                blockEntity.duration = Minecraft.TICKS / blockEntity.efficiency
                blockEntity.progress = 0
            } else if (blockEntity.progress >= blockEntity.duration) {
                quarry(blockEntity, world.random)
            }
        }
        if (blockEntity.fuel == 0) blockEntity.off()
    }

    private fun quarry(blockEntity: QuarryBlock.Entity, random: Random) {
        val ores = recipes.map { it.output }.map { it.item to it.count }
        val r = random.nextInt(ores.sumOf { it.second })
        var v = 0
        ores.forEach { ore ->
            v += ore.second
            if (v >= r) {
                blockEntity.progress = 0
                blockEntity.insert(ItemStack(ore.first, blockEntity.fortune))
                blockEntity.duration = Minecraft.TICKS / blockEntity.efficiency
                return@forEach
            }
        }
    }

}

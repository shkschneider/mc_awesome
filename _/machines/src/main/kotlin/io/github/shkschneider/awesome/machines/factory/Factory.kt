package io.github.shkschneider.awesome.machines.factory

import io.github.shkschneider.awesome.AwesomeMachines
import io.github.shkschneider.awesome.core.ext.canInsert
import io.github.shkschneider.awesome.core.ext.copy
import io.github.shkschneider.awesome.core.ext.getEnchantmentLevel
import io.github.shkschneider.awesome.custom.InputOutput
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

class Factory : AwesomeMachine<FactoryBlock.Entity, FactoryScreen.Handler>(
    id = "factory",
    io = InputOutput(inputs = 5, outputs = 1),
    properties = PROPERTIES + 3,
) {

    val recipes = FactoryRecipes()

    override fun block(): AwesomeMachineBlock<FactoryBlock.Entity, FactoryScreen.Handler> =
        FactoryBlock(this)

    override fun screenHandler(syncId: Int, playerInventory: PlayerInventory): FactoryScreen.Handler =
        FactoryScreen.Handler(this, null, syncId, playerInventory)

    override fun screen(handler: FactoryScreen.Handler, playerInventory: PlayerInventory, title: Text): AwesomeMachineScreen<FactoryBlock.Entity, FactoryScreen.Handler> =
        FactoryScreen(this, handler, playerInventory, title)

    override fun tick(world: World, pos: BlockPos, state: BlockState, blockEntity: FactoryBlock.Entity) {
        if (world.isClient) return
        blockEntity.efficiency = 1 + blockEntity.getEnchantmentSlot().second.getEnchantmentLevel(Enchantments.EFFICIENCY)
        // TODO blockEntity.fortune = 1 + blockEntity.getEnchantmentSlot().second.getEnchantmentLevel(Enchantments.FORTUNE)
        if (blockEntity.fuel > 0) { blockEntity.fuel-- ; blockEntity.on() }
        if (!blockEntity.getInputSlots()[2].second.isEmpty) factory(blockEntity, blockEntity.getInputSlots()[2], blockEntity.getOutputSlot())
        else if (!blockEntity.getInputSlots()[1].second.isEmpty) factory(blockEntity, blockEntity.getInputSlots()[1], blockEntity.getInputSlots()[2])
        else if (!blockEntity.getInputSlots()[0].second.isEmpty) factory(blockEntity, blockEntity.getInputSlots()[0], blockEntity.getInputSlots()[1])
        if (blockEntity.fuel == 0) blockEntity.off()
    }

    private fun factory(blockEntity: FactoryBlock.Entity, inputSlot: Pair<Int, ItemStack>, outputSlot: Pair<Int, ItemStack>) {
        val recipe = recipes.firstOrNull { recipe ->
            recipe.inputs.all { input ->
                inputSlot.second.item == input.item && inputSlot.second.count >= input.count
            }
        }?.takeIf { recipe -> outputSlot.second.canInsert(recipe.output) }
        if (recipe != null) {
            if (blockEntity.fuel == 0) {
                if (blockEntity.getFuelSlot().second.count > 0) {
                    blockEntity.getFuelSlot().second.count--
                    blockEntity.fuel += AwesomeMachines.fuel.time
                } else {
                    blockEntity.off()
                    return
                }
            }
            if (blockEntity.duration > 0) blockEntity.progress++
            if (blockEntity.duration == 0) {
                blockEntity.duration = recipe.time / blockEntity.efficiency
                blockEntity.progress = 0
            } else if (blockEntity.progress >= blockEntity.duration) {
                val output = recipe.output.copy(amount = blockEntity.fortune)
                // input
                inputSlot.second.count -= recipe.inputs.first().count
                // output
                if (outputSlot.second.isEmpty) blockEntity.setStack(outputSlot.first, output)
                else outputSlot.second.count += output.count
                // reset
                blockEntity.duration = recipe.time / blockEntity.efficiency
                blockEntity.progress = 0
                blockEntity.markDirty()
            }
        }
    }

}

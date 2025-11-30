package io.github.shkschneider.awesome.machines.cultivator

import io.github.shkschneider.awesome.AwesomeMachines
import io.github.shkschneider.awesome.core.AwesomeRecipes
import io.github.shkschneider.awesome.core.ext.copy
import io.github.shkschneider.awesome.core.ext.getEnchantmentLevel
import io.github.shkschneider.awesome.custom.InputOutput
import io.github.shkschneider.awesome.machines.AwesomeMachine
import io.github.shkschneider.awesome.machines.AwesomeMachineBlock
import io.github.shkschneider.awesome.machines.AwesomeMachineScreen
import net.minecraft.block.BlockState
import net.minecraft.enchantment.Enchantments
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.text.Text
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class Cultivator : AwesomeMachine<CultivatorBlock.Entity, CultivatorScreen.Handler>(
    id = "cultivator",
    io = InputOutput(inputs = 3, outputs = 2),
    properties = PROPERTIES + 2,
) {

    val recipes = CultivatorRecipes()

    override fun block(): AwesomeMachineBlock<CultivatorBlock.Entity, CultivatorScreen.Handler> =
        CultivatorBlock(this)

    override fun screenHandler(syncId: Int, playerInventory: PlayerInventory): CultivatorScreen.Handler =
        CultivatorScreen.Handler(this, null, syncId, playerInventory)

    override fun screen(handler: CultivatorScreen.Handler, playerInventory: PlayerInventory, title: Text): AwesomeMachineScreen<CultivatorBlock.Entity, CultivatorScreen.Handler> =
        CultivatorScreen(this, handler, playerInventory, title)

    override fun tick(world: World, pos: BlockPos, state: BlockState, blockEntity: CultivatorBlock.Entity) {
        if (world.isClient) return
        blockEntity.efficiency = 1 + blockEntity.getEnchantmentSlot().second.getEnchantmentLevel(Enchantments.EFFICIENCY)
        blockEntity.fortune = 1 + blockEntity.getEnchantmentSlot().second.getEnchantmentLevel(Enchantments.FORTUNE)
        if (blockEntity.fuel > 0) { blockEntity.fuel-- ; blockEntity.on() }
        val recipe = AwesomeRecipes.get(recipes, listOf(blockEntity.getInputSlot()).map { it.second }, blockEntity.getOutputSlots().first().second)
        if (recipe != null) {
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
                blockEntity.duration = recipe.time / blockEntity.efficiency
                blockEntity.progress = 0
            } else if (blockEntity.progress >= blockEntity.duration) {
                val output = recipe.output.copy(amount = blockEntity.fortune)
                val output2 = blockEntity.getInputSlot().second.copy(
                    amount = if (CultivatorRecipes.shouldGiveSecondaryDrop(world.random)) 1 else 0,
                )
                // input
                blockEntity.getInputSlot().second.count -= recipe.inputs.first().count
                // output
                if (blockEntity.getOutputSlots().first().second.isEmpty) blockEntity.setStack(blockEntity.getOutputSlots().first().first, output)
                else blockEntity.getOutputSlots().first().second.count += output.count
                if (!output2.isEmpty) {
                    if (blockEntity.getOutputSlots().last().second.isEmpty) blockEntity.setStack(blockEntity.getOutputSlots().last().first, output2)
                    else blockEntity.getOutputSlots().last().second.count += output.count
                }
                // reset
                blockEntity.duration = recipe.time / blockEntity.efficiency
                blockEntity.progress = 0
                blockEntity.markDirty()
            }
        } else blockEntity.off()
        if (blockEntity.fuel == 0) blockEntity.off()
    }

}

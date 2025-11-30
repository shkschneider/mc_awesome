package io.github.shkschneider.awesome.machines.refinery

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

class Refinery : AwesomeMachine<RefineryBlock.Entity, RefineryScreen.Handler>(
    id = "refinery",
    io = InputOutput(inputs = 3, outputs = 1),
    properties = PROPERTIES + 3,
) {

    val recipes = RefineryRecipes()

    override fun block(): AwesomeMachineBlock<RefineryBlock.Entity, RefineryScreen.Handler> =
        RefineryBlock(this)

    override fun screenHandler(syncId: Int, playerInventory: PlayerInventory): RefineryScreen.Handler =
        RefineryScreen.Handler(this, null, syncId, playerInventory)

    override fun screen(handler: RefineryScreen.Handler, playerInventory: PlayerInventory, title: Text): AwesomeMachineScreen<RefineryBlock.Entity, RefineryScreen.Handler> =
        RefineryScreen(this, handler, playerInventory, title)

    override fun tick(world: World, pos: BlockPos, state: BlockState, blockEntity: RefineryBlock.Entity) {
        if (world.isClient) return
        blockEntity.efficiency = 1 + blockEntity.getEnchantmentSlot().second.getEnchantmentLevel(Enchantments.EFFICIENCY)
        blockEntity.fortune = 1 + blockEntity.getEnchantmentSlot().second.getEnchantmentLevel(Enchantments.FORTUNE)
        if (blockEntity.fuel > 0) { blockEntity.fuel-- ; blockEntity.on() }
        val recipe = AwesomeRecipes.get(recipes, listOf(blockEntity.getInputSlot()).map { it.second }, blockEntity.getOutputSlot().second)
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
                // input
                blockEntity.getInputSlot().second.count -= recipe.inputs.first().count
                // output
                if (blockEntity.getOutputSlot().second.isEmpty) blockEntity.setStack(blockEntity.getOutputSlot().first, output)
                else blockEntity.getOutputSlot().second.count += output.count
                // reset
                blockEntity.duration = recipe.time / blockEntity.efficiency
                blockEntity.progress = 0
                blockEntity.markDirty()
            }
        } else blockEntity.off()
        if (blockEntity.fuel == 0) blockEntity.off()
    }

}

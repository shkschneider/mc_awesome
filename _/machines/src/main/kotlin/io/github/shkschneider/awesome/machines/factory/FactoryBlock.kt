package io.github.shkschneider.awesome.machines.factory

import io.github.shkschneider.awesome.core.AwesomeUtils
import io.github.shkschneider.awesome.machines.AwesomeMachine
import io.github.shkschneider.awesome.machines.AwesomeMachineBlock
import io.github.shkschneider.awesome.machines.AwesomeMachineBlockEntity
import net.minecraft.block.BlockState
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.SidedInventory
import net.minecraft.item.ItemStack
import net.minecraft.screen.PropertyDelegate
import net.minecraft.screen.ScreenHandler
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import net.minecraft.util.Formatting
import net.minecraft.util.math.BlockPos

@Suppress("RemoveRedundantQualifierName")
class FactoryBlock(
    machine: AwesomeMachine<FactoryBlock.Entity, FactoryScreen.Handler>
) : AwesomeMachineBlock<FactoryBlock.Entity, FactoryScreen.Handler>(machine) {

    override fun tooltips(stack: ItemStack): List<Text> = listOf(
        TranslatableText(AwesomeUtils.translatable("block", machine.id, "hint")).formatted(Formatting.GRAY),
    )

    override fun blockEntity(machine: AwesomeMachine<FactoryBlock.Entity, FactoryScreen.Handler>, pos: BlockPos, state: BlockState): FactoryBlock.Entity =
        FactoryBlock.Entity(machine, pos, state)

    class Entity(
        machine: AwesomeMachine<FactoryBlock.Entity, FactoryScreen.Handler>,
        pos: BlockPos,
        state: BlockState,
    ) : AwesomeMachineBlockEntity<FactoryBlock.Entity, FactoryScreen.Handler>(
        machine, pos, state,
    ) {

        var efficiency: Int
            get() = getCustomProperty(0)
            set(value) = setCustomProperty(0, value)
        var fortune: Int
            get() = getCustomProperty(1)
            set(value) = setCustomProperty(1, value)

        init {
            efficiency = 1
            fortune = 1
        }

        fun getEnchantmentSlot(): Pair<Int, ItemStack> = getSlot(0)

        fun getInputSlots(): List<Pair<Int, ItemStack>> = listOf(
            getSlot(1), getSlot(2), getSlot(3),
        )

        fun getFuelSlot(): Pair<Int, ItemStack> = getSlot(4)

        fun getOutputSlot(): Pair<Int, ItemStack> = getSlot(5)

        override fun screenHandler(syncId: Int, playerInventory: PlayerInventory, sidedInventory: SidedInventory, properties: PropertyDelegate): ScreenHandler =
            FactoryScreen.Handler(machine, machine.screen, syncId, playerInventory, sidedInventory, properties)

    }

}

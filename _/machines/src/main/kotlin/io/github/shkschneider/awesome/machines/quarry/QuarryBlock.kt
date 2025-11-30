package io.github.shkschneider.awesome.machines.quarry

import io.github.shkschneider.awesome.AwesomeMachines
import io.github.shkschneider.awesome.core.AwesomeUtils
import io.github.shkschneider.awesome.custom.Minecraft
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
import net.minecraft.util.math.Direction

@Suppress("RemoveRedundantQualifierName")
class QuarryBlock(
    machine: AwesomeMachine<QuarryBlock.Entity, QuarryScreen.Handler>
) : AwesomeMachineBlock<QuarryBlock.Entity, QuarryScreen.Handler>(machine) {

    override fun tooltips(stack: ItemStack): List<Text> = listOf(
        TranslatableText(AwesomeUtils.translatable("block", machine.id, "hint")).formatted(Formatting.GRAY),
    )

    override fun blockEntity(machine: AwesomeMachine<QuarryBlock.Entity, QuarryScreen.Handler>, pos: BlockPos, state: BlockState): QuarryBlock.Entity =
        Entity(machine, pos, state)

    class Entity(
        machine: AwesomeMachine<QuarryBlock.Entity, QuarryScreen.Handler>,
        pos: BlockPos,
        state: BlockState,
    ) : AwesomeMachineBlockEntity<QuarryBlock.Entity, QuarryScreen.Handler>(
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
            duration = Minecraft.TICKS / efficiency
        }

        fun getEnchantmentSlot(): Pair<Int, ItemStack> = getSlot(0)

        fun getFuelSlot(): Pair<Int, ItemStack> = getSlot(1)

        fun getOutputSlot(): Pair<Int, ItemStack> = getSlot(2)

        override fun canInsert(slot: Int, stack: ItemStack, dir: Direction?): Boolean {
            return if (stack.item == AwesomeMachines.fuel) {
                slot == io.inputs - 1
            } else {
                super.canInsert(slot, stack, dir)
            }
        }

        override fun screenHandler(syncId: Int, playerInventory: PlayerInventory, sidedInventory: SidedInventory, properties: PropertyDelegate): ScreenHandler =
            QuarryScreen.Handler(machine, machine.screen, syncId, playerInventory, sidedInventory, properties)

    }

}

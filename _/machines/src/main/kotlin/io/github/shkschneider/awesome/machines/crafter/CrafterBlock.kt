package io.github.shkschneider.awesome.machines.crafter

import io.github.shkschneider.awesome.core.AwesomeBlockEntity
import io.github.shkschneider.awesome.core.AwesomeUtils
import io.github.shkschneider.awesome.machines.AwesomeMachine
import io.github.shkschneider.awesome.machines.AwesomeMachineBlock
import io.github.shkschneider.awesome.machines.AwesomeMachineBlockEntity
import net.minecraft.block.BlockState
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.SidedInventory
import net.minecraft.item.ItemStack
import net.minecraft.loot.context.LootContext
import net.minecraft.loot.context.LootContextParameters
import net.minecraft.screen.PropertyDelegate
import net.minecraft.screen.ScreenHandler
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import net.minecraft.util.Formatting
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction

@Suppress("RemoveRedundantQualifierName")
class CrafterBlock(
    machine: AwesomeMachine<CrafterBlock.Entity, CrafterScreen.Handler>
) : AwesomeMachineBlock<CrafterBlock.Entity, CrafterScreen.Handler>(machine) {

    override fun tooltips(stack: ItemStack): List<Text> = listOf(
        TranslatableText(AwesomeUtils.translatable("block", machine.id, "hint")).formatted(Formatting.GRAY),
    )

    override fun blockEntity(machine: AwesomeMachine<CrafterBlock.Entity, CrafterScreen.Handler>, pos: BlockPos, state: BlockState): CrafterBlock.Entity =
        CrafterBlock.Entity(machine, pos, state)

    override fun getDroppedStacks(state: BlockState, builder: LootContext.Builder): MutableList<ItemStack> = mutableListOf(
        (builder.get(LootContextParameters.BLOCK_ENTITY) as? AwesomeBlockEntity.WithInventory)?.items?.last() ?: ItemStack.EMPTY
    )

    class Entity(
        machine: AwesomeMachine<CrafterBlock.Entity, CrafterScreen.Handler>,
        pos: BlockPos,
        state: BlockState,
    ) : AwesomeMachineBlockEntity<CrafterBlock.Entity, CrafterScreen.Handler>(
        machine, pos, state,
    ) {

        fun getInputSlots(): List<Pair<Int, ItemStack>> = listOf(
            getSlot(0), getSlot(1), getSlot(2),
            getSlot(3), getSlot(4), getSlot(5),
            getSlot(6), getSlot(7), getSlot(8),
        )

        fun getOutputSlot(): Pair<Int, ItemStack> = getSlot(9)

        override fun canInsert(slot: Int, stack: ItemStack, dir: Direction?): Boolean =
            slot in getInputSlots().map { it.first } && getStack(slot).item == stack.item && getStack(slot).count == 1

        override fun canExtract(slot: Int, stack: ItemStack, dir: Direction?): Boolean =
            slot == getOutputSlot().first

        override fun screenHandler(syncId: Int, playerInventory: PlayerInventory, sidedInventory: SidedInventory, properties: PropertyDelegate): ScreenHandler =
            CrafterScreen.Handler(machine, machine.screen, syncId, playerInventory, sidedInventory, properties)

    }

}

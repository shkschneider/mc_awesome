package io.github.shkschneider.awesome.machines.collector

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
class CollectorBlock(
    machine: AwesomeMachine<CollectorBlock.Entity, CollectorScreen.Handler>
) : AwesomeMachineBlock<CollectorBlock.Entity, CollectorScreen.Handler>(machine) {

    override fun tooltips(stack: ItemStack): List<Text> = listOf(
        TranslatableText(AwesomeUtils.translatable("block", machine.id, "hint")).formatted(Formatting.GRAY),
    )

    override fun blockEntity(machine: AwesomeMachine<CollectorBlock.Entity, CollectorScreen.Handler>, pos: BlockPos, state: BlockState): CollectorBlock.Entity =
        Entity(machine, pos, state)

    class Entity(
        machine: AwesomeMachine<CollectorBlock.Entity, CollectorScreen.Handler>,
        pos: BlockPos,
        state: BlockState,
    ) : AwesomeMachineBlockEntity<CollectorBlock.Entity, CollectorScreen.Handler>(
        machine, pos, state,
    ) {

        var efficiency: Int
            get() = getCustomProperty(0)
            set(value) = setCustomProperty(0, value)

        init {
            efficiency = 1
        }

        fun getEnchantmentSlot(): Pair<Int, ItemStack> = getSlot(0)

        override fun screenHandler(syncId: Int, playerInventory: PlayerInventory, sidedInventory: SidedInventory, properties: PropertyDelegate): ScreenHandler =
            CollectorScreen.Handler(machine, machine.screen, syncId, playerInventory, sidedInventory, properties)

    }

}

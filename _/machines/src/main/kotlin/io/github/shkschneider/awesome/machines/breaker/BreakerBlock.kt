package io.github.shkschneider.awesome.machines.breaker

import io.github.shkschneider.awesome.core.AwesomeUtils
import io.github.shkschneider.awesome.custom.Minecraft
import io.github.shkschneider.awesome.machines.AwesomeMachine
import io.github.shkschneider.awesome.machines.AwesomeMachineBlock
import io.github.shkschneider.awesome.machines.AwesomeMachineBlockEntity
import net.minecraft.block.BlockState
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.SidedInventory
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.screen.PropertyDelegate
import net.minecraft.screen.ScreenHandler
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import net.minecraft.util.Formatting
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction

@Suppress("RemoveRedundantQualifierName")
class BreakerBlock(
    machine: AwesomeMachine<BreakerBlock.Entity, BreakerScreen.Handler>
) : AwesomeMachineBlock<BreakerBlock.Entity, BreakerScreen.Handler>(machine) {

    override fun tooltips(stack: ItemStack): List<Text> = listOf(
        TranslatableText(AwesomeUtils.translatable("block", machine.id, "hint")).formatted(Formatting.GRAY),
    )

    override fun blockEntity(machine: AwesomeMachine<BreakerBlock.Entity, BreakerScreen.Handler>, pos: BlockPos, state: BlockState): BreakerBlock.Entity =
        Entity(machine, pos, state)

    class Entity(
        machine: AwesomeMachine<BreakerBlock.Entity, BreakerScreen.Handler>,
        pos: BlockPos,
        state: BlockState,
    ) : AwesomeMachineBlockEntity<BreakerBlock.Entity, BreakerScreen.Handler>(
        machine, pos, state,
    ) {

        var efficiency: Int
            get() = getCustomProperty(0)
            set(value) = setCustomProperty(0, value)

        init {
            efficiency = 1
            duration = Minecraft.TICKS / efficiency
        }

        fun getEnchantmentSlot(): Pair<Int, ItemStack> =
            getSlot(0)

        override fun canInsert(slot: Int, stack: ItemStack, dir: Direction?): Boolean =
            (stack.item == Items.ENCHANTED_BOOK && slot == 0) || slot > 0

        override fun screenHandler(syncId: Int, playerInventory: PlayerInventory, sidedInventory: SidedInventory, properties: PropertyDelegate): ScreenHandler =
            BreakerScreen.Handler(machine, machine.screen, syncId, playerInventory, sidedInventory, properties)

    }

}

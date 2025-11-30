package io.github.shkschneider.awesome.custom

import io.github.shkschneider.awesome.core.ext.canInsert
import io.github.shkschneider.awesome.core.ext.copy
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.inventory.Inventory
import net.minecraft.item.ItemStack
import net.minecraft.screen.slot.Slot

class TemplateSlot(inventory: Inventory, index: Int, x: Int, y: Int) : Slot(inventory, index, x, y) {

    override fun isEnabled(): Boolean = true

    override fun canTakeItems(playerEntity: PlayerEntity): Boolean = false

    override fun canTakePartial(player: PlayerEntity): Boolean = false

    companion object {

        fun onSlotClick(slots: List<Slot>, slotIndex: Int, inventory: Inventory, cursorStack: ItemStack): ItemStack? {
            if (slotIndex in (0 until inventory.size() - 1)) {
                val slot = slots[slotIndex] // beware of negative indexes
                if (cursorStack.isEmpty) {
                    if (slot.stack.count > 1) {
                        slot.stack.decrement(1)
                        return slot.stack.copy(amount = slot.stack.count - 1)
                    } else {
                        slot.stack = ItemStack.EMPTY
                    }
                } else {
                    if (slot.stack.count > 1) {
                        if (cursorStack.canInsert(slot.stack.copy(amount = 1))) {
                            cursorStack.increment(1)
                            slot.stack.decrement(1)
                        } else {
                            // nothing
                        }
                    } else {
                        slot.stack = cursorStack.copy(amount = 1)
                    }
                }
                return cursorStack
            } else {
                return null
            }
        }

    }

}

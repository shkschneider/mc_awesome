package io.github.shkschneider.awesome.custom

import net.minecraft.inventory.Inventory
import net.minecraft.item.ItemConvertible
import net.minecraft.item.ItemStack
import net.minecraft.screen.slot.Slot

class FilteredSlot(inventory: Inventory, index: Int, x: Int, y: Int, val filter: ItemConvertible) : Slot(inventory, index, x, y) {

    override fun canInsert(stack: ItemStack): Boolean =
        stack.item == filter && super.canInsert(stack)

}

package io.github.shkschneider.awesome.custom

import net.minecraft.entity.player.PlayerEntity
import net.minecraft.inventory.Inventories
import net.minecraft.inventory.Inventory
import net.minecraft.item.ItemStack
import net.minecraft.util.collection.DefaultedList

// https://fabricmc.net/wiki/tutorial:inventory
interface IInventory : Inventory {

    val items: DefaultedList<ItemStack>

    override fun size(): Int = items.size

    override fun isEmpty(): Boolean = items.all { it.isEmpty }

    override fun getStack(slot: Int): ItemStack =
        items.getOrNull(slot) ?: ItemStack.EMPTY

    override fun removeStack(slot: Int, count: Int): ItemStack {
        val result = Inventories.splitStack(items, slot, count)
        if (!result.isEmpty) {
            markDirty()
        }
        return result
    }

    override fun removeStack(slot: Int): ItemStack = Inventories.removeStack(items, slot)

    override fun setStack(slot: Int, stack: ItemStack) {
        items[slot] = stack
        if (stack.count > stack.maxCount) {
            stack.count = stack.maxCount
        }
    }

    override fun clear() {
        items.clear()
    }

    override fun canPlayerUse(player: PlayerEntity): Boolean  = true

}

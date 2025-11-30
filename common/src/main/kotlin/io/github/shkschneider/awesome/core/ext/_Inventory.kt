package io.github.shkschneider.awesome.core.ext

import net.minecraft.inventory.Inventory
import net.minecraft.item.ItemStack

fun Inventory.getStacks(): List<ItemStack> =
    (0 until size()).map { getStack(it) }

fun Inventory.insert(itemStack: ItemStack): ItemStack {
    (0 until size()).filter { getStack(it).item == itemStack.item }.forEach { index ->
        if (itemStack.isEmpty) return@forEach
        while (itemStack.count > 0 && !getStack(index).isFull) {
            getStack(index).count++
            itemStack.count--
        }
    }
    (0 until size()).filter { getStack(it).isEmpty }.forEach { index ->
        setStack(index, itemStack.copy())
        itemStack.count = 0
    }
    markDirty()
    return itemStack
}

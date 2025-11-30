package io.github.shkschneider.awesome.custom

import io.github.shkschneider.awesome.core.ext.getStacks
import io.github.shkschneider.awesome.mixins.ICraftingInventoryMixin
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.inventory.CraftingInventory
import net.minecraft.inventory.Inventory
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.screen.ScreenHandler
import net.minecraft.util.collection.DefaultedList

/**
 * Author: supersaiyansubtlety
 * License: MIT
 * Source: https://gitlab.com/supersaiyansubtlety/automated_crafting
 */
class DummyCraftingInventory(widget: Int, height: Int, inventory: DefaultedList<ItemStack>): CraftingInventory(DummyHandler(), widget, height), Inventory {

    init {
        @Suppress("CAST_NEVER_SUCCEEDS")
        (this as ICraftingInventoryMixin).stacks = inventory
    }

    override fun isEmpty(): Boolean = getStacks().isEmpty()

    override fun containsAny(items: MutableSet<Item>): Boolean = getStacks().any { it.item in items }

    override fun count(item: Item): Int = getStacks().count { it.item == item }

    override fun isValid(slot: Int, stack: ItemStack): Boolean = getStack(slot).isEmpty

    override fun getMaxCountPerStack(): Int = 1

    private class DummyHandler : ScreenHandler(null, 0) {
        override fun canUse(player: PlayerEntity): Boolean = false
        override fun transferSlot(player: PlayerEntity, index: Int): ItemStack = ItemStack.EMPTY
        override fun onContentChanged(inventory: Inventory) = Unit
    }

}

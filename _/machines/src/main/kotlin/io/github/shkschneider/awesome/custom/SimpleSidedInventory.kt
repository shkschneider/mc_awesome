package io.github.shkschneider.awesome.custom

import net.minecraft.inventory.SidedInventory
import net.minecraft.inventory.SimpleInventory
import net.minecraft.item.ItemStack
import net.minecraft.util.math.Direction

class SimpleSidedInventory(size: Int) : SimpleInventory(size), SidedInventory {

    override fun getAvailableSlots(side: Direction): IntArray =
        (0 until size()).toList().toIntArray()

    override fun canInsert(slot: Int, stack: ItemStack, dir: Direction?): Boolean =
        true

    override fun canExtract(slot: Int, stack: ItemStack, dir: Direction): Boolean =
        true

}

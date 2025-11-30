package io.github.shkschneider.awesome.core.ext

import net.minecraft.enchantment.Enchantment
import net.minecraft.enchantment.EnchantmentHelper
import net.minecraft.item.ItemStack

val ItemStack.isFull: Boolean get() =
    this.count >= this.maxCount

fun ItemStack.getEnchantments(): Map<Enchantment, Int> =
    EnchantmentHelper.get(this)

fun ItemStack.canInsert(itemStack: ItemStack): Boolean =
    this.isEmpty || (this.item == itemStack.item && this.count + itemStack.count <= this.maxCount)

fun ItemStack.copy(amount: Int): ItemStack =
    this.copy().apply { this.count = amount }

// EnchantmentHelper.getLevel() does not work for EnchantedBooks
fun ItemStack.getEnchantmentLevel(enchantment: Enchantment): Int =
    EnchantmentHelper.get(this).getOrDefault(enchantment, 0)

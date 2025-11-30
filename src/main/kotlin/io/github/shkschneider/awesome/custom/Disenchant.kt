package io.github.shkschneider.awesome.custom

import net.minecraft.enchantment.EnchantmentHelper
import net.minecraft.item.ItemStack
import net.minecraft.item.Items

object Disenchant {

    operator fun invoke(input: ItemStack, allowCurses: Boolean): ItemStack {
        if (input.hasEnchantments().not()) return ItemStack.EMPTY
        val output = ItemStack(Items.ENCHANTED_BOOK)
        val enchantments = EnchantmentHelper.get(input).entries.filter { entry ->
            allowCurses || entry.key.isCursed.not()
        }.associate { it.key to it.value }
        EnchantmentHelper.set(enchantments, output)
        output.repairCost = 0
        return output
    }

}

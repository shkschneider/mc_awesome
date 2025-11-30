package io.github.shkschneider.awesome.core.ext

import net.minecraft.inventory.SimpleInventory
import net.minecraft.item.ItemStack
import net.minecraft.recipe.Recipe

fun Recipe<*>.test(stacks: List<ItemStack>): Int {
    val inventory = SimpleInventory(stacks.size).apply {
        stacks.forEach(::addStack)
    }
    if (inventory.isEmpty) return -1
    if (ingredients.any { ingredient -> inventory.getStacks().none { ingredient.test(it) } }) return -2
    ingredients.forEach { ingredient ->
        val index = inventory.getStacks().indexOfFirst { ingredient.test(it) }.takeIf { it >= 0 } ?: return -3
        inventory.removeStack(index, 1)
    }
    return 1
}

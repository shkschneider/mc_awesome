package io.github.shkschneider.awesome.core

import io.github.shkschneider.awesome.core.ext.canInsert
import net.minecraft.inventory.Inventory
import net.minecraft.item.ItemStack

object AwesomeRecipes {

    @Suppress("SimplifiableCallChain")
    fun <T : Inventory> get(recipes: List<AwesomeRecipe<T>>, inputs: List<ItemStack>, output: ItemStack): AwesomeRecipe<T>? =
        recipes.filter { recipe ->
            // recipe must have all its ingredients present in inputs and have room in any outputs
            recipe.inputs.all { input -> inputs.any { stack -> stack.item == input.item && stack.count >= input.count } }
        }.filter { recipe ->
            // only returns a recipe when there is output space
            output.canInsert(recipe.output)
        }.firstOrNull()

}

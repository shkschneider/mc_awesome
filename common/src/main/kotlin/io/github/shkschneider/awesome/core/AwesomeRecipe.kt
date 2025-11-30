package io.github.shkschneider.awesome.core

import io.github.shkschneider.awesome.core.ext.getStacks
import io.github.shkschneider.awesome.core.ext.id
import net.minecraft.inventory.Inventory
import net.minecraft.item.ItemStack
import net.minecraft.recipe.Ingredient
import net.minecraft.recipe.Recipe
import net.minecraft.recipe.RecipeSerializer
import net.minecraft.recipe.RecipeType
import net.minecraft.util.Identifier
import net.minecraft.util.collection.DefaultedList
import net.minecraft.world.World

class AwesomeRecipe<T : Inventory>(
    private val type: AwesomeRecipeType<AwesomeRecipe<T>>,
    val inputs: List<ItemStack>,
    val time: Int, // ticks
    private val output: ItemStack,
) : Recipe<T> {

    override fun matches(inventory: T, world: World): Boolean =
        inputs.all { input ->
            inventory.getStacks().any { it.item == input.item && it.count >= input.count }
        }

    override fun craft(inventory: T): ItemStack =
        output

    override fun fits(width: Int, height: Int): Boolean =
        width * height <= inputs.size

    override fun getIngredients(): DefaultedList<Ingredient> =
        DefaultedList.ofSize<Ingredient>(inputs.size).apply {
            inputs.forEach { input ->
                add(Ingredient.ofStacks(input))
            }
        }

    override fun getOutput(): ItemStack =
        output.copy()

    override fun getId(): Identifier {
        val inputId = inputs.first().item.id()
        val outputId = output.item.id()
        // modId:firstInput_output_n
        return AwesomeUtils.identifier("${inputId.path}_${outputId.path}_${output.count}")
    }

    override fun getSerializer(): RecipeSerializer<*> {
        throw NotImplementedError()
    }

    override fun getType(): RecipeType<AwesomeRecipe<T>> =
        type

    override fun toString(): String =
        output.toString()

}

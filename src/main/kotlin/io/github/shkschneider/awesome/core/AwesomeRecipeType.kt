package io.github.shkschneider.awesome.core

import net.minecraft.inventory.Inventory
import net.minecraft.recipe.Recipe
import net.minecraft.recipe.RecipeType

class AwesomeRecipeType<T : Recipe<out Inventory>> : RecipeType<T>

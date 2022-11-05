package io.github.shkschneider.awesome.machines.smelter

import io.github.shkschneider.awesome.Awesome
import io.github.shkschneider.awesome.recipes.AwesomeRecipe
import io.github.shkschneider.awesome.recipes.AwesomeRecipeType
import io.github.shkschneider.awesome.materials.AwesomeMaterials
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.recipe.Recipe

object SmelterRecipes {

    private val SMELTING = AwesomeRecipeType<Recipe<SmelterBlock.Entity>>()

    operator fun invoke(): List<AwesomeRecipe<SmelterBlock.Entity>> = mutableListOf(
        // Dust -> Ingot/Gem (no redstone)
        AwesomeRecipe(SMELTING, listOf(ItemStack(AwesomeMaterials.coalDust, 1)), AwesomeMaterials.redstoneFlux, ItemStack(Items.COAL, 1)),
        AwesomeRecipe(SMELTING, listOf(ItemStack(AwesomeMaterials.copperDust, 1)), AwesomeMaterials.redstoneFlux, ItemStack(Items.COPPER_INGOT, 1)),
        AwesomeRecipe(SMELTING, listOf(ItemStack(AwesomeMaterials.diamondDust, 1)), AwesomeMaterials.redstoneFlux, ItemStack(Items.DIAMOND, 1)),
        AwesomeRecipe(SMELTING, listOf(ItemStack(AwesomeMaterials.emeraldDust, 1)), AwesomeMaterials.redstoneFlux, ItemStack(Items.EMERALD, 1)),
        AwesomeRecipe(SMELTING, listOf(ItemStack(AwesomeMaterials.goldDust, 1)), AwesomeMaterials.redstoneFlux, ItemStack(Items.GOLD_INGOT, 1)),
        AwesomeRecipe(SMELTING, listOf(ItemStack(AwesomeMaterials.ironDust, 1)), AwesomeMaterials.redstoneFlux, ItemStack(Items.IRON_INGOT, 1)),
        AwesomeRecipe(SMELTING, listOf(ItemStack(AwesomeMaterials.lapisDust, 1)), AwesomeMaterials.redstoneFlux, ItemStack(Items.LAPIS_LAZULI, 1)),
        AwesomeRecipe(SMELTING, listOf(ItemStack(AwesomeMaterials.quartzDust, 1)), AwesomeMaterials.redstoneFlux, ItemStack(Items.QUARTZ, 1)),
        // Raw -> Ingot
        AwesomeRecipe(SMELTING, listOf(ItemStack(Items.RAW_COPPER, 1)), AwesomeMaterials.redstoneFlux, ItemStack(Items.COPPER_INGOT, 1)),
        AwesomeRecipe(SMELTING, listOf(ItemStack(Items.RAW_GOLD, 1)), AwesomeMaterials.redstoneFlux, ItemStack(Items.GOLD_INGOT, 1)),
        AwesomeRecipe(SMELTING, listOf(ItemStack(Items.RAW_IRON, 1)), AwesomeMaterials.redstoneFlux, ItemStack(Items.IRON_INGOT, 1)),
        // RawBlock -> Block
        AwesomeRecipe(SMELTING, listOf(ItemStack(Items.RAW_COPPER_BLOCK, 1)), AwesomeMaterials.redstoneFlux, ItemStack(Items.COPPER_BLOCK, 1)),
        AwesomeRecipe(SMELTING, listOf(ItemStack(Items.RAW_GOLD_BLOCK, 1)), AwesomeMaterials.redstoneFlux, ItemStack(Items.GOLD_BLOCK, 1)),
        AwesomeRecipe(SMELTING, listOf(ItemStack(Items.RAW_IRON_BLOCK, 1)), AwesomeMaterials.redstoneFlux, ItemStack(Items.IRON_BLOCK, 1)),
    ).apply {
        if (Awesome.CONFIG.redstoneFluxFromRandomiumOre) {
            add(AwesomeRecipe(SMELTING, listOf(ItemStack(AwesomeMaterials.deepslateRandomiumOre, 1)), AwesomeMaterials.redstoneFlux, ItemStack(AwesomeMaterials.redstoneFlux, 1)))
            add(AwesomeRecipe(SMELTING, listOf(ItemStack(AwesomeMaterials.endRandomiumOre, 1)), AwesomeMaterials.redstoneFlux, ItemStack(AwesomeMaterials.redstoneFlux, 1)))
            add(AwesomeRecipe(SMELTING, listOf(ItemStack(AwesomeMaterials.randomiumOre, 1)), AwesomeMaterials.redstoneFlux, ItemStack(AwesomeMaterials.redstoneFlux, 1)))
        }
    }

}

package io.github.shkschneider.awesome.machines.refinery

import io.github.shkschneider.awesome.AwesomeItems
import io.github.shkschneider.awesome.core.AwesomeRecipe
import io.github.shkschneider.awesome.core.AwesomeRecipeType
import net.minecraft.item.ItemStack
import net.minecraft.item.Items

object RefineryRecipes {

    private val REFINING = AwesomeRecipeType<AwesomeRecipe<RefineryBlock.Entity>>()

    operator fun invoke(): List<AwesomeRecipe<RefineryBlock.Entity>> = mutableListOf(
        // Ore+Water -> Chip
        AwesomeRecipe(REFINING, listOf(ItemStack(Items.COAL_ORE, 1)), time = 20, ItemStack(AwesomeItems.Coal.chip, 8)),
        AwesomeRecipe(REFINING, listOf(ItemStack(Items.COPPER_ORE, 1)), time = 20, ItemStack(AwesomeItems.Copper.chip, 8)),
        AwesomeRecipe(REFINING, listOf(ItemStack(Items.DEEPSLATE_COAL_ORE, 1)), time = 20, ItemStack(AwesomeItems.Coal.chip, 8)),
        AwesomeRecipe(REFINING, listOf(ItemStack(Items.DEEPSLATE_COPPER_ORE, 1)), time = 20, ItemStack(AwesomeItems.Copper.chip, 8)),
        AwesomeRecipe(REFINING, listOf(ItemStack(Items.DEEPSLATE_DIAMOND_ORE, 1)), time = 20, ItemStack(AwesomeItems.Diamond.chip, 8)),
        AwesomeRecipe(REFINING, listOf(ItemStack(Items.DEEPSLATE_EMERALD_ORE, 1)), time = 20, ItemStack(AwesomeItems.Emerald.chip, 8)),
        AwesomeRecipe(REFINING, listOf(ItemStack(Items.DEEPSLATE_GOLD_ORE, 1)), time = 20, ItemStack(AwesomeItems.Gold.chip, 8)),
        AwesomeRecipe(REFINING, listOf(ItemStack(Items.DEEPSLATE_IRON_ORE, 1)), time = 20, ItemStack(AwesomeItems.Iron.chip, 8)),
        AwesomeRecipe(REFINING, listOf(ItemStack(Items.DEEPSLATE_LAPIS_ORE, 1)), time = 20, ItemStack(AwesomeItems.Lapis.chip, 8)),
        AwesomeRecipe(REFINING, listOf(ItemStack(Items.DEEPSLATE_REDSTONE_ORE, 1)), time = 20, ItemStack(AwesomeItems.Redstone.chip, 8)),
        AwesomeRecipe(REFINING, listOf(ItemStack(Items.DIAMOND_ORE, 1)), time = 20, ItemStack(AwesomeItems.Diamond.chip, 8)),
        AwesomeRecipe(REFINING, listOf(ItemStack(Items.EMERALD_ORE, 1)), time = 20, ItemStack(AwesomeItems.Emerald.chip, 8)),
        AwesomeRecipe(REFINING, listOf(ItemStack(Items.GOLD_ORE, 1)), time = 20, ItemStack(AwesomeItems.Gold.chip, 8)),
        AwesomeRecipe(REFINING, listOf(ItemStack(Items.IRON_ORE, 1)), time = 20, ItemStack(AwesomeItems.Iron.chip, 8)),
        AwesomeRecipe(REFINING, listOf(ItemStack(Items.LAPIS_ORE, 1)), time = 20, ItemStack(AwesomeItems.Lapis.chip, 8)),
        AwesomeRecipe(REFINING, listOf(ItemStack(Items.NETHER_GOLD_ORE, 1)), time = 20, ItemStack(AwesomeItems.Gold.chip, 8)),
        AwesomeRecipe(REFINING, listOf(ItemStack(Items.NETHER_QUARTZ_ORE, 1)), time = 20, ItemStack(AwesomeItems.Quartz.chip, 8)),
        AwesomeRecipe(REFINING, listOf(ItemStack(Items.REDSTONE_ORE, 1)), time = 20, ItemStack(AwesomeItems.Redstone.chip, 8)),
    )

}

package io.github.shkschneider.awesome.machines.quarry

import io.github.shkschneider.awesome.core.AwesomeRecipe
import io.github.shkschneider.awesome.core.AwesomeRecipeType
import net.minecraft.item.ItemStack
import net.minecraft.item.Items

object QuarryRecipes {

    private val QUARRYING = AwesomeRecipeType<AwesomeRecipe<QuarryBlock.Entity>>()

    operator fun invoke(): List<AwesomeRecipe<QuarryBlock.Entity>> = listOf(
        // placed_feature.count * configured_feature.size
        AwesomeRecipe(QUARRYING, listOf(ItemStack(Items.AIR, 1)), time = 0, ItemStack(Items.COAL_ORE, 20 * 17)),
        AwesomeRecipe(QUARRYING, listOf(ItemStack(Items.AIR, 1)), time = 0, ItemStack(Items.DIAMOND_ORE, 7 * 8)),
        AwesomeRecipe(QUARRYING, listOf(ItemStack(Items.AIR, 1)), time = 0, ItemStack(Items.EMERALD_ORE, 100 * 3)),
        AwesomeRecipe(QUARRYING, listOf(ItemStack(Items.AIR, 1)), time = 0, ItemStack(Items.LAPIS_ORE, 2 * 7)),
        AwesomeRecipe(QUARRYING, listOf(ItemStack(Items.AIR, 1)), time = 0, ItemStack(Items.COPPER_ORE, 16 * 15)),
        AwesomeRecipe(QUARRYING, listOf(ItemStack(Items.AIR, 1)), time = 0, ItemStack(Items.GOLD_ORE, 4 * 9)),
        AwesomeRecipe(QUARRYING, listOf(ItemStack(Items.AIR, 1)), time = 0, ItemStack(Items.IRON_ORE, 90 * 9)),
        AwesomeRecipe(QUARRYING, listOf(ItemStack(Items.AIR, 1)), time = 0, ItemStack(Items.REDSTONE_ORE, 4 * 8)),
    )

}

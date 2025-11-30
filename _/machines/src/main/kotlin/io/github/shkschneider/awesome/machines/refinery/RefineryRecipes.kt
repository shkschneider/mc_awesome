package io.github.shkschneider.awesome.machines.refinery

import io.github.shkschneider.awesome.core.AwesomeRecipe
import io.github.shkschneider.awesome.core.AwesomeRecipeType
import net.minecraft.block.Blocks
import net.minecraft.item.ItemStack
import net.minecraft.item.Items

object RefineryRecipes {

    private val REFINING = AwesomeRecipeType<AwesomeRecipe<RefineryBlock.Entity>>()

    operator fun invoke(): List<AwesomeRecipe<RefineryBlock.Entity>> = mutableListOf(
        // Ore -> Gem/Ingot
        AwesomeRecipe(REFINING, listOf(ItemStack(Items.COAL_ORE, 1)), time = 20, ItemStack(Items.COAL, 1)),
        AwesomeRecipe(REFINING, listOf(ItemStack(Items.COPPER_ORE, 1)), time = 20, ItemStack(Items.COPPER_INGOT, 1)),
        AwesomeRecipe(REFINING, listOf(ItemStack(Items.DEEPSLATE_COAL_ORE, 1)), time = 20, ItemStack(Items.COAL, 1)),
        AwesomeRecipe(REFINING, listOf(ItemStack(Items.DEEPSLATE_COPPER_ORE, 1)), time = 20, ItemStack(Items.COPPER_INGOT, 1)),
        AwesomeRecipe(REFINING, listOf(ItemStack(Items.DEEPSLATE_DIAMOND_ORE, 1)), time = 20, ItemStack(Items.DIAMOND, 1)),
        AwesomeRecipe(REFINING, listOf(ItemStack(Items.DEEPSLATE_EMERALD_ORE, 1)), time = 20, ItemStack(Items.EMERALD, 1)),
        AwesomeRecipe(REFINING, listOf(ItemStack(Items.DEEPSLATE_GOLD_ORE, 1)), time = 20, ItemStack(Items.GOLD_INGOT, 1)),
        AwesomeRecipe(REFINING, listOf(ItemStack(Items.DEEPSLATE_IRON_ORE, 1)), time = 20, ItemStack(Items.IRON_INGOT, 1)),
        AwesomeRecipe(REFINING, listOf(ItemStack(Items.DEEPSLATE_LAPIS_ORE, 1)), time = 20, ItemStack(Items.LAPIS_LAZULI, 1)),
        AwesomeRecipe(REFINING, listOf(ItemStack(Items.DEEPSLATE_REDSTONE_ORE, 1)), time = 20, ItemStack(Items.REDSTONE, 1)),
        AwesomeRecipe(REFINING, listOf(ItemStack(Items.DIAMOND_ORE, 1)), time = 20, ItemStack(Items.DIAMOND, 1)),
        AwesomeRecipe(REFINING, listOf(ItemStack(Items.EMERALD_ORE, 1)), time = 20, ItemStack(Items.EMERALD, 1)),
        AwesomeRecipe(REFINING, listOf(ItemStack(Items.GOLD_ORE, 1)), time = 20, ItemStack(Items.GOLD_INGOT, 1)),
        AwesomeRecipe(REFINING, listOf(ItemStack(Items.IRON_ORE, 1)), time = 20, ItemStack(Items.IRON_INGOT, 1)),
        AwesomeRecipe(REFINING, listOf(ItemStack(Items.LAPIS_ORE, 1)), time = 20, ItemStack(Items.LAPIS_LAZULI, 1)),
        AwesomeRecipe(REFINING, listOf(ItemStack(Items.NETHER_GOLD_ORE, 1)), time = 20, ItemStack(Items.GOLD_INGOT, 1)),
        AwesomeRecipe(REFINING, listOf(ItemStack(Items.NETHER_QUARTZ_ORE, 1)), time = 20, ItemStack(Items.QUARTZ, 1)),
        AwesomeRecipe(REFINING, listOf(ItemStack(Items.REDSTONE_ORE, 1)), time = 20, ItemStack(Items.REDSTONE, 1)),
        // RawOre -> Ingot
        AwesomeRecipe(REFINING, listOf(ItemStack(Items.RAW_COPPER, 1)), time = 20, ItemStack(Items.COPPER_INGOT, 1)),
        AwesomeRecipe(REFINING, listOf(ItemStack(Items.RAW_GOLD, 1)), time = 20, ItemStack(Items.GOLD_INGOT, 1)),
        AwesomeRecipe(REFINING, listOf(ItemStack(Items.RAW_IRON, 1)), time = 20, ItemStack(Items.IRON_INGOT, 1)),
        // RawBlock -> Block
        AwesomeRecipe(REFINING, listOf(ItemStack(Items.RAW_COPPER_BLOCK, 1)), time = 20, ItemStack(Items.COPPER_BLOCK, 1)),
        AwesomeRecipe(REFINING, listOf(ItemStack(Items.RAW_GOLD_BLOCK, 1)), time = 20, ItemStack(Items.GOLD_BLOCK, 1)),
        AwesomeRecipe(REFINING, listOf(ItemStack(Items.RAW_IRON_BLOCK, 1)), time = 20, ItemStack(Items.IRON_BLOCK, 1)),
        // AncientDebris -> NetheriteScrap
        AwesomeRecipe(REFINING, listOf(ItemStack(Blocks.ANCIENT_DEBRIS, 1)), time = 200, ItemStack(Items.NETHERITE_SCRAP, 1)),
    )

}

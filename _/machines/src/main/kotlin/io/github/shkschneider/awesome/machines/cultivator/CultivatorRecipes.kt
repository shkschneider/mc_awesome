package io.github.shkschneider.awesome.machines.cultivator

import io.github.shkschneider.awesome.core.AwesomeRecipe
import io.github.shkschneider.awesome.core.AwesomeRecipeType
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import java.util.Random

object CultivatorRecipes {

    fun shouldGiveSecondaryDrop(random: Random): Boolean =
        (random.nextInt(100) < 60 /*%*/)

    private val CULTIVATING = AwesomeRecipeType<AwesomeRecipe<CultivatorBlock.Entity>>()

    // TreeConfiguredFeatures
    operator fun invoke(): List<AwesomeRecipe<CultivatorBlock.Entity>> = listOf(
        // Sapling->Log
        AwesomeRecipe(CULTIVATING, listOf(ItemStack(Items.ACACIA_SAPLING, 1)), time = 20, ItemStack(Items.ACACIA_LOG, 4)),
        AwesomeRecipe(CULTIVATING, listOf(ItemStack(Items.BIRCH_SAPLING, 1)), time = 20, ItemStack(Items.BIRCH_LOG, 4)),
        AwesomeRecipe(CULTIVATING, listOf(ItemStack(Items.DARK_OAK_SAPLING, 1)), time = 20, ItemStack(Items.DARK_OAK_LOG, 4)),
        AwesomeRecipe(CULTIVATING, listOf(ItemStack(Items.JUNGLE_SAPLING, 1)), time = 20, ItemStack(Items.JUNGLE_LOG, 4)),
        // 1.19 AwesomeRecipe(CULTIVATING, listOf(ItemStack(Items.MANGROVE_PROPAGULE, 1)), time = 20, ItemStack(Items.MANGROVE_LOG, 4)),
        AwesomeRecipe(CULTIVATING, listOf(ItemStack(Items.OAK_SAPLING, 1)), time = 20, ItemStack(Items.OAK_LOG, 4)),
        AwesomeRecipe(CULTIVATING, listOf(ItemStack(Items.SPRUCE_SAPLING, 1)), time = 20, ItemStack(Items.SPRUCE_LOG, 4)),
        // Fungus->Log
        AwesomeRecipe(CULTIVATING, listOf(ItemStack(Items.BROWN_MUSHROOM, 1)), time = 20, ItemStack(Items.MUSHROOM_STEM, 4)),
        AwesomeRecipe(CULTIVATING, listOf(ItemStack(Items.CRIMSON_FUNGUS, 1)), time = 20, ItemStack(Items.CRIMSON_STEM, 4)),
        AwesomeRecipe(CULTIVATING, listOf(ItemStack(Items.RED_MUSHROOM, 1)), time = 20, ItemStack(Items.MUSHROOM_STEM, 4)),
        AwesomeRecipe(CULTIVATING, listOf(ItemStack(Items.WARPED_FUNGUS, 1)), time = 20, ItemStack(Items.WARPED_STEM, 4)),
        // Crops->Item
        AwesomeRecipe(CULTIVATING, listOf(ItemStack(Items.BEETROOT_SEEDS, 2)), time = 20, ItemStack(Items.BEETROOT, 1)),
        AwesomeRecipe(CULTIVATING, listOf(ItemStack(Items.MELON_SEEDS, 2)), time = 20, ItemStack(Items.MELON_SLICE, 1)),
        AwesomeRecipe(CULTIVATING, listOf(ItemStack(Items.PUMPKIN_SEEDS, 4)), time = 20, ItemStack(Items.PUMPKIN, 1)),
        AwesomeRecipe(CULTIVATING, listOf(ItemStack(Items.WHEAT_SEEDS, 2)), time = 20, ItemStack(Items.WHEAT, 1)),
    )

}

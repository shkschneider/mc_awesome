package io.github.shkschneider.awesome.machines.factory

import io.github.shkschneider.awesome.core.AwesomeRecipe
import io.github.shkschneider.awesome.core.AwesomeRecipeType
import net.minecraft.item.ItemStack
import net.minecraft.item.Items

object FactoryRecipes {

    private val FACTORING = AwesomeRecipeType<AwesomeRecipe<FactoryBlock.Entity>>()

    operator fun invoke(): List<AwesomeRecipe<FactoryBlock.Entity>> = listOf(
        // StoneBricks -> Stone -> Cobblestone -> Gravel
        AwesomeRecipe(FACTORING, listOf(ItemStack(Items.STONE_BRICKS, 1)), time = 20, ItemStack(Items.STONE, 4)),
        AwesomeRecipe(FACTORING, listOf(ItemStack(Items.STONE, 1)), time = 20, ItemStack(Items.COBBLESTONE, 1)),
        AwesomeRecipe(FACTORING, listOf(ItemStack(Items.COBBLESTONE, 1)), time = 20, ItemStack(Items.GRAVEL, 1)),
        // DeepslateTiles -> DeepslateBricks -> Deepslate ->
        AwesomeRecipe(FACTORING, listOf(ItemStack(Items.DEEPSLATE_TILES, 1)), time = 20, ItemStack(Items.DEEPSLATE_BRICKS, 4)),
        AwesomeRecipe(FACTORING, listOf(ItemStack(Items.DEEPSLATE_BRICKS, 1)), time = 20, ItemStack(Items.DEEPSLATE, 4)),
        // Sandstone -> Sand
        AwesomeRecipe(FACTORING, listOf(ItemStack(Items.SANDSTONE, 1)), time = 20, ItemStack(Items.SAND, 4)),
        // NetherBricks -> Netherrack -> NetherWart
        AwesomeRecipe(FACTORING, listOf(ItemStack(Items.NETHER_BRICKS, 1)), time = 20, ItemStack(Items.NETHERRACK, 4)),
        AwesomeRecipe(FACTORING, listOf(ItemStack(Items.NETHERRACK, 64)), time = 20, ItemStack(Items.NETHER_WART, 1)),
        // CryingObsidian -> Obsidian
        AwesomeRecipe(FACTORING, listOf(ItemStack(Items.CRYING_OBSIDIAN, 1)), time = 20, ItemStack(Items.OBSIDIAN, 1)),
        // Grass -> Dirt -> Mud -> Clay
        AwesomeRecipe(FACTORING, listOf(ItemStack(Items.GRASS_BLOCK, 1)), time = 20, ItemStack(Items.DIRT, 1)),
        // 1.19 AwesomeRecipe(FACTORING, listOf(ItemStack(Items.DIRT, 1)), time = 20, ItemStack(Items.MUD, 1)),
        // 1.19 AwesomeRecipe(FACTORING, listOf(ItemStack(Items.MUD, 1)), time = 20, ItemStack(Items.CLAY, 1)),
        // AncientDebris -> NetheriteScrap
        AwesomeRecipe(FACTORING, listOf(ItemStack(Items.ANCIENT_DEBRIS, 1)), time = 200, ItemStack(Items.NETHERITE_SCRAP, 1)),
    )

}

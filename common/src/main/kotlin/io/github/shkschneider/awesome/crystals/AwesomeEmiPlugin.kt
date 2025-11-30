package io.github.shkschneider.awesome.crystals

import dev.emi.emi.api.EmiPlugin
import dev.emi.emi.api.EmiRegistry
import dev.emi.emi.api.recipe.EmiRecipe
import dev.emi.emi.api.recipe.EmiRecipeCategory
import dev.emi.emi.api.render.EmiTexture
import dev.emi.emi.api.stack.EmiIngredient
import dev.emi.emi.api.stack.EmiStack
import dev.emi.emi.api.widget.WidgetHolder
import io.github.shkschneider.awesome.AwesomeCrystals
import io.github.shkschneider.awesome.core.AwesomeUtils
import io.github.shkschneider.awesome.core.ext.id
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.item.ItemStack
import net.minecraft.recipe.Ingredient
import net.minecraft.util.Identifier

@Environment(EnvType.CLIENT)
class AwesomeEmiPlugin : EmiPlugin {

    override fun register(registry: EmiRegistry) {
        listOf(
            AwesomeCrystals.coal,
            AwesomeCrystals.copper,
            AwesomeCrystals.diamond,
            AwesomeCrystals.emerald,
            AwesomeCrystals.ender,
            AwesomeCrystals.glowstone,
            AwesomeCrystals.gold,
            AwesomeCrystals.iron,
            AwesomeCrystals.lapis,
            AwesomeCrystals.netherite,
            AwesomeCrystals.quartz,
            AwesomeCrystals.redstone,
        ).forEach { crystal ->
            val category = EmiRecipeCategory(
                AwesomeUtils.identifier("budding_crystals"),
                EmiStack.of(crystal.budding),
                EmiTexture(crystal.budding.asItem().id(), 0, 0, 16, 16),
            ).also { registry.addCategory(it) }
            registry.addRecipe(AwesomeEmiRecipe(category, ItemStack(crystal.budding), ItemStack(crystal.crystal)))
            registry.addRecipe(AwesomeEmiRecipe(category, ItemStack(crystal.crystal), crystal.output))
        }
    }

}

class AwesomeEmiRecipe(
    private val category: EmiRecipeCategory,
    private val input: ItemStack,
    private val output: ItemStack,
) : EmiRecipe {

    override fun getId(): Identifier =
        AwesomeUtils.identifier("${input.item.id().path}_${output.item.id().path}")

    override fun getCategory(): EmiRecipeCategory =
        category

    override fun getInputs(): MutableList<EmiIngredient> = mutableListOf(
        EmiIngredient.of(Ingredient.ofStacks(input), input.count.toLong()),
    )

    override fun getOutputs(): MutableList<EmiStack> = mutableListOf(
        EmiStack.of(output.copy())
    )

    override fun getDisplayWidth(): Int = 76

    override fun getDisplayHeight(): Int = 18

    override fun addWidgets(holder: WidgetHolder) {
        with(holder) {
            addSlot(inputs.first(), 0, 0)
            addTexture(EmiTexture.EMPTY_ARROW, 26, holder.height / 2 - 18 / 2) // 24x17
            addSlot(outputs.first(), 58, holder.height / 2 - 18 / 2).recipeContext(this@AwesomeEmiRecipe)
        }
    }

}

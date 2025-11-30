package io.github.shkschneider.awesome.extras

import io.github.shkschneider.awesome.core.AwesomeItem
import io.github.shkschneider.awesome.core.AwesomeUtils
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.client.item.TooltipContext
import net.minecraft.item.FoodComponent
import net.minecraft.item.FoodComponents
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import net.minecraft.util.Formatting
import net.minecraft.world.World

class Omelette : AwesomeItem(
    id = AwesomeUtils.identifier("omelette"),
    settings = FabricItemSettings()
        .group(ItemGroup.FOOD)
        .food(FoodComponent.Builder()
            .hunger(FoodComponents.APPLE.hunger)
            .saturationModifier(FoodComponents.APPLE.saturationModifier * 2)
            .build()
        ),
    group = ItemGroup.FOOD,
) {

    override fun appendTooltip(stack: ItemStack, world: World?, tooltip: MutableList<Text>, context: TooltipContext) {
        tooltip.add(TranslatableText(AwesomeUtils.translatable("item", id.path, "hint")).formatted(Formatting.GRAY))
    }

}

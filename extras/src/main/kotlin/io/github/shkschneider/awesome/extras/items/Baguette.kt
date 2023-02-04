package io.github.shkschneider.awesome.extras.items

import io.github.shkschneider.awesome.Awesome
import io.github.shkschneider.awesome.core.AwesomeItem
import io.github.shkschneider.awesome.core.AwesomeUtils
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.client.item.TooltipContext
import net.minecraft.item.FoodComponent
import net.minecraft.item.FoodComponents
import net.minecraft.item.ItemStack
import net.minecraft.text.Text
import net.minecraft.util.Formatting
import net.minecraft.world.World

class Baguette : AwesomeItem(
    id = AwesomeUtils.identifier(ID),
    settings = FabricItemSettings()
        .food(FoodComponent.Builder()
            .hunger(FoodComponents.BREAD.hunger * 2)
            .saturationModifier(FoodComponents.BREAD.saturationModifier)
            .build()
        ),
    group = Awesome.GROUP,
) {

    companion object {

        const val ID = "baguette"

    }

    override fun appendTooltip(stack: ItemStack, world: World?, tooltip: MutableList<Text>, context: TooltipContext) {
        tooltip.add(Text.translatable(AwesomeUtils.translatable("item", ID, "hint")).formatted(Formatting.GRAY))
    }

}
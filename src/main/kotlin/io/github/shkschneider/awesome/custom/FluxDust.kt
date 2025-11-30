package io.github.shkschneider.awesome.custom

import io.github.shkschneider.awesome.core.AwesomeRegistries
import io.github.shkschneider.awesome.core.AwesomeUtils
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.util.Rarity

class FluxDust : Item(
    FabricItemSettings().group(ItemGroup.REDSTONE).maxCount(Minecraft.STACK).rarity(Rarity.UNCOMMON),
) {

    init {
        AwesomeRegistries.item(AwesomeUtils.identifier("flux_dust"), this, null)
    }

    override fun hasGlint(stack: ItemStack): Boolean = false

}

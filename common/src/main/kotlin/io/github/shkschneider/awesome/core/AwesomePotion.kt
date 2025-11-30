package io.github.shkschneider.awesome.core

import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.item.Item
import net.minecraft.potion.Potions

class AwesomePotion(
    val name: String,
    item: Item,
    effectInstance: StatusEffectInstance,
) {

    init {
        AwesomeRegistries.potion(name, effectInstance, Potions.AWKWARD to item)
    }

}

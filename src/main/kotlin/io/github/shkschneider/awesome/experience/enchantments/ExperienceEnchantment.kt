package io.github.shkschneider.awesome.experience.enchantments

import io.github.shkschneider.awesome.core.AwesomeEnchantment
import io.github.shkschneider.awesome.core.AwesomeUtils
import io.github.shkschneider.awesome.custom.Event
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents
import net.minecraft.enchantment.Enchantment
import net.minecraft.enchantment.EnchantmentHelper
import net.minecraft.enchantment.EnchantmentTarget
import net.minecraft.enchantment.Enchantments
import net.minecraft.entity.EquipmentSlot

class ExperienceEnchantment : AwesomeEnchantment(
    id = AwesomeUtils.identifier("experience"),
    Rarity.UNCOMMON,
    levels = 1 to 1,
    EnchantmentTarget.DIGGER,
    listOf(EquipmentSlot.MAINHAND),
) {

    init {
        @Event("PlayerBlockBreakEvents")
        PlayerBlockBreakEvents.AFTER.register(PlayerBlockBreakEvents.After { _, player, _, _, _ ->
            val silkTouch = EnchantmentHelper.getLevel(Enchantments.SILK_TOUCH, player.mainHandStack)
            val experience = EnchantmentHelper.getLevel(this, player.mainHandStack)
            if (silkTouch == 0 && experience > 0) {
                player.addExperience(1)
            }
        })
    }

    override fun canAccept(other: Enchantment): Boolean =
        listOf(this, Enchantments.SILK_TOUCH).contains(other).not()

}

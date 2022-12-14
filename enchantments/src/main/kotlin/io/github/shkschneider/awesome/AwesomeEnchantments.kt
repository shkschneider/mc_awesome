package io.github.shkschneider.awesome

import io.github.shkschneider.awesome.core.AwesomeEnchantment
import io.github.shkschneider.awesome.custom.OreXp
import io.github.shkschneider.awesome.custom.SilkTouchSpawners
import io.github.shkschneider.awesome.effects.AwesomeEffects
import io.github.shkschneider.awesome.enchantments.ExperienceEnchantment
import io.github.shkschneider.awesome.enchantments.IceAspectEnchantment
import io.github.shkschneider.awesome.enchantments.MagnetismEnchantment
import io.github.shkschneider.awesome.enchantments.PoisonAspectEnchantment
import io.github.shkschneider.awesome.enchantments.UnbreakableEnchantment
import io.github.shkschneider.awesome.enchantments.VeinMiningEnchantment
import io.github.shkschneider.awesome.potions.AwesomePotions

object AwesomeEnchantments {

    private lateinit var EXPERIENCE: AwesomeEnchantment
    val experience get() = EXPERIENCE
    private lateinit var ICE_ASPECT: AwesomeEnchantment
    val iceAspect get() = ICE_ASPECT
    private lateinit var MAGNETISM: AwesomeEnchantment
    val magnetism get() = MAGNETISM
    private lateinit var POISON_ASPECT: AwesomeEnchantment
    val poisonAspect get() = POISON_ASPECT
    private lateinit var UNBREAKABLE: AwesomeEnchantment
    val unbreakable get() = UNBREAKABLE
    private lateinit var VEIN_MINING: AwesomeEnchantment
    val veinMining get() = VEIN_MINING

    operator fun invoke() {
        AwesomeEffects()
        AwesomePotions()
        if (Awesome.CONFIG.enchantments.experience) {
            EXPERIENCE = ExperienceEnchantment()
        }
        if (Awesome.CONFIG.enchantments.magnetism) {
            MAGNETISM = MagnetismEnchantment()
        }
        if (Awesome.CONFIG.enchantments.unbreakable) {
            UNBREAKABLE = UnbreakableEnchantment()
        }
        if (Awesome.CONFIG.enchantments.veinMining) {
            VEIN_MINING = VeinMiningEnchantment()
        }
        if (Awesome.CONFIG.enchantments.aspects) {
            ICE_ASPECT = IceAspectEnchantment()
            POISON_ASPECT = PoisonAspectEnchantment()
        }
        if (Awesome.CONFIG.enchantments.oreXp) {
            OreXp()
        }
        if (Awesome.CONFIG.enchantments.silkTouchSpawners) {
            SilkTouchSpawners()
        }
    }

}

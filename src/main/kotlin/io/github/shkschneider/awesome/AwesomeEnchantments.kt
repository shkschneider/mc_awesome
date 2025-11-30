package io.github.shkschneider.awesome

import io.github.shkschneider.awesome.core.AwesomeEnchantment
import io.github.shkschneider.awesome.enchantments.CriticalEnchantment
import io.github.shkschneider.awesome.enchantments.LastStandEnchantment
import io.github.shkschneider.awesome.enchantments.MagnetismEnchantment
import io.github.shkschneider.awesome.enchantments.SixthSenseEnchantment
import io.github.shkschneider.awesome.enchantments.UnbreakableEnchantment
import io.github.shkschneider.awesome.enchantments.VampirismEnchantment
import io.github.shkschneider.awesome.enchantments.VeinMiningEnchantment

object AwesomeEnchantments {

    private lateinit var _sixthSense: AwesomeEnchantment
    val sixthSense get() = _sixthSense
    private lateinit var _unbreakable: AwesomeEnchantment
    val unbreakable get() = _unbreakable

    operator fun invoke() {
        if (Awesome.CONFIG.enchantments.critical) CriticalEnchantment()
        if (Awesome.CONFIG.enchantments.lastStand) LastStandEnchantment()
        if (Awesome.CONFIG.enchantments.magnetism) MagnetismEnchantment()
        if (Awesome.CONFIG.enchantments.sixthSense) _sixthSense = SixthSenseEnchantment()
        if (Awesome.CONFIG.enchantments.unbreakable) _unbreakable = UnbreakableEnchantment()
        if (Awesome.CONFIG.enchantments.vampirism) VampirismEnchantment()
        if (Awesome.CONFIG.enchantments.veinMining) VeinMiningEnchantment()
    }

}

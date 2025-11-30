package io.github.shkschneider.awesome

import io.github.shkschneider.awesome.crystals.AwesomeCrystal
import net.minecraft.item.ItemStack
import net.minecraft.item.Items

object AwesomeCrystals {

    val coal: AwesomeCrystal = AwesomeCrystal("coal", ItemStack(Items.COAL, Awesome.CONFIG.crystals.coal))
    val copper: AwesomeCrystal = AwesomeCrystal("copper", ItemStack(Items.RAW_COPPER, Awesome.CONFIG.crystals.coal))
    val diamond: AwesomeCrystal = AwesomeCrystal("diamond", ItemStack(Items.DIAMOND, Awesome.CONFIG.crystals.diamond))
    val emerald: AwesomeCrystal = AwesomeCrystal("emerald", ItemStack(Items.EMERALD, Awesome.CONFIG.crystals.emerald))
    val ender: AwesomeCrystal = AwesomeCrystal("ender", ItemStack(Items.ENDER_PEARL, Awesome.CONFIG.crystals.ender))
    val glowstone: AwesomeCrystal = AwesomeCrystal("glowstone", ItemStack(Items.GLOWSTONE_DUST, Awesome.CONFIG.crystals.glowstone))
    val gold: AwesomeCrystal = AwesomeCrystal("gold", ItemStack(Items.RAW_GOLD, Awesome.CONFIG.crystals.gold))
    val iron: AwesomeCrystal = AwesomeCrystal("iron", ItemStack(Items.RAW_IRON, Awesome.CONFIG.crystals.iron))
    val lapis: AwesomeCrystal = AwesomeCrystal("lapis", ItemStack(Items.LAPIS_LAZULI, Awesome.CONFIG.crystals.lapis))
    val netherite: AwesomeCrystal = AwesomeCrystal("netherite", ItemStack(Items.ANCIENT_DEBRIS, Awesome.CONFIG.crystals.netherite))
    val quartz: AwesomeCrystal = AwesomeCrystal("quartz", ItemStack(Items.QUARTZ, Awesome.CONFIG.crystals.quartz))
    val redstone: AwesomeCrystal = AwesomeCrystal("redstone", ItemStack(Items.REDSTONE, Awesome.CONFIG.crystals.redstone))

    operator fun invoke() = Unit

}

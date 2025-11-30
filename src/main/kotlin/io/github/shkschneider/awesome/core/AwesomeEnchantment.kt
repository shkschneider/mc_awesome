package io.github.shkschneider.awesome.core

import net.minecraft.enchantment.Enchantment
import net.minecraft.enchantment.EnchantmentTarget
import net.minecraft.entity.Entity
import net.minecraft.entity.EquipmentSlot
import net.minecraft.entity.LivingEntity
import net.minecraft.util.Identifier

abstract class AwesomeEnchantment(
    val id: Identifier,
    rarity: Rarity,
    private val levels: Pair<Int, Int>,
    target: EnchantmentTarget,
    slots: List<EquipmentSlot>,
) : Enchantment(rarity, target, slots.toTypedArray()) {

    init {
        init()
    }

    private fun init() {
        AwesomeRegistries.enchantment(id, this as Enchantment)
    }

    override fun onTargetDamaged(user: LivingEntity, target: Entity, level: Int) {
        // !user.world.isClient
        super.onTargetDamaged(user, target, level)
    }

    override fun onUserDamaged(user: LivingEntity, attacker: Entity, level: Int) {
        // !user.world.isClient
        super.onUserDamaged(user, attacker, level)
    }

    override fun canAccept(other: Enchantment): Boolean =
        other != this

    override fun getMinLevel(): Int {
        return levels.first
    }

    override fun getMaxLevel(): Int {
        return levels.second
    }

}

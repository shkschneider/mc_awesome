package io.github.shkschneider.awesome.enchantments

import io.github.shkschneider.awesome.core.AwesomeEnchantment
import io.github.shkschneider.awesome.core.AwesomeLogger
import io.github.shkschneider.awesome.core.AwesomeUtils
import net.minecraft.enchantment.Enchantment
import net.minecraft.enchantment.EnchantmentTarget
import net.minecraft.entity.Entity
import net.minecraft.entity.EquipmentSlot
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.damage.DamageSource
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.SwordItem

class LastStandEnchantment : AwesomeEnchantment(
    id = AwesomeUtils.identifier("last_stand"),
    Rarity.UNCOMMON,
    levels = 1 to 1,
    EnchantmentTarget.WEAPON,
    listOf(EquipmentSlot.MAINHAND),
) {

    override fun onTargetDamaged(user: LivingEntity, target: Entity, level: Int) {
        if (user.world.isClient) return
        (user as? PlayerEntity)?.takeIf { it.isAlive }?.let { playerEntity ->
            (target as? LivingEntity)?.takeIf { it.isAlive }?.let { livingEntity ->
                lastStand(playerEntity, livingEntity)
            }
        }
    }

    private fun lastStand(playerEntity: PlayerEntity, livingEntity: LivingEntity) {
        val sword = (playerEntity.mainHandStack.item as? SwordItem) ?: return
        val ratio = (playerEntity.maxHealth / playerEntity.health) / 2F
        val damage = sword.attackDamage * ratio
        if (livingEntity.isAlive && damage > 1F) {
            AwesomeLogger.debug("lastStand: $damage")
            livingEntity.damage(DamageSource.MAGIC, damage)
        }
    }

    override fun canAccept(other: Enchantment): Boolean =
        super.canAccept(other) && other.translationKey == AwesomeUtils.translatable("critical")

}

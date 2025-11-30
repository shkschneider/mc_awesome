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

class CriticalEnchantment : AwesomeEnchantment(
    id = AwesomeUtils.identifier("critical"),
    Rarity.UNCOMMON,
    levels = 1 to 1,
    EnchantmentTarget.WEAPON,
    listOf(EquipmentSlot.MAINHAND),
) {

    override fun onTargetDamaged(user: LivingEntity, target: Entity, level: Int) {
        super.onTargetDamaged(user, target, level)
        ((user as? PlayerEntity)?.mainHandStack?.item as? SwordItem)?.let { sword ->
            val damage = sword.attackDamage / 2
            AwesomeLogger.debug("critical: $damage")
            target.damage(DamageSource.MAGIC, damage)
        }
    }

    override fun canAccept(other: Enchantment): Boolean =
        super.canAccept(other) && other.translationKey == AwesomeUtils.translatable("last_stand")

}

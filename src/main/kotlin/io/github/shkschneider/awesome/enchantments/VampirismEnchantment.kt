package io.github.shkschneider.awesome.enchantments

import io.github.shkschneider.awesome.core.AwesomeEnchantment
import io.github.shkschneider.awesome.core.AwesomeUtils
import net.minecraft.enchantment.EnchantmentTarget
import net.minecraft.entity.Entity
import net.minecraft.entity.EquipmentSlot
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity

class VampirismEnchantment : AwesomeEnchantment(
    id = AwesomeUtils.identifier("vampirism"),
    Rarity.RARE,
    levels = 1 to 5,
    EnchantmentTarget.WEAPON,
    listOf(EquipmentSlot.MAINHAND),
) {

    override fun onTargetDamaged(user: LivingEntity, target: Entity, level: Int) {
        if (user.world.isClient) return
        (user as? PlayerEntity)?.takeIf { it.isAlive }?.let { playerEntity ->
            (target as? LivingEntity)?.takeIf { it.isAlive }?.let { livingEntity ->
                leech(playerEntity, level, livingEntity)
            }
        }
    }

    private fun leech(playerEntity: PlayerEntity, level: Int, livingEntity: LivingEntity) {
        val life = livingEntity.health
        playerEntity.heal(life * (level / 100F))
    }

}

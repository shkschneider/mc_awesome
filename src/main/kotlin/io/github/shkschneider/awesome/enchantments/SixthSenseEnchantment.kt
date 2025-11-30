package io.github.shkschneider.awesome.enchantments

import com.google.common.base.Predicates
import io.github.shkschneider.awesome.core.AwesomeEnchantment
import io.github.shkschneider.awesome.core.AwesomeUtils
import io.github.shkschneider.awesome.core.ext.toBox
import io.github.shkschneider.awesome.custom.Minecraft
import net.minecraft.enchantment.EnchantmentTarget
import net.minecraft.entity.Entity
import net.minecraft.entity.EquipmentSlot
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.entity.player.PlayerEntity
import kotlin.math.sqrt

/**
 * Inspiration: https://github.com/Majrusz/MajruszsEnchantmentsMod
 */
class SixthSenseEnchantment : AwesomeEnchantment(
    id = AwesomeUtils.identifier("sixth_sense"),
    Rarity.UNCOMMON,
    levels = 1 to 1,
    EnchantmentTarget.ARMOR_HEAD,
    listOf(EquipmentSlot.HEAD),
) {

    override fun onUserDamaged(user: LivingEntity, attacker: Entity, level: Int) {
        if (user.world.isClient) return
        (user as? PlayerEntity)?.let(::invoke)
    }

    companion object {

        operator fun invoke(player: PlayerEntity) {
            val box = player.blockPos.toBox(radius = (Minecraft.CHUNK * sqrt(Minecraft.CHUNK.toDouble())))
            player.world.getEntitiesByClass(LivingEntity::class.java, box, Predicates.alwaysTrue()).forEach { entity ->
                if (entity.uuid != player.uuid && entity !is PlayerEntity) {
                    entity.addStatusEffect(StatusEffectInstance(StatusEffects.GLOWING, Minecraft.TICKS, 1))
                }
            }
        }

    }

}

package io.github.shkschneider.awesome.experience.effects

import io.github.shkschneider.awesome.core.AwesomeEffect
import io.github.shkschneider.awesome.core.AwesomeLogger
import net.minecraft.entity.Entity
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.effect.StatusEffectCategory
import net.minecraft.entity.player.PlayerEntity

class ExperienceEffect(
    val level: Int,
) : AwesomeEffect.Instant("experience${level}", StatusEffectCategory.BENEFICIAL) {

    // does not trigger using /effect give command
    override fun invoke(source: Entity?, attacker: Entity?, target: LivingEntity, amplifier: Int, proximity: Double) {
        if (target is PlayerEntity && target.isAlive) {
            AwesomeLogger.debug("experience+=$level")
            target.addExperienceLevels(level)
        }
    }

}

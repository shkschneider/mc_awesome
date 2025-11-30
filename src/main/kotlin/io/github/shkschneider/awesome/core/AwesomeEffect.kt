package io.github.shkschneider.awesome.core

import net.minecraft.entity.Entity
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.effect.StatusEffect
import net.minecraft.entity.effect.StatusEffectCategory

sealed class AwesomeEffect(
    open val name: String,
    type: StatusEffectCategory,
    color: Int = type.formatting.colorValue ?: 0x000000,
) : StatusEffect(type, color) {

    protected fun init() {
        AwesomeRegistries.statusEffect(name, this)
    }

    abstract class Instant(
        override val name: String,
        type: StatusEffectCategory,
        color: Int = type.formatting.colorValue ?: 0x000000,
    ) : AwesomeEffect(name, type, color) {

        init {
            init()
        }

        abstract fun invoke(source: Entity?, attacker: Entity?, target: LivingEntity, amplifier: Int, proximity: Double)

        override fun applyInstantEffect(source: Entity?, attacker: Entity?, target: LivingEntity, amplifier: Int, proximity: Double) {
            if (target.world.isClient) return super.applyInstantEffect(source, attacker, target, amplifier, proximity)
            invoke(source, attacker, target, amplifier, proximity)
            super.applyInstantEffect(source, attacker, target, amplifier, proximity)
        }

        override fun isInstant(): Boolean {
            return true
        }

    }

    abstract class Continuous(
        override val name: String,
        type: StatusEffectCategory,
        color: Int = type.formatting.colorValue ?: 0x000000,
    ) : AwesomeEffect(name, type, color) {

        init {
            init()
        }

        override fun applyUpdateEffect(entity: LivingEntity, amplifier: Int) {
            if (entity.world.isClient) return super.applyUpdateEffect(entity, amplifier)
            invoke(entity, amplifier)
            super.applyUpdateEffect(entity, amplifier)
        }

        abstract fun invoke(entity: LivingEntity, amplifier: Int)

        override fun canApplyUpdateEffect(duration: Int, amplifier: Int): Boolean {
            return true
        }

        override fun isInstant(): Boolean {
            return false
        }

    }

}

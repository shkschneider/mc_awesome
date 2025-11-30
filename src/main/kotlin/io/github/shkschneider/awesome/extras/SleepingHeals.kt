package io.github.shkschneider.awesome.extras

import io.github.shkschneider.awesome.custom.Event
import net.fabricmc.fabric.api.entity.event.v1.EntitySleepEvents
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.effect.StatusEffects

object SleepingHeals {

    operator fun invoke() {
        @Event("EntitySleepEvents.StopSleeping")
        EntitySleepEvents.STOP_SLEEPING.register(EntitySleepEvents.StopSleeping { livingEntity, _ ->
            if (livingEntity.isPlayer) {
                invoke(livingEntity)
            }
        })
    }

    operator fun invoke(livingEntity: LivingEntity) {
        livingEntity.addStatusEffect(StatusEffectInstance(StatusEffects.INSTANT_HEALTH, 5))
        livingEntity.addStatusEffect(StatusEffectInstance(StatusEffects.WEAKNESS, 5))
    }

}

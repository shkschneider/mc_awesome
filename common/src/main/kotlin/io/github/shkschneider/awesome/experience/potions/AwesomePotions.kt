package io.github.shkschneider.awesome.experience.potions

import io.github.shkschneider.awesome.core.AwesomePotion
import io.github.shkschneider.awesome.experience.effects.ExperienceEffect
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.item.Items

object AwesomePotions {

    operator fun invoke() {
        val experience1 = ExperienceEffect(1)
        val experience9 = ExperienceEffect(9)
        AwesomePotion("experience1", Items.LAPIS_LAZULI, StatusEffectInstance(experience1))
        AwesomePotion("experience9", Items.LAPIS_BLOCK, StatusEffectInstance(experience9))
    }

}

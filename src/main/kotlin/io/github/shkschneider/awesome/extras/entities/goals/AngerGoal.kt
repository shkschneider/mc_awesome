package io.github.shkschneider.awesome.extras.entities.goals

import io.github.shkschneider.awesome.core.ext.isBeingLookedAt
import io.github.shkschneider.awesome.custom.Minecraft
import net.minecraft.entity.ai.goal.Goal
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.entity.mob.HostileEntity
import java.util.EnumSet

class AngerGoal(
    private val mob: HostileEntity,
    private val range: Int,
) : Goal() {

    companion object {

        private const val TIMING = 5

    }

    init {
        controls = EnumSet.of(Control.LOOK)
    }

    override fun canStart(): Boolean {
        return mob.target?.let { target ->
            return@let mob.distanceTo(target) <= range.toDouble()
        } ?: false
    }

    override fun start() {
        mob.isAttacking = true
    }

    override fun shouldContinue(): Boolean = canStart()

    override fun tick() {
        mob.target?.let { target ->
            if (mob.random.nextInt(toGoalTicks(TIMING)) == 0) {
                if (mob.isBeingLookedAt(target)) {
                    target.addStatusEffect(StatusEffectInstance(StatusEffects.INSTANT_DAMAGE, TIMING), mob)
                }
            }
            if (mob.random.nextInt(toGoalTicks(TIMING * TIMING)) == 0) {
                target.addStatusEffect(StatusEffectInstance(StatusEffects.MINING_FATIGUE, Minecraft.TICKS * TIMING), mob)
            }
            mob.lookControl.lookAt(target.x, mob.eyeY, target.z)
        }
    }

    override fun stop() {
        mob.isAttacking = false
    }

}

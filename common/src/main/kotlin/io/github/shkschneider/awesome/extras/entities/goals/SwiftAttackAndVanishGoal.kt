package io.github.shkschneider.awesome.extras.entities.goals

import net.minecraft.entity.ai.goal.Goal
import net.minecraft.entity.mob.HostileEntity
import net.minecraft.entity.player.PlayerEntity

class SwiftAttackAndVanishGoal(
    private val mob: HostileEntity,
    private val range: Int,
) : Goal() {

    override fun canStart(): Boolean {
        return mob.target?.let { target ->
            return@let mob.distanceTo(target) < range.toDouble()
        } ?: false
    }

    override fun start() {
        mob.isAttacking = false
        (mob.target as? PlayerEntity)?.let { player ->
            mob.onPlayerCollision(player)
        }
    }

    override fun shouldContinue(): Boolean = false

    override fun stop() {
        mob.isAttacking = false
        mob.discard()
    }

}

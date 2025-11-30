package io.github.shkschneider.awesome.extras.entities.goals

import net.minecraft.entity.ai.goal.Goal
import net.minecraft.entity.mob.HostileEntity
import java.util.EnumSet

class StareAtPlayerGoal constructor(
    private val mob: HostileEntity,
) : Goal() {

    init {
        controls = EnumSet.of(Control.LOOK)
    }

    override fun canStart(): Boolean {
        return mob.target != null
    }

    override fun start() {
        mob.navigation.stop()
    }

    override fun shouldContinue(): Boolean = canStart()

    override fun tick() {
        mob.target?.let { target ->
            mob.lookControl.lookAt(target.x, mob.eyeY, target.z)
        }
    }

}

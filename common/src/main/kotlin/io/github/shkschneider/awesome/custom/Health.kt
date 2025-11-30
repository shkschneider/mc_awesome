package io.github.shkschneider.awesome.custom

import net.minecraft.util.math.MathHelper.clamp
import kotlin.math.roundToInt

object Health {

    private const val HEARTS = 10
    private const val MIN = 0
    private const val MAX = 20

    fun hearts(health: Float): Int =
        clamp(MIN, MAX, (health / (MAX / HEARTS)).roundToInt())

}

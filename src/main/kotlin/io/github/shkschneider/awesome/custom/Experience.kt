package io.github.shkschneider.awesome.custom

import kotlin.math.roundToInt

object Experience {

    // https://minecraft.fandom.com/wiki/Experience
    fun experienceFromLevel(level: Int): Int =
        when {
            level >= 32 -> (4.5 * (level * level) - 162.5 * level + 2220).roundToInt()
            level >= 17 -> (2.5 * (level * level) - 40.5 * level + 360).roundToInt()
            level >= 1 -> (level * level) + 6 * level
            else -> 0
        }

    fun experienceFromLevel2Level(low: Int, high: Int): Int =
        experienceFromLevel(high) - experienceFromLevel(low)

    fun levelsFromExperience(experience: Int, startingFrom: Int = 0): Int {
        var level = 0
        var xp = experience
        while (xp > 0) {
            xp -= experienceFromLevel(++level)
        }
        return level -1
    }

}

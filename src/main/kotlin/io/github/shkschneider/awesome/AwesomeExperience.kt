package io.github.shkschneider.awesome

import com.google.common.math.BigIntegerMath
import com.google.common.math.LongMath
import io.github.shkschneider.awesome.experience.enchantments.ExperienceEnchantment
import io.github.shkschneider.awesome.experience.gamerules.KeepXpGameRule
import io.github.shkschneider.awesome.experience.obelisk.Obelisk
import io.github.shkschneider.awesome.experience.potions.AwesomePotions
import java.math.BigInteger
import java.math.RoundingMode
import kotlin.math.pow
import kotlin.math.sqrt

object AwesomeExperience {

    // https://github.com/Meridanus/fabric_xp_storage_1.18/blob/master/src/main/java/com/notker/xp_storage/XpFunctions.java
    fun levelFromXp(xp: Long): Int = when {
        xp > Long.MAX_VALUE / 72 -> BigIntegerMath.sqrt(
            BigInteger.valueOf(xp).multiply(BigInteger.valueOf(72)).subtract(BigInteger.valueOf(54215)), RoundingMode.DOWN,
        ).add(BigInteger.valueOf(325)).divide(BigInteger.valueOf(18)).intValueExact()
        xp > Int.MAX_VALUE -> ((LongMath.sqrt(72 * xp - 54215, RoundingMode.DOWN) + 325) / 18).toInt()
        xp > 1395 -> ((sqrt((72 * xp - 54215).toDouble()) + 325) / 18).toInt()
        xp > 315 -> (sqrt((40 * xp - 7839).toDouble()) / 10 + 8.1).toInt()
        xp > 0 -> (sqrt((xp + 9).toDouble()) - 3).toInt()
        else -> 0
    }

    // https://github.com/Meridanus/fabric_xp_storage_1.18/blob/master/src/main/java/com/notker/xp_storage/XpFunctions.java
    fun xpFromLevel(level: Int): Long = when {
        level in 1..16 -> (level.toDouble().pow(2.0) + 6 * level).toLong()
        level in 17..31 -> (2.5 * level.toDouble().pow(2.0) - 40.5 * level + 360).toLong()
        level >= 32 -> (4.5 * level.toDouble().pow(2.0) - 162.5 * level + 2220).toLong()
        else -> 0
    }

    operator fun invoke() {
        KeepXpGameRule()
        if (Awesome.CONFIG.experience.obelisk) Obelisk()
        if (Awesome.CONFIG.experience.oneXpPerBlock) ExperienceEnchantment()
        if (Awesome.CONFIG.experience.potions) AwesomePotions()
    }

}

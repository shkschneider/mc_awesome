package io.github.shkschneider.awesome.core

import io.github.shkschneider.awesome.Awesome
import io.github.shkschneider.awesome.custom.Location
import io.github.shkschneider.awesome.custom.Minecraft
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.util.Identifier
import kotlin.math.pow
import kotlin.math.sqrt

object AwesomeUtils {

    const val BLOCK_ENTITY_TAG = "BlockEntityTag"

    fun identifier(id: String) = Identifier("${Awesome.ID}:$id")

    fun key(vararg k: String): String = "${Awesome.ID}_${k.joinToString(separator = "_")}"

    fun translatable(vararg k: String): String = "${k.first()}.${Awesome.ID}.${k.drop(1).joinToString(separator = ".")}"

    fun secondsToTicks(s: Int): Int = s * Minecraft.TICKS

    fun ticksToSeconds(t: Int): Int = t / Minecraft.TICKS

    fun isModLoaded(id: String): Boolean =
        FabricLoader.getInstance().isModLoaded(id)

    // https://minecraft.fandom.com/wiki/Tutorials/Measuring_distance#Euclidean_distance_in_3_dimensions_(including_elevation)
    fun distance(l1: Location, l2: Location): Double {
        if (l1.key != l2.key) return Double.MAX_VALUE
        return sqrt(
            (l1.x - l2.x).pow(2) + (l1.y - l2.y).pow(2) + (l1.z - l2.z).pow(2)
        )
    }

    fun humanReadable(n: Long): String =
        when {
            n < 1_000 -> String.format("%d", n)
            n < 1_000_000 -> String.format("%.01fK", n.toFloat() / 1_000)
            n < 1_000_000_000 -> String.format("%.01fM", n.toFloat() / 1_000_000)
            else -> "INF."
        }

}

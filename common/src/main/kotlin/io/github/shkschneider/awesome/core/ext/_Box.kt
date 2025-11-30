package io.github.shkschneider.awesome.core.ext

import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Box
import kotlin.math.roundToInt

fun Box.positions(): List<BlockPos> = buildList {
    val xx = (minX.roundToInt() until maxX.roundToInt())
    val yy = (minY.roundToInt() until maxY.roundToInt())
    val zz = (minZ.roundToInt() until maxZ.roundToInt())
    xx.forEach { x ->
        yy.forEach { y ->
            zz.forEach { z ->
                add(BlockPos(x, y, z))
            }
        }
    }
}

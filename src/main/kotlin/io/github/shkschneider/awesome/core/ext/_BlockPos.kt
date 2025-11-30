package io.github.shkschneider.awesome.core.ext

import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Box
import net.minecraft.util.math.Vec3d

fun BlockPos.copy(x: Int? = null, y: Int? = null, z: Int? = null): BlockPos =
    BlockPos(x ?: this.x, y ?: this.y, z ?: this.z)

fun BlockPos.sides(): List<BlockPos> =
    listOf(this.up(), this.down(), this.north(), this.south(), this.east(), this.west())

fun BlockPos.toVec3d() =
    Vec3d(x.toDouble(), y.toDouble(), z.toDouble())

fun BlockPos.toBox(radius: Double = 1.0) =
    Box.of(toVec3d().center(), radius, radius, radius)

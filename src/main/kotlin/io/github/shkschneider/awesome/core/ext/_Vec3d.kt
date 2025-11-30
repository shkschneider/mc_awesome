package io.github.shkschneider.awesome.core.ext

import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Vec3d
import net.minecraft.util.math.Vec3f
import kotlin.math.roundToInt

fun Vec3d.center() =
    Vec3d(x.roundToInt() + 0.5, y.roundToInt() + 0.5, z.roundToInt() + 0.5)

fun Vec3d.toVec3f() =
    Vec3f(this.x.toFloat(), this.y.toFloat(), this.z.toFloat())

fun Vec3d.toBlockPos() =
    BlockPos(this.x, this.y, this.z)

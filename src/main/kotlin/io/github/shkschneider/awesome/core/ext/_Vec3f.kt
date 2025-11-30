package io.github.shkschneider.awesome.core.ext

import net.minecraft.util.math.Vec3d
import net.minecraft.util.math.Vec3f

fun Vec3f.toVec3d(): Vec3d =
    Vec3d(this.x.toDouble(), this.y.toDouble(), this.z.toDouble())

package io.github.shkschneider.awesome.core

import io.github.shkschneider.awesome.core.ext.toBlockPos
import io.github.shkschneider.awesome.core.ext.toVec3f
import net.minecraft.particle.DustParticleEffect
import net.minecraft.particle.ParticleEffect
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.math.Vec3d
import net.minecraft.world.World

object AwesomeParticles {

    private operator fun invoke(world: World, pos: Vec3d, particleEffect: ParticleEffect) {
        world.addParticle(particleEffect, pos.x, pos.y, pos.z, 0.0, 0.0, 0.0)
    }

    operator fun invoke(world: World, pos: BlockPos, direction: Direction = Direction.values().random(), color: Int, offset: Double = 0.0) {
        if (!world.getBlockState(pos.offset(direction)).isOpaqueFullCube(world, pos.offset(direction))) {
            val e = if (direction.axis === Direction.Axis.X) 0.5 + offset * direction.offsetX.toDouble() else world.random.nextFloat().toDouble()
            val f = if (direction.axis === Direction.Axis.Y) 0.5 + offset * direction.offsetY.toDouble() else world.random.nextFloat().toDouble()
            val g = if (direction.axis === Direction.Axis.Z) 0.5 + offset * direction.offsetZ.toDouble() else world.random.nextFloat().toDouble()
            invoke(world, Vec3d(pos.x.toDouble() + e, pos.y.toDouble() + f, pos.z.toDouble() + g), DustParticleEffect(Vec3d.unpackRgb(color).toVec3f(), 1.0F))
        }
    }

    operator fun invoke(world: World, pos: Vec3d, direction: Direction = Direction.values().random(), color: Int, offset: Double = 0.0) {
        invoke(world, pos.toBlockPos(), direction, color, offset)
    }

}

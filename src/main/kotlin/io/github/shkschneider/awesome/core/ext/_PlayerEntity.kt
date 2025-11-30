package io.github.shkschneider.awesome.core.ext

import io.github.shkschneider.awesome.custom.Minecraft
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.util.hit.HitResult

fun PlayerEntity.lookingAt(maxDistance: Double = Minecraft.CHUNK / 2.toDouble(), includeFluids: Boolean = false): HitResult? =
    this.raycast(maxDistance, 0.toFloat(), includeFluids)
        .takeIf { it.type in listOf(HitResult.Type.BLOCK, HitResult.Type.ENTITY) }

package io.github.shkschneider.awesome.core

import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvent
import net.minecraft.sound.SoundEvents
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

object AwesomeSounds {

    val crop = SoundEvents.BLOCK_CROP_BREAK to SoundCategory.AMBIENT
    val experience = SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP to SoundCategory.PLAYERS
    val teleport = SoundEvents.ENTITY_ENDERMAN_TELEPORT to SoundCategory.PLAYERS
    val vinesPlaced = SoundEvents.BLOCK_CAVE_VINES_PLACE to SoundCategory.AMBIENT
    val vinesBroken = SoundEvents.BLOCK_CAVE_VINES_BREAK to SoundCategory.AMBIENT

    operator fun invoke(where: Pair<World, BlockPos>, sound: Pair<SoundEvent, SoundCategory>, volume: Float = 1.0F, pitch: Float = 1.0F): Boolean {
        val world = where.first
        val pos = where.second
        if (world.isClient) return false
        world.playSound(null, pos, sound.first, sound.second, volume, pitch)
        return true
    }

}

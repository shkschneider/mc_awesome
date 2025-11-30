package io.github.shkschneider.awesome.extras.elevator

import io.github.shkschneider.awesome.Awesome
import io.github.shkschneider.awesome.core.AwesomeLogger
import io.github.shkschneider.awesome.core.AwesomeSounds
import io.github.shkschneider.awesome.core.ext.copy
import io.github.shkschneider.awesome.custom.Minecraft
import io.github.shkschneider.awesome.mixins.ElevatorCooldownMixin
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction

object Elevator {

    private lateinit var _block: ElevatorBlock
    val block: ElevatorBlock get() = _block

    operator fun invoke() {
        if (Awesome.CONFIG.extras.elevator) { _block = ElevatorBlock() }
    }

    private fun check(livingEntity: LivingEntity): Boolean =
        livingEntity.isAlive && livingEntity is PlayerEntity
                && (livingEntity as ElevatorCooldownMixin).jumpingCooldown == 0
                && livingEntity.world.getBlockState(livingEntity.blockPos.down())?.block == block

    private fun search(player: PlayerEntity, direction: Direction): BlockPos? {
        val world = player.world
        val at = player.blockPos
        return when (direction) {
            Direction.UP -> (at.y + 2 until world.topY - 2)
            Direction.DOWN -> (at.y - 2 downTo world.bottomY + 2)
            else -> throw IllegalArgumentException()
        }.firstOrNull { y ->
            world.getBlockState(at.copy(y = y))?.block == block
        }?.let { y ->
            at.copy(y = y + 1)
        }
    }

    fun onSneak(livingEntity: LivingEntity) {
        if (livingEntity.world.isClient || !check(livingEntity)) return
        val player = livingEntity as PlayerEntity
        search(player, Direction.DOWN)?.let { target ->
            teleport(player, target)
        }
    }

    fun onJump(livingEntity: LivingEntity) {
        if (livingEntity.world.isClient || !check(livingEntity)) return
        val player = livingEntity as PlayerEntity
        search(player, Direction.UP)?.let { target ->
            teleport(player, target)
        }
    }

    private fun teleport(player: PlayerEntity, target: BlockPos) {
        AwesomeLogger.debug("Teleporting to $target...")
        (player as ElevatorCooldownMixin).jumpingCooldown = Minecraft.TICKS
        player.requestTeleportAndDismount(target.x + 0.5, target.y.toDouble() + 0.25, target.z + 0.5)
        AwesomeSounds(player.world to target, AwesomeSounds.teleport)
    }

}

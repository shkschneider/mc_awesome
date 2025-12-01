package io.github.shkschneider.awesome.core

import io.github.shkschneider.awesome.custom.Event
import net.fabricmc.fabric.api.entity.event.v1.EntitySleepEvents
import net.minecraft.SharedConstants
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.text.Text
import net.minecraft.text.Texts
import net.minecraft.util.Formatting
import net.minecraft.world.World

object AwesomeTime {

    val ticksPerRealLifeSecond: Int = SharedConstants.TICKS_PER_SECOND
    val ticksPerRealLifeMinute: Int = ticksPerRealLifeSecond * 60
    val ticksPerRealLifeHour: Int = ticksPerRealLifeMinute * 60

    val ticksPerInGameDay: Int = SharedConstants.TICKS_PER_IN_GAME_DAY
    val ticksPerInGameHour: Int = ticksPerInGameDay / 24
    val ticksPerInGameMinute: Int = ticksPerInGameHour / 60
    // ticksPerInGameSecond < 1

    fun day(world: World): Long =
        world.time / ticksPerInGameDay

    operator fun invoke() {
        @Event("EntitySleepEvents.StartSleeping")
        EntitySleepEvents.START_SLEEPING.register(EntitySleepEvents.StartSleeping { livingEntity, _ ->
            (livingEntity as? PlayerEntity)?.let { player ->
                player.world.players.filterNot { it.uuid == player.uuid }.forEach { otherPlayer ->
                    AwesomeChat.message(otherPlayer, Texts.join(listOf(
                        Texts.toText(player.gameProfile),
                        Text.translatable(AwesomeUtils.translatable("ui", "wants_to_sleep")).formatted(Formatting.GRAY),
                    ), Text.of(" ")))
                }
            }
        })
        @Event("EntitySleepEvents.StopSleeping")
        EntitySleepEvents.STOP_SLEEPING.register(EntitySleepEvents.StopSleeping { livingEntity, _ ->
            (livingEntity as? PlayerEntity)?.let { player ->
                AwesomeChat.overlay(player, Texts.join(listOf(
                    Text.translatable(AwesomeUtils.translatable("ui", "new_day")).formatted(Formatting.WHITE),
                    Text.literal(day(player.world).toString()).formatted(Formatting.BOLD).formatted(Formatting.YELLOW),
                ), Text.of(" ")))
            }
        })
    }

}

package io.github.shkschneider.awesome.extras

import io.github.shkschneider.awesome.Awesome
import io.github.shkschneider.awesome.core.AwesomeClock
import io.github.shkschneider.awesome.core.AwesomeLogger
import io.github.shkschneider.awesome.core.AwesomeTime
import io.github.shkschneider.awesome.core.ext.clock
import io.github.shkschneider.awesome.custom.Event
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents
import net.minecraft.world.GameRules
import net.minecraft.world.World

object ZenithNadirLengths {

    private val TAG = ZenithNadirLengths::class.java.simpleName

    private val ZENITH_LENGTH: Int = (Awesome.CONFIG.extras.zenithLengthInDays * AwesomeTime.ticksPerInGameDay).toInt()
    private val NADIR_LENGTH: Int = (Awesome.CONFIG.extras.nadirLengthInDays * AwesomeTime.ticksPerInGameHour).toInt()

    private const val STANDBY = -1
    private var progress: Int = STANDBY

    init {
        check(ZENITH_LENGTH >= 0.0F)
        check(NADIR_LENGTH >= 0.0F)
    }

    operator fun invoke() {
        @Event("ServerTickEvents.EndWorldTick")
        ServerTickEvents.END_WORLD_TICK.register(ServerTickEvents.EndWorldTick { world ->
            val clock = world.clock()
            // safety: you could change the time while this ticks
            if (progress >= 0 && clock.isZenith.not() && clock.isNadir.not()) {
                progress = STANDBY
                return@EndWorldTick
            }
            // safety: you could change the gamerule while this ticks
            if (progress > 0 && world.gameRules.getBoolean(GameRules.DO_DAYLIGHT_CYCLE)) {
                progress = STANDBY
                return@EndWorldTick
            }
            // done
            if (progress == 0) {
                doDaylightCycle(world, clock)
                return@EndWorldTick
            }
            // waiting
            if (progress == STANDBY && world.gameRules.getBoolean(GameRules.DO_DAYLIGHT_CYCLE)) {
                if (clock.isZenith) dontDaylightCycle(world, clock, ZENITH_LENGTH)
                if (clock.isNadir) dontDaylightCycle(world, clock, NADIR_LENGTH)
                return@EndWorldTick
            }
            // running
            if (progress > 0) {
                progress--
            }
        })
    }

    private fun doDaylightCycle(world: World, clock: AwesomeClock) {
        if (!world.gameRules.getBoolean(GameRules.DO_DAYLIGHT_CYCLE)) {
            AwesomeLogger.info("$TAG@$clock doDaylightCycle")
            world.gameRules.get(GameRules.DO_DAYLIGHT_CYCLE).set(true, world.server)
        }
        progress = 0
    }

    private fun dontDaylightCycle(world: World, clock: AwesomeClock, duration: Int) {
        if (world.gameRules.getBoolean(GameRules.DO_DAYLIGHT_CYCLE)) {
            AwesomeLogger.info("$TAG@$clock dontDaylightCycle")
            world.gameRules.get(GameRules.DO_DAYLIGHT_CYCLE).set(false, world.server)
        }
        progress = duration
    }

}

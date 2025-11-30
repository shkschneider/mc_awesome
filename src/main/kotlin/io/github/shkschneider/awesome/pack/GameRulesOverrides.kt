package io.github.shkschneider.awesome.pack

import io.github.shkschneider.awesome.custom.Event
import io.github.shkschneider.awesome.custom.Minecraft
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents
import net.minecraft.world.GameRules

object GameRulesOverrides {

    operator fun invoke() {
        @Event("ServerWorldEvents.Load")
        ServerWorldEvents.LOAD.register(ServerWorldEvents.Load { server, world ->
            if (world.time < Minecraft.TICKS) {
                world.gameRules.get(GameRules.DO_DAYLIGHT_CYCLE).set(true, server)
                world.gameRules.get(GameRules.DO_FIRE_TICK).set(false, server)
                world.gameRules.get(GameRules.DO_IMMEDIATE_RESPAWN).set(false, server)
                world.gameRules.get(GameRules.DO_INSOMNIA).set(false, server)
                world.gameRules.get(GameRules.DO_MOB_GRIEFING).set(false, server)
                // 1.19 world.gameRules.get(GameRules.LAVA_SOURCE_CONVERSION).set(true, server)
                world.gameRules.get(GameRules.PLAYERS_SLEEPING_PERCENTAGE).set(50, server)
                world.gameRules.get(GameRules.REDUCED_DEBUG_INFO).set(Minecraft.isDevelopment.not(), server)
                // 1.19 world.gameRules.get(GameRules.WATER_SOURCE_CONVERSION).set(true, server)
            }
        })
    }

}

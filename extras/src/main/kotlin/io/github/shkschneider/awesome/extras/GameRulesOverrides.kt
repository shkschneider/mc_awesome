package io.github.shkschneider.awesome.extras

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents
import net.minecraft.world.GameRules

object GameRulesOverrides {

    operator fun invoke() {
        ServerWorldEvents.LOAD.register(ServerWorldEvents.Load { server, world ->
            // awesome
            world.gameRules.get(KeepXpGameRule.key).set(true, server)
            // vanilla
            world.gameRules.get(GameRules.DO_FIRE_TICK).set(false, server)
            world.gameRules.get(GameRules.DO_MOB_GRIEFING).set(false, server)
            world.gameRules.get(GameRules.DO_MOB_GRIEFING).set(false, server)
            world.gameRules.get(GameRules.DO_DAYLIGHT_CYCLE).set(true, server)
            world.gameRules.get(GameRules.DO_IMMEDIATE_RESPAWN).set(false, server)
            world.gameRules.get(GameRules.PLAYERS_SLEEPING_PERCENTAGE).set(50, server)
        })
    }

}

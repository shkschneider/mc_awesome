package io.github.shkschneider.awesome.pack

import io.github.shkschneider.awesome.custom.Event
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents

object DeathCounter {

    private const val NAME = "Deaths"

    operator fun invoke() {
        @Event("ServerLifecycleEvents.ServerStarted")
        ServerLifecycleEvents.SERVER_STARTED.register(ServerLifecycleEvents.ServerStarted { server ->
            if (server.scoreboard.objectives.none { it.name == NAME }) {
                // couldn't get it to display using server.scoreboard
                server.commandManager.execute(server.commandSource, "/scoreboard objectives add $NAME deathCount")
                server.commandManager.execute(server.commandSource, "/scoreboard objectives setdisplay list $NAME")
            }
        })
    }

}

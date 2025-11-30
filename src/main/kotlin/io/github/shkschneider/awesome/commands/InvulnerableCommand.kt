package io.github.shkschneider.awesome.commands

import com.mojang.brigadier.context.CommandContext
import io.github.shkschneider.awesome.core.AwesomeCommand
import io.github.shkschneider.awesome.custom.Permissions
import net.minecraft.server.command.ServerCommandSource
import net.minecraft.server.network.ServerPlayerEntity

class InvulnerableCommand : AwesomeCommand.ForPlayers("invulnerable", Permissions.GameMaster) {

    override fun run(context: CommandContext<ServerCommandSource>, players: List<ServerPlayerEntity>): Int {
        players.forEach { player ->
            if (!player.abilities.invulnerable) {
                player.abilities.invulnerable = true
                feedback(context.source, "${player.name.string} is now invulnerable!", broadcastToOps = true)
            } else {
                player.abilities.invulnerable = false
                feedback(context.source, "${player.name.string} is vulnerable again!", broadcastToOps = true)
            }
            player.sendAbilitiesUpdate()
        }
        return SUCCESS
    }

}

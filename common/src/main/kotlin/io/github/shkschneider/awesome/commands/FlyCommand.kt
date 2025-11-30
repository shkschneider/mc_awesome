package io.github.shkschneider.awesome.commands

import com.mojang.brigadier.context.CommandContext
import io.github.shkschneider.awesome.core.AwesomeCommand
import io.github.shkschneider.awesome.custom.Permissions
import net.minecraft.server.command.ServerCommandSource
import net.minecraft.server.network.ServerPlayerEntity

class FlyCommand : AwesomeCommand.ForPlayers("fly", Permissions.GameMaster) {

    override fun run(context: CommandContext<ServerCommandSource>, players: List<ServerPlayerEntity>): Int {
        players.forEach { player ->
            if (!player.abilities.allowFlying) {
                player.abilities.allowFlying = true
                // player.abilities.flySpeed = max(player.abilities.walkSpeed, player.abilities.flySpeed)
                feedback(context.source, "${player.name.string} can now fly!", broadcastToOps = true)
            } else {
                player.abilities.allowFlying = false
                player.abilities.flying = false
                feedback(context.source, "${player.name.string} cannot fly anymore!", broadcastToOps = true)
            }
            player.sendAbilitiesUpdate()
        }
        return SUCCESS
    }

}

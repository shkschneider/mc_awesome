package io.github.shkschneider.awesome.commands

import com.mojang.brigadier.context.CommandContext
import io.github.shkschneider.awesome.core.AwesomeCommand
import io.github.shkschneider.awesome.custom.Permissions
import net.minecraft.server.command.ServerCommandSource
import net.minecraft.server.network.ServerPlayerEntity

class HealCommand : AwesomeCommand.ForPlayers("heal", Permissions.GameMaster) {

    override fun run(context: CommandContext<ServerCommandSource>, players: List<ServerPlayerEntity>): Int {
        players.forEach { player ->
            feedback(context.source, "Healing ${player.gameProfile.name}...", broadcastToOps = true)
            player.heal(player.maxHealth)
            player.hungerManager.foodLevel = player.maxHealth.toInt()
        }
        return SUCCESS
    }

}

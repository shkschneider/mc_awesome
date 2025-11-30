package io.github.shkschneider.awesome.commands

import com.mojang.brigadier.context.CommandContext
import io.github.shkschneider.awesome.core.AwesomeCommand
import io.github.shkschneider.awesome.custom.Permissions
import net.minecraft.server.command.ServerCommandSource
import net.minecraft.server.network.ServerPlayerEntity

class RepairCommand : AwesomeCommand.ForPlayer("repair", Permissions.GameMaster) {

    override fun run(context: CommandContext<ServerCommandSource>, player: ServerPlayerEntity): Int {
        feedback(context.source, "Repairing ${player.gameProfile.name}...", broadcastToOps = true)
        player.mainHandStack.takeIf { it.isDamageable && it.isDamaged }?.damage = 0
        return SUCCESS
    }

}

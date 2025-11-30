package io.github.shkschneider.awesome.commands

import com.mojang.brigadier.context.CommandContext
import io.github.shkschneider.awesome.core.AwesomeChat
import io.github.shkschneider.awesome.core.AwesomeCommand
import io.github.shkschneider.awesome.custom.Permissions
import net.minecraft.server.command.ServerCommandSource
import net.minecraft.util.Formatting

class BroadcastCommand : AwesomeCommand.WithText("broadcast", Permissions.Admin) {

    override fun run(context: CommandContext<ServerCommandSource>, text: String): Int {
        context.source.world.players.forEach { player ->
            AwesomeChat.overlay(player, AwesomeChat.text(text)
                .formatted(Formatting.BOLD)
                .formatted(Formatting.RED)
            )
        }
        return SUCCESS
    }

}

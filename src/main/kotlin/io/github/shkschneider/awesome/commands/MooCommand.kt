package io.github.shkschneider.awesome.commands

import com.mojang.brigadier.context.CommandContext
import io.github.shkschneider.awesome.core.AwesomeCommand
import io.github.shkschneider.awesome.custom.Permissions
import net.minecraft.server.command.ServerCommandSource

class MooCommand : AwesomeCommand("moo", Permissions.Anyone) {

    override fun run(context: CommandContext<ServerCommandSource>): Int {
        feedback(context.source, "Have you mooed today?")
        return SUCCESS
    }

}

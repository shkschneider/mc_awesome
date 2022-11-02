package io.github.shkschneider.awesome.commands

import com.mojang.brigadier.context.CommandContext
import io.github.shkschneider.awesome.custom.Permissions
import net.minecraft.server.command.ServerCommandSource
import net.minecraft.text.Text

class MooCommand : AwesomeCommand("moo", Permissions.Player) {

    override fun run(context: CommandContext<ServerCommandSource>?): Int {
        context?.source?.sendFeedback(Text.of("Have you mooed today?"), false)
        return 1
    }

}

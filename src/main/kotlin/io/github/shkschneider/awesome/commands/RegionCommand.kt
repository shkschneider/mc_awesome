package io.github.shkschneider.awesome.commands

import com.mojang.brigadier.context.CommandContext
import io.github.shkschneider.awesome.core.AwesomeCommand
import io.github.shkschneider.awesome.custom.Permissions
import net.minecraft.server.command.ServerCommandSource
import net.minecraft.text.Text

class RegionCommand : AwesomeCommand("region", Permissions.Admin) {

    override fun run(context: CommandContext<ServerCommandSource>): Int {
        val player = context.source.player
        val chunk = player.world.getChunk(player.blockPos)
        println(player.world.dimension.toString())
        context.source.sendFeedback(Text.of("r.${chunk.pos.regionX}.${chunk.pos.regionZ}"), false)
        return 0
    }

}

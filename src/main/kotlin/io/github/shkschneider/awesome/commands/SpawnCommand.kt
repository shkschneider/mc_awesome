package io.github.shkschneider.awesome.commands

import com.mojang.brigadier.context.CommandContext
import io.github.shkschneider.awesome.core.AwesomeCommand
import io.github.shkschneider.awesome.core.ext.spawn
import io.github.shkschneider.awesome.custom.IEntityData
import io.github.shkschneider.awesome.custom.Location.Companion.writeLocation
import io.github.shkschneider.awesome.custom.Permissions
import net.minecraft.server.command.ServerCommandSource

class SpawnCommand : AwesomeCommand("spawn", Permissions.Anyone) {

    override fun run(context: CommandContext<ServerCommandSource>): Int {
        val player = context.source?.player ?: return error(context.source, code = -1)
        val location = player.world.spawn()
        // back
        (player as IEntityData).writeLocation("back")
        // spawn
        feedback(context.source, "Teleporting to spawn @ $location...")
        player.teleport(player.server.getWorld(location.key), location.x, location.y, location.z, location.yaw, location.pitch)
        return SUCCESS
    }

}

package io.github.shkschneider.awesome.commands

import com.mojang.brigadier.context.CommandContext
import io.github.shkschneider.awesome.core.AwesomeCommand
import io.github.shkschneider.awesome.custom.IEntityData
import io.github.shkschneider.awesome.custom.Location.Companion.clearLocation
import io.github.shkschneider.awesome.custom.Location.Companion.readLocation
import io.github.shkschneider.awesome.custom.Permissions
import net.minecraft.server.command.ServerCommandSource

class BackCommand : AwesomeCommand("back", Permissions.Anyone) {

    override fun run(context: CommandContext<ServerCommandSource>): Int {
        val player = context.source?.player ?: return error(context.source, code = -1)
        if (player.isInTeleportationState || player.isOnGround.not()) return error(context.source, "You must be on solid ground!", code = -2)
        val data = (player as IEntityData).data
        val location = data.readLocation("back") ?: run {
            return error(context.source, "Nowhere to go back to...", -3)
        }
        data.clearLocation("back")
        feedback(context.source, "Teleporting back @ $location...")
        player.teleport(player.server.getWorld(location.key), location.x, location.y, location.z, location.yaw, location.pitch)
        return SUCCESS
    }

}

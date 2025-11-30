package io.github.shkschneider.awesome.commands

import com.mojang.brigadier.context.CommandContext
import io.github.shkschneider.awesome.core.AwesomeCommand
import io.github.shkschneider.awesome.custom.IEntityData
import io.github.shkschneider.awesome.custom.Location.Companion.writeLocation
import io.github.shkschneider.awesome.custom.Permissions
import io.github.shkschneider.awesome.custom.SavedHomes
import net.minecraft.server.command.ServerCommandSource

class HomeCommand : AwesomeCommand("home", Permissions.Anyone) {

    override fun run(context: CommandContext<ServerCommandSource>): Int {
        val player = context.source?.player ?: return error(context.source, code = -1)
        if (player.isInTeleportationState || player.isOnGround.not()) return error(context.source, "You must be on solid ground!", code = -2)
        // read
        val location = SavedHomes.getClientState(player) ?: return error(context.source, "Homeless...", -3)
        // back
        (player as? IEntityData)?.writeLocation("back")
        // teleport
        feedback(context.source, "Teleporting home @ $location...")
        player.teleport(player.server.getWorld(location.key), location.x, location.y, location.z, location.yaw, location.pitch)
        return SUCCESS
    }

}

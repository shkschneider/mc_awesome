package io.github.shkschneider.awesome.commands

import com.mojang.brigadier.context.CommandContext
import io.github.shkschneider.awesome.core.AwesomeCommand
import io.github.shkschneider.awesome.custom.Location
import io.github.shkschneider.awesome.custom.Permissions
import io.github.shkschneider.awesome.custom.SavedHomes
import net.minecraft.server.command.ServerCommandSource

class SetHomeCommand : AwesomeCommand("sethome", Permissions.Anyone) {

    override fun run(context: CommandContext<ServerCommandSource>): Int {
        val player = context.source?.player ?: return error(context.source, code = -1)
        if (player.isInTeleportationState || player.isOnGround.not()) return error(context.source, "You must be on solid ground!", code = -2)
        // write
        val currentLocation = Location(player.world.registryKey, player.x, player.y, player.z, player.yaw, player.pitch)
        SavedHomes.getServerState(context.source.server)?.save(player, currentLocation) ?: return error(context.source, "Could not save your location!", code = -3)
        feedback(context.source, "Home set @ $currentLocation!")
        return SUCCESS
    }

}

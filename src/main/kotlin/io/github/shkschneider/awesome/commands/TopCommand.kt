package io.github.shkschneider.awesome.commands

import com.mojang.brigadier.context.CommandContext
import io.github.shkschneider.awesome.core.AwesomeCommand
import io.github.shkschneider.awesome.custom.IEntityData
import io.github.shkschneider.awesome.custom.Location
import io.github.shkschneider.awesome.custom.Location.Companion.writeLocation
import io.github.shkschneider.awesome.custom.Permissions
import net.minecraft.server.command.ServerCommandSource
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class TopCommand : AwesomeCommand.ForPlayer("top", Permissions.GameMaster) {

    override fun run(context: CommandContext<ServerCommandSource>, player: ServerPlayerEntity): Int {
        if (player.isOnGround.not()) return error(context.source, code = -2)
        val world = player.world
        var position = player.blockPos.up()
        while (valid(world, position).not()) {
            position = position.up()
        }
        if (!valid(world, position)) return error(context.source, code = -3)
        // back
        (player as IEntityData).writeLocation("back")
        // up
        val location = Location(player.world.registryKey, position.x.toDouble(), position.y.toDouble(), position.z.toDouble(), player.yaw, player.pitch)
        feedback(context.source, "Teleporting up @ $location...")
        player.teleport(player.server.getWorld(location.key), location.x, location.y, location.z, location.yaw, location.pitch)
        return SUCCESS
    }

    private fun valid(world: World, pos: BlockPos) =
        world.isOutOfHeightLimit(pos.up()).not()
                && world.getBlockState(pos.up()).isAir
                && world.getBlockState(pos).isAir
                && world.getBlockState(pos.down()).isAir.not()

}

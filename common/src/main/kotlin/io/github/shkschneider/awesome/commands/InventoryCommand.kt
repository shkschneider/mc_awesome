package io.github.shkschneider.awesome.commands

import com.mojang.brigadier.context.CommandContext
import io.github.shkschneider.awesome.core.AwesomeCommand
import io.github.shkschneider.awesome.custom.Permissions
import net.minecraft.screen.GenericContainerScreenHandler
import net.minecraft.screen.SimpleNamedScreenHandlerFactory
import net.minecraft.server.command.ServerCommandSource
import net.minecraft.server.network.ServerPlayerEntity

class InventoryCommand : AwesomeCommand.ForPlayer("inventory", Permissions.GameMaster) {

    // FIXME closes at the moment it opens while multiplayer
    override fun run(context: CommandContext<ServerCommandSource>, player: ServerPlayerEntity): Int {
        val user = context.source?.player ?: return error(context.source, code = -1)
        user.openHandledScreen(SimpleNamedScreenHandlerFactory({ syncId, playerInventory, _ ->
            GenericContainerScreenHandler.createGeneric9x3(syncId, playerInventory, player.inventory)
        }, player.name))
        return 0
    }

}

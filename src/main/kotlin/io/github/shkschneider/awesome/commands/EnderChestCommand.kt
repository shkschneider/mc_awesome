package io.github.shkschneider.awesome.commands

import com.mojang.brigadier.context.CommandContext
import io.github.shkschneider.awesome.core.AwesomeCommand
import io.github.shkschneider.awesome.custom.Permissions
import net.minecraft.block.Blocks
import net.minecraft.screen.GenericContainerScreenHandler
import net.minecraft.screen.SimpleNamedScreenHandlerFactory
import net.minecraft.server.command.ServerCommandSource

class EnderChestCommand : AwesomeCommand("enderchest", Permissions.GameMaster) {

    override fun run(context: CommandContext<ServerCommandSource>): Int {
        val player = context.source?.player ?: return error(context.source, code = -1)
        val enderChest = player.enderChestInventory
        player.openHandledScreen(SimpleNamedScreenHandlerFactory({ syncId, playerInventory, _ ->
            GenericContainerScreenHandler.createGeneric9x3(syncId, playerInventory, enderChest)
        }, Blocks.ENDER_CHEST.name))
        return 0
    }

}

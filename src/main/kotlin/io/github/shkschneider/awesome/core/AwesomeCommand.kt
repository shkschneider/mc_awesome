package io.github.shkschneider.awesome.core

import com.mojang.brigadier.arguments.StringArgumentType.getString
import com.mojang.brigadier.context.CommandContext
import io.github.shkschneider.awesome.custom.Permissions
import net.minecraft.command.argument.EntityArgumentType
import net.minecraft.server.command.ServerCommandSource
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.util.Formatting

abstract class AwesomeCommand(
    val name: String,
    permission: Permissions = Permissions.Anyone,
) {

    companion object {

        const val SUCCESS = 1

    }

    init {
        AwesomeRegistries.command(name, permission, ::run)
    }

    abstract fun run(context: CommandContext<ServerCommandSource>): Int

    fun message(source: ServerCommandSource, txt: String) {
        // 1.19 source.sendMessage(AwesomeChat.text(txt).formatted(Formatting.WHITE))
        feedback(source, txt)
    }

    fun feedback(source: ServerCommandSource, txt: String, broadcastToOps: Boolean = false) {
        source.sendFeedback(AwesomeChat.text(txt).formatted(Formatting.GRAY), broadcastToOps)
    }

    fun error(source: ServerCommandSource, txt: String? = null, code: Int): Int {
        check(code < 0)
        source.sendError(AwesomeChat.text(txt ?: "Error $code").formatted(Formatting.RED))
        return code
    }

    fun broadcast(source: ServerCommandSource, txt: String) {
        source.world.players.forEach { player ->
            AwesomeChat.message(player, AwesomeChat.text(txt).formatted(Formatting.GRAY))
        }
    }

    abstract class WithText(
        name: String,
        permission: Permissions = Permissions.Anyone,
    ) : AwesomeCommand(name, permission) {

        init {
            AwesomeRegistries.commandWithText(name, permission, ::run)
        }

        override fun run(context: CommandContext<ServerCommandSource>): Int {
            return run(context, getString(context, "text"))
        }

        abstract fun run(context: CommandContext<ServerCommandSource>, text: String): Int

    }

    abstract class ForPlayer(
        name: String,
        permission: Permissions = Permissions.Anyone,
    ) : AwesomeCommand(name, permission) {

        init {
            AwesomeRegistries.commandForPlayer(name, permission, ::run)
        }

        override fun run(context: CommandContext<ServerCommandSource>): Int {
            return try {
                run(context, EntityArgumentType.getPlayer(context, "player"))
            } catch (e: IllegalArgumentException) {
                run(context, requireNotNull(context.source?.player))
            } catch (e: NullPointerException) {
                error(context.source, code = -1)
            }
        }

        abstract fun run(context: CommandContext<ServerCommandSource>, player: ServerPlayerEntity): Int

    }

    abstract class ForPlayers(
        name: String,
        permission: Permissions = Permissions.Anyone,
    ) : AwesomeCommand(name, permission) {

        init {
            AwesomeRegistries.commandForPlayers(name, permission, ::run)
        }

        override fun run(context: CommandContext<ServerCommandSource>): Int {
            return try {
                run(context, EntityArgumentType.getPlayers(context, "players").toList())
            } catch (e: IllegalArgumentException) {
                run(context, requireNotNull(context.source.world.players))
            } catch (e: NullPointerException) {
                error(context.source, code = -1)
            }
        }

        abstract fun run(context: CommandContext<ServerCommandSource>, players: List<ServerPlayerEntity>): Int

    }

}

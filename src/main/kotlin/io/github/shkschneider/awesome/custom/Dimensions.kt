package io.github.shkschneider.awesome.custom

import io.github.shkschneider.awesome.core.AwesomeChat
import io.github.shkschneider.awesome.core.AwesomeUtils
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientChunkEvents
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents
import net.minecraft.client.MinecraftClient
import net.minecraft.text.Text
import net.minecraft.text.Texts
import net.minecraft.util.Formatting
import net.minecraft.util.Identifier
import net.minecraft.world.World
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

object Dimensions {

    const val NETHER = -1
    const val OVERWORLD = 0
    const val END = 1
    const val UNKNOWN = Int.MIN_VALUE

    private var CURRENT: Identifier = World.OVERWORLD.value

    operator fun invoke() {
        if (Minecraft.isClient) {
            @Event("ClientPlayConnectionEvents.Join")
            ClientPlayConnectionEvents.JOIN.register(ClientPlayConnectionEvents.Join { _, _, _ ->
                @Event("ClientChunkEvents.Load")
                ClientChunkEvents.CHUNK_LOAD.register(ClientChunkEvents.Load { world, _ ->
                    val current = world.registryKey.value
                    if (current != CURRENT) {
                        CURRENT = world.registryKey.value
                        // delay because the client ticks while the world is still loading
                        Executors.newSingleThreadScheduledExecutor().schedule(Runnable {
                            MinecraftClient.getInstance().player?.let { player ->
                                AwesomeChat.overlay(
                                    player, Texts.join(
                                        listOf(
                                            Text.literal(
                                                "${current.namespace}:".lowercase().replaceFirstChar { it.uppercaseChar() })
                                                .formatted(Formatting.ITALIC).formatted(Formatting.WHITE),
                                            Text.literal(current.path.uppercase()).formatted(Formatting.BOLD)
                                                .formatted(Formatting.YELLOW),
                                        ), Text.of(" ")
                                    )
                                )
                            }
                        }, AwesomeUtils.ticksToSeconds(Minecraft.TICKS).toLong(), TimeUnit.SECONDS)
                    }
                })
            })
        }
    }

}

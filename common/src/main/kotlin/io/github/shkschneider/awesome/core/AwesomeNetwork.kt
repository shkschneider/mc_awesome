package io.github.shkschneider.awesome.core

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
import net.minecraft.network.PacketByteBuf
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.util.Identifier

object AwesomeNetwork {

    // client-to-server
    fun c2s(id: Identifier, packet: PacketByteBuf) {
        ClientPlayNetworking.send(id, packet)
    }

    // server-to-client
    fun s2c(id: Identifier, player: ServerPlayerEntity, packet: PacketByteBuf) {
        ServerPlayNetworking.send(player, id, packet)
    }

}

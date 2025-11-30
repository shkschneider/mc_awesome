package io.github.shkschneider.awesome.custom

import io.github.shkschneider.awesome.Awesome
import io.github.shkschneider.awesome.custom.Location.Companion.readLocation
import io.github.shkschneider.awesome.custom.Location.Companion.writeLocation
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.nbt.NbtCompound
import net.minecraft.server.MinecraftServer
import net.minecraft.world.PersistentState
import net.minecraft.world.World
import java.util.UUID

class SavedHomes : PersistentState() {

    var homes: MutableMap<UUID, Location> = mutableMapOf()

    fun save(playerEntity: PlayerEntity, location: Location) {
        homes[playerEntity.uuid] = location
        markDirty()
    }

    override fun writeNbt(nbt: NbtCompound): NbtCompound {
        val globalNbt = NbtCompound()
        homes.forEach { (uuid, location) ->
            val localNbt = NbtCompound().apply { writeLocation(location, LOCAL_PREFIX) }
            globalNbt.put(uuid.toString(), localNbt)
        }
        nbt.put(GLOBAL_PREFIX, globalNbt)
        return nbt
    }

    companion object {

        private const val LOCAL_PREFIX = "home"
        private const val GLOBAL_PREFIX = "homes"

        fun getServerState(server: MinecraftServer): SavedHomes? {
            // World.OVERWORLD stores the Location (which specifies the correct world's registry key)
            val manager = server.getWorld(World.OVERWORLD)?.persistentStateManager ?: return null
            val state = manager.getOrCreate({ nbt -> readNbt(nbt) }, { SavedHomes() }, Awesome.ID) ?: return null
            state.markDirty() // mandatory
            return state
        }

        fun getClientState(playerEntity: PlayerEntity): Location? =
            playerEntity.world.server?.let { server -> getServerState(server)?.homes?.get(playerEntity.uuid) }

        private fun readNbt(nbt: NbtCompound): SavedHomes {
            val state = SavedHomes()
            val globalNbt = nbt.getCompound(GLOBAL_PREFIX)
            globalNbt.keys.forEach { key ->
                globalNbt.getCompound(key).readLocation(LOCAL_PREFIX)?.let { location ->
                    state.homes[UUID.fromString(key)] = location
                }
            }
            return state
        }

    }

}

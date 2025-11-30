package io.github.shkschneider.awesome.custom

import io.github.shkschneider.awesome.core.AwesomeUtils
import net.minecraft.entity.Entity
import net.minecraft.nbt.NbtCompound
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import net.minecraft.util.registry.RegistryKey
import net.minecraft.world.World
import kotlin.math.roundToInt

data class Location(
    val key: RegistryKey<World>,
    val x: Double,
    val y: Double,
    val z: Double,
    val yaw: Float = 0F,
    val pitch: Float = 0F,
) {

    override fun toString(): String {
        return "${key.value.path}:${x.roundToInt()},${y.roundToInt()},${z.roundToInt()}"
    }

    fun safe() = copy(x = x.roundToInt() + 0.5, y = y.roundToInt() + 0.25, z = z.roundToInt() + 0.5)

    companion object {

        fun <T : IEntityData> T.writeLocation(prefix: String): Location? {
            val entity = (this as? Entity) ?: return null
            val location = Location(entity.world.registryKey, entity.x, entity.y, entity.z, entity.yaw, entity.pitch)
            data.writeLocation(location, prefix)
            return location
        }

        fun NbtCompound.writeLocation(location: Location, prefix: String): NbtCompound {
            this.putString(AwesomeUtils.key(prefix, "dim"), location.key.value.toString())
            this.putDouble(AwesomeUtils.key(prefix, "x"), location.x)
            this.putDouble(AwesomeUtils.key(prefix, "y"), location.y)
            this.putDouble(AwesomeUtils.key(prefix, "z"), location.z)
            this.putFloat(AwesomeUtils.key(prefix, "yaw"), location.yaw)
            this.putFloat(AwesomeUtils.key(prefix, "pitch"), location.pitch)
            return this
        }

        fun NbtCompound.readLocation(prefix: String): Location? {
            if (
                !contains(AwesomeUtils.key(prefix, "dim"))
                || !contains(AwesomeUtils.key(prefix, "x"))
                || !contains(AwesomeUtils.key(prefix, "y"))
                || !contains(AwesomeUtils.key(prefix, "z"))
            ) return null
            return Location(
                RegistryKey.of(Registry.WORLD_KEY, Identifier.tryParse(getString(AwesomeUtils.key(prefix, "dim")))),
                getDouble(AwesomeUtils.key(prefix, "x")),
                getDouble(AwesomeUtils.key(prefix, "y")),
                getDouble(AwesomeUtils.key(prefix, "z")),
                getFloat(AwesomeUtils.key(prefix, "yaw")),
                getFloat(AwesomeUtils.key(prefix, "pitch")),
            )
        }

        fun NbtCompound.clearLocation(prefix: String) {
            remove(AwesomeUtils.key(prefix, "dim"))
            remove(AwesomeUtils.key(prefix, "x"))
            remove(AwesomeUtils.key(prefix, "y"))
            remove(AwesomeUtils.key(prefix, "z"))
            remove(AwesomeUtils.key(prefix, "yaw"))
            remove(AwesomeUtils.key(prefix, "pitch"))
        }

    }

}

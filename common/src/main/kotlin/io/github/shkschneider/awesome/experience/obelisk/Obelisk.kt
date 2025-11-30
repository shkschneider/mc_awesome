package io.github.shkschneider.awesome.experience.obelisk

import io.github.shkschneider.awesome.custom.Minecraft
import net.minecraft.state.property.IntProperty

object Obelisk {

    val LEVELS: IntProperty get() = IntProperty.of("levels", 0, Minecraft.STACK)

    operator fun invoke() {
        ObeliskBlock()
    }

}

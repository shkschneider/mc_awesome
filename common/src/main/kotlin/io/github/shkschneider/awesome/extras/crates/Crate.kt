package io.github.shkschneider.awesome.extras.crates

import io.github.shkschneider.awesome.core.AwesomeRegistries
import io.github.shkschneider.awesome.core.AwesomeUtils
import io.github.shkschneider.awesome.custom.Minecraft
import net.minecraft.util.Identifier

class Crate(
    val size: Crates.Sizes,
) {

    val id: Identifier = AwesomeUtils.identifier("${size.name}_crate")
    val block  = CrateBlock(this)
    val screen = AwesomeRegistries.screen(id.path) { syncId, playerInventory ->
        CrateScreen.Handler(this, syncId, playerInventory)
    }

    init {
        if (Minecraft.isClient) AwesomeRegistries.screenHandler(screen) { handler, playerInventory, title ->
            CrateScreen(this, handler, playerInventory, title)
        }
    }

}

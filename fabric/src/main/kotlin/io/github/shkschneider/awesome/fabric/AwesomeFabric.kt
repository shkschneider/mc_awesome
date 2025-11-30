package io.github.shkschneider.awesome.fabric

import io.github.shkschneider.awesome.AwesomeMod
import net.fabricmc.api.ModInitializer

/**
 * Fabric entrypoint for the Awesome mod.
 * Delegates to the common initialization code.
 */
class AwesomeFabric : ModInitializer {

    override fun onInitialize() {
        AwesomeMod.init()
    }

}

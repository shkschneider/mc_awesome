package io.github.shkschneider.awesome.neoforge

import io.github.shkschneider.awesome.AwesomeMod
import net.neoforged.fml.common.Mod

/**
 * NeoForge entrypoint for the Awesome mod.
 * Delegates to the common initialization code.
 */
@Mod(AwesomeMod.MOD_ID)
class AwesomeNeoForge {

    init {
        AwesomeMod.init()
    }

}

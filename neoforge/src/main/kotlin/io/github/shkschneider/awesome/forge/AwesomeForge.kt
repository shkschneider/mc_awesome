package io.github.shkschneider.awesome.forge

import io.github.shkschneider.awesome.AwesomeMod
import net.minecraftforge.fml.common.Mod

/**
 * Forge entrypoint for the Awesome mod.
 * Delegates to the common initialization code.
 */
@Mod(AwesomeMod.MOD_ID)
class AwesomeForge {

    init {
        AwesomeMod.init()
    }

}

package io.github.shkschneider.awesome

import net.fabricmc.api.ModInitializer

/**
 * Unified Awesome mod - combines all modules into a single mod.
 * Includes: Core, Crystals, Experience, Extras, Commands, Enchantments, Pack
 */
class AwesomeMod : ModInitializer {

    override fun onInitialize() {
        // Core (base functionality, config, utilities)
        AwesomeCore()
        
        // Crystals (budding blocks for ores)
        AwesomeCrystals()
        
        // Experience (XP management, obelisk, potions)
        AwesomeExperience()
        
        // Extras (various additions: elevator, crates, rope, etc.)
        AwesomeExtras()
        
        // Commands (admin and player commands)
        AwesomeCommands()
        
        // Enchantments (custom enchantments)
        AwesomeEnchantments()
        
        // Pack (data pack features, loot, recipes)
        AwesomePack()
    }

}

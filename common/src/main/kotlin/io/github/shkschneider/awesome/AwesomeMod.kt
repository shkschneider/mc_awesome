package io.github.shkschneider.awesome

/**
 * Common Awesome mod initialization - combines all modules into a single mod.
 * Includes: Core, Crystals, Experience, Extras, Commands, Enchantments, Pack
 *
 * This is called by platform-specific entrypoints (Fabric/NeoForge)
 */
object AwesomeMod {

    const val MOD_ID = "awesome"
    const val MOD_NAME = "Awesome"

    fun init() {
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

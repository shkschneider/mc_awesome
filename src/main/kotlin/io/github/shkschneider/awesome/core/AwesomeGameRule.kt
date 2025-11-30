package io.github.shkschneider.awesome.core

import net.minecraft.world.GameRules
import net.minecraft.world.World

abstract class AwesomeGameRule(
    val name: String,
    category: GameRules.Category,
    default: Boolean,
) {

    private lateinit var _key: GameRules.Key<GameRules.BooleanRule>
    open val key: GameRules.Key<GameRules.BooleanRule> get() = _key

    init {
        AwesomeRegistries.gameRule(name, category, default)
    }

    fun check(world: World): Boolean {
        return world.gameRules.getBoolean(key)
    }

}

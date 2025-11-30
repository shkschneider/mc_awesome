package io.github.shkschneider.awesome.experience.gamerules

import io.github.shkschneider.awesome.core.AwesomeRegistries
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.world.GameRules

object KeepXpGameRule {

    private const val ID = "keepXp"

    private lateinit var _key: GameRules.Key<GameRules.BooleanRule>
    val key get() = _key

    operator fun invoke() {
        _key = AwesomeRegistries.gameRule(ID, GameRules.Category.PLAYER, true)
    }

    operator fun invoke(oldPlayer: ServerPlayerEntity, newPlayer: ServerPlayerEntity) {
        newPlayer.experienceLevel = oldPlayer.experienceLevel
        newPlayer.totalExperience = oldPlayer.totalExperience
        newPlayer.experienceProgress = oldPlayer.experienceProgress
    }

}

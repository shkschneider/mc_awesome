package io.github.shkschneider.awesome

import io.github.shkschneider.awesome.pack.DeathCounter
import io.github.shkschneider.awesome.pack.GameRulesOverrides
import io.github.shkschneider.awesome.pack.PlayerHeads
import io.github.shkschneider.awesome.pack.PlayerHealth

object AwesomePack {

    operator fun invoke() {
        DeathCounter()
        GameRulesOverrides()
        PlayerHeads()
        PlayerHealth()
    }

}


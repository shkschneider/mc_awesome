package io.github.shkschneider.awesome.core

import net.minecraft.entity.player.PlayerEntity
import net.minecraft.text.Text

object AwesomeChat {

    fun text(txt: String) = Text.literal(txt)

    fun message(player: PlayerEntity, text: Text) {
        player.sendMessage(text, false)
    }

    fun overlay(player: PlayerEntity, text: Text) {
        player.sendMessage(text, true)
    }

}

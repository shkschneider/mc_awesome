package io.github.shkschneider.awesome

import io.github.shkschneider.awesome.extras.Baguette
import io.github.shkschneider.awesome.extras.LilyPad
import io.github.shkschneider.awesome.extras.Omelette
import io.github.shkschneider.awesome.extras.Rope
import io.github.shkschneider.awesome.extras.Scythe
import io.github.shkschneider.awesome.extras.SleepingHeals
import io.github.shkschneider.awesome.extras.ZenithNadirLengths
import io.github.shkschneider.awesome.extras.crates.Crates
import io.github.shkschneider.awesome.extras.elevator.Elevator
import io.github.shkschneider.awesome.extras.entities.AwesomeEntities
import io.github.shkschneider.awesome.extras.randomium.Randomium
import io.github.shkschneider.awesome.extras.tool.AwesomeTools
import io.github.shkschneider.awesome.extras.void.Void

object AwesomeExtras {

    operator fun invoke() {
        if (Awesome.CONFIG.extras.allInOneTools) AwesomeTools()
        if (Awesome.CONFIG.extras.baguette) Baguette()
        if (Awesome.CONFIG.extras.crates) Crates()
        if (Awesome.CONFIG.extras.elevator) Elevator()
        if (Awesome.CONFIG.extras.entities) AwesomeEntities()
        if (Awesome.CONFIG.extras.lilypad) LilyPad()
        if (Awesome.CONFIG.extras.omelette) Omelette()
        if (Awesome.CONFIG.extras.randomium) Randomium()
        if (Awesome.CONFIG.extras.rope) Rope()
        if (Awesome.CONFIG.extras.scythe) Scythe()
        if (Awesome.CONFIG.extras.sleepingHeals) SleepingHeals()
        if (Awesome.CONFIG.extras.void) Void()
        if (Awesome.CONFIG.extras.zenithLengthInDays + Awesome.CONFIG.extras.nadirLengthInDays > 0F) ZenithNadirLengths()
    }

}

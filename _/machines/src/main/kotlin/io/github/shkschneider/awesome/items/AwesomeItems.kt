package io.github.shkschneider.awesome.items

import io.github.shkschneider.awesome.Awesome

object AwesomeItems {

    operator fun invoke() {
        if (Awesome.CONFIG.machines.imprisoner) Imprisoner()
        if (Awesome.CONFIG.machines.prospector) Prospector()
    }

}

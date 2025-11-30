package io.github.shkschneider.awesome

import io.github.shkschneider.awesome.core.AwesomeTime
import io.github.shkschneider.awesome.custom.Dimensions

object AwesomeCore {

    operator fun invoke() {
        Awesome()
        AwesomeTime()
        if (Awesome.CONFIG.dimensions) Dimensions()
    }

}

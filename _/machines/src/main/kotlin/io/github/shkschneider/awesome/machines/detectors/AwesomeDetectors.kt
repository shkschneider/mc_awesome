package io.github.shkschneider.awesome.machines.detectors

object AwesomeDetectors {

    operator fun invoke() {
        EntityDetector()
        TimeDetector()
        WeatherDetector()
    }

}

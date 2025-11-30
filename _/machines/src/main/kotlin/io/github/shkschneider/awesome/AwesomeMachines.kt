package io.github.shkschneider.awesome

import io.github.shkschneider.awesome.items.AwesomeItems
import io.github.shkschneider.awesome.machines.breaker.Breaker
import io.github.shkschneider.awesome.machines.collector.Collector
import io.github.shkschneider.awesome.machines.crafter.Crafter
import io.github.shkschneider.awesome.machines.cultivator.Cultivator
import io.github.shkschneider.awesome.machines.detectors.AwesomeDetectors
import io.github.shkschneider.awesome.machines.duplicator.Duplicator
import io.github.shkschneider.awesome.machines.factory.Factory
import io.github.shkschneider.awesome.machines.placer.Placer
import io.github.shkschneider.awesome.machines.quarry.Quarry
import io.github.shkschneider.awesome.machines.recycler.Recycler
import io.github.shkschneider.awesome.machines.refinery.Refinery

object AwesomeMachines {

    val fuel = Awesome.flux

    val breaker = Breaker()
    val collector = Collector()
    val cultivator = Cultivator()
    val placer = Placer()
    val refinery = Refinery()

    private lateinit var _crafter: Crafter
    val crafter get() = _crafter
    private lateinit var _duplicator: Duplicator
    val duplicator get() = _duplicator
    private lateinit var _factory: Factory
    val factory get() = _factory
    private lateinit var _quarry: Quarry
    val quarry get() = _quarry
    private lateinit var _recycler: Recycler
    val recycler get() = _recycler

    operator fun invoke() {
        AwesomeDetectors()
        if (Awesome.CONFIG.machines.crafter) _crafter = Crafter()
        if (Awesome.CONFIG.machines.duplicator) _duplicator = Duplicator()
        if (Awesome.CONFIG.machines.factory) _factory = Factory()
        if (Awesome.CONFIG.machines.quarry) _quarry = Quarry()
        if (Awesome.CONFIG.machines.recycler) _recycler = Recycler()
        AwesomeItems()
    }

}

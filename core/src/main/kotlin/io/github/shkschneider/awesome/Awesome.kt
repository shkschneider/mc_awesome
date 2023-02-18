package io.github.shkschneider.awesome

import io.github.shkschneider.awesome.core.AwesomeConfigFactory
import io.github.shkschneider.awesome.core.AwesomeRegistries
import io.github.shkschneider.awesome.core.AwesomeUtils
import io.github.shkschneider.awesome.custom.Flux
import io.github.shkschneider.awesome.custom.FluxDust
import net.minecraft.item.ItemGroup

object Awesome {

    val ID = Awesome::class.java.simpleName.lowercase()

    val flux = Flux()

    val GROUP: ItemGroup = AwesomeRegistries.group(AwesomeUtils.identifier(ID), FluxDust()).also {
        AwesomeRegistries.group(it, flux)
    }

    val CONFIG = AwesomeConfigFactory<AwesomeConfig>(ID)(AwesomeConfig::class.java)

    operator fun invoke() = Unit

}

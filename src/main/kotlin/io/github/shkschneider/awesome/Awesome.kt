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
    private val fluxDust = FluxDust()

    val GROUP: ItemGroup = AwesomeRegistries.group(AwesomeUtils.identifier(ID), fluxDust).also { group ->
        AwesomeRegistries.group(group, flux)
        AwesomeRegistries.group(group, fluxDust)
    }

    val CONFIG = AwesomeConfigFactory<AwesomeConfig>(ID)(AwesomeConfig::class.java)

    operator fun invoke() = Unit

}

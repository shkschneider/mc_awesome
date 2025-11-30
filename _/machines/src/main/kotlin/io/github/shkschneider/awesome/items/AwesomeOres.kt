package io.github.shkschneider.awesome.items

import io.github.shkschneider.awesome.Awesome
import io.github.shkschneider.awesome.core.AwesomeItem
import io.github.shkschneider.awesome.core.AwesomeUtils
import net.minecraft.item.ItemGroup
import net.minecraft.util.Identifier

sealed class AwesomeOres(
    id: Identifier,
    settings: Settings,
    group: ItemGroup,
) : AwesomeItem(id, settings, group) {

    class Chip(
        name: String,
        settings: Settings,
        group: ItemGroup = Awesome.GROUP,
    ) : AwesomeOres(AwesomeUtils.identifier("${name}_chip"), settings, group)

    class Dust(
        name: String,
        settings: Settings,
        group: ItemGroup = Awesome.GROUP,
    ) : AwesomeOres(AwesomeUtils.identifier("${name}_dust"), settings, group)

    class Powder(
        name: String,
        settings: Settings,
        group: ItemGroup = Awesome.GROUP,
    ) : AwesomeOres(AwesomeUtils.identifier("${name}_powder"), settings, group)

    class Ingot(
        name: String,
        settings: Settings,
        group: ItemGroup = Awesome.GROUP,
    ) : AwesomeOres(AwesomeUtils.identifier("${name}_ingot"), settings, group)

}

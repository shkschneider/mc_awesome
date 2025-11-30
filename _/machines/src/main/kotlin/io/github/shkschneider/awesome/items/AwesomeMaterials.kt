package io.github.shkschneider.awesome.items

import io.github.shkschneider.awesome.Awesome
import io.github.shkschneider.awesome.core.AwesomeItem
import io.github.shkschneider.awesome.core.AwesomeUtils
import net.minecraft.item.ItemGroup
import net.minecraft.util.Identifier

sealed class AwesomeMaterials(
    id: Identifier,
    settings: Settings,
    group: ItemGroup,
) : AwesomeItem(id, settings, group) {

    class Frame(
        name: String,
        settings: Settings,
        group: ItemGroup = Awesome.GROUP,
    ) : AwesomeMaterials(AwesomeUtils.identifier("${name}_frame"), settings, group)

    class Plate(
        name: String,
        settings: Settings,
        group: ItemGroup = Awesome.GROUP,
    ) : AwesomeMaterials(AwesomeUtils.identifier("${name}_plate"), settings, group)

    class Rod(
        name: String,
        settings: Settings,
        group: ItemGroup = Awesome.GROUP,
    ) : AwesomeMaterials(AwesomeUtils.identifier("${name}_rod"), settings, group)

}

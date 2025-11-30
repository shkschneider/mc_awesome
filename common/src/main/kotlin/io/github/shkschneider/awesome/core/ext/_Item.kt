package io.github.shkschneider.awesome.core.ext

import net.minecraft.item.Item
import net.minecraft.util.Identifier

fun Item.id(): Identifier {
    // item.modId.itemId
    val key = translationKey.split(".").drop(1)
    // modId:itemId
    return Identifier(key.first(), key.last())
}

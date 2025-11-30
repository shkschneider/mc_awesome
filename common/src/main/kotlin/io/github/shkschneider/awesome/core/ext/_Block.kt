package io.github.shkschneider.awesome.core.ext

import net.minecraft.block.Block
import net.minecraft.util.Identifier

fun Block.id(): Identifier {
    // item.modId.itemId
    val key = translationKey.split(".").drop(1)
    // modId:itemId
    return Identifier(key.first(), key.last())
}

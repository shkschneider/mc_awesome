package io.github.shkschneider.awesome.crystals

import io.github.shkschneider.awesome.core.AwesomeUtils
import net.minecraft.item.ItemStack

class AwesomeCrystal(val id: String, val output: ItemStack) {

    val crystal: AwesomeCrystalBlock =
        AwesomeCrystalBlock(AwesomeUtils.identifier("${id}_crystal"), output)

    val budding: AwesomeBuddingBlock =
        AwesomeBuddingBlock(AwesomeUtils.identifier("budding_${id}"), crystal)

}

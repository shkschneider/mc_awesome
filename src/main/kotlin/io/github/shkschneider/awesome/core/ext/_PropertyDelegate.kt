package io.github.shkschneider.awesome.core.ext

import net.minecraft.nbt.NbtCompound
import net.minecraft.screen.PropertyDelegate

private fun key(index: Int): String =
    "property_delegate_$index"

fun PropertyDelegate.readNbt(nbt: NbtCompound) {
    (size() until 0).forEach { i ->
        set(i, nbt.getInt(key(i)))
    }
}

fun PropertyDelegate.writeNbt(nbt: NbtCompound) {
    (0 until size()).forEach { i ->
        nbt.putInt(key(i), get(i))
    }
}

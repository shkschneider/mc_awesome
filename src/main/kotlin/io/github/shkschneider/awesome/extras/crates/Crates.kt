package io.github.shkschneider.awesome.extras.crates

import net.minecraft.block.BlockWithEntity
import net.minecraft.util.shape.VoxelShape

// Inspired from the ShulkerBox -- retains inventory
object Crates {

    sealed class Sizes(val name: String, val width: Int, val height: Int) {

        val total: Int  = width * height

        object Small : Sizes("small", 9, 1)
        object Medium : Sizes("medium", 9, 3)
        object Large : Sizes("large", 9, 6)

        val backgroundHeight: Int get() = when (this) {
            Small -> 130
            Medium -> 166
            Large -> 220
        }
        val playerInventoryOffset: Int get() = when (this) {
            Small -> 48
            Medium -> 84
            Large -> 138
        }
        val playerHotbarOffset: Int get() = when (this) {
            Small -> 106
            Medium -> 142
            Large -> 196
        }

        val shape: VoxelShape get() = when (this) {
            Small -> BlockWithEntity.createCuboidShape(2.0, 0.0, 2.0, 14.0, 12.0, 14.0)
            Medium -> BlockWithEntity.createCuboidShape(1.0, 0.0, 1.0, 15.0, 14.0, 15.0)
            Large -> BlockWithEntity.createCuboidShape(0.0, 0.0, 0.0, 16.0, 16.0, 16.0)
        }

    }

    operator fun invoke() {
        Crate(Sizes.Small)
        Crate(Sizes.Medium)
        Crate(Sizes.Large)
    }

}

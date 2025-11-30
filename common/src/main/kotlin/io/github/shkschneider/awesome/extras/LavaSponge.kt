package io.github.shkschneider.awesome.extras

import io.github.shkschneider.awesome.core.ext.positions
import net.minecraft.block.Blocks
import net.minecraft.tag.FluidTags
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Box
import net.minecraft.world.World

object LavaSponge {

    private const val RANGE = 6

    operator fun invoke(world: World, pos: BlockPos): Boolean {
        var i = 0
        Box(pos).expand(RANGE.toDouble()).positions().forEach { position ->
            if (world.getFluidState(position).isIn(FluidTags.LAVA)) {
                world.setBlockState(position, Blocks.AIR.defaultState)
                i++
            }
        }
        return i > 0
    }

}

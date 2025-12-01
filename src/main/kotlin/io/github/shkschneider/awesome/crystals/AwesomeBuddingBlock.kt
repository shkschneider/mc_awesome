package io.github.shkschneider.awesome.crystals

import io.github.shkschneider.awesome.Awesome
import io.github.shkschneider.awesome.core.AwesomeRegistries
import io.github.shkschneider.awesome.core.AwesomeUtils
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.block.BuddingAmethystBlock
import net.minecraft.block.piston.PistonBehavior
import net.minecraft.client.item.TooltipContext
import net.minecraft.item.ItemStack
import net.minecraft.server.world.ServerWorld
import net.minecraft.state.property.Properties
import net.minecraft.text.Text
import net.minecraft.util.Formatting
import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.math.random.Random
import net.minecraft.world.BlockView

class AwesomeBuddingBlock(
    protected val id: Identifier,
    protected val block: Block,
) : BuddingAmethystBlock(FabricBlockSettings.copyOf(Blocks.BUDDING_AMETHYST)) {

    init {
        init()
    }

    private fun init() {
        AwesomeRegistries.blockWithItem(id, this as Block, Awesome.GROUP)
    }

    override fun appendTooltip(stack: ItemStack, world: BlockView?, tooltip: MutableList<Text>, options: TooltipContext) {
        tooltip.add(Text.translatable(AwesomeUtils.translatable("block", id.path, "hint")).formatted(Formatting.GRAY))
    }

    override fun randomTick(state: BlockState, world: ServerWorld, blockPos: BlockPos, random: Random) {
        Direction.values().forEach { side ->
            val pos = blockPos.offset(side)
            if (world.getBlockState(pos).isAir) {
                world.setBlockState(pos, block.defaultState.with(Properties.FACING, side))
                return@forEach
            }
        }
    }

    override fun getPistonBehavior(state: BlockState): PistonBehavior = PistonBehavior.DESTROY

}

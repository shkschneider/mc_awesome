package io.github.shkschneider.awesome.extras.elevator

import io.github.shkschneider.awesome.core.AwesomeBlock
import io.github.shkschneider.awesome.core.AwesomeUtils
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.client.item.TooltipContext
import net.minecraft.entity.Entity
import net.minecraft.item.ItemStack
import net.minecraft.text.Text
import net.minecraft.util.Formatting
import net.minecraft.util.math.BlockPos
import net.minecraft.world.BlockView
import net.minecraft.world.World

class ElevatorBlock : AwesomeBlock(
    AwesomeUtils.identifier("elevator"), FabricBlockSettings.copyOf(Blocks.QUARTZ_BLOCK),
) {

    override fun appendTooltip(stack: ItemStack, world: BlockView?, tooltip: MutableList<Text>, options: TooltipContext) {
        tooltip.add(Text.translatable(AwesomeUtils.translatable("block", id.path, "hint")).formatted(Formatting.GRAY))
    }

    override fun onSteppedOn(world: World, pos: BlockPos, state: BlockState, entity: Entity) {
        if (world.isClient) return
    }

}

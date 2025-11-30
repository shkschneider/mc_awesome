package io.github.shkschneider.awesome.machines.detectors

import io.github.shkschneider.awesome.core.AwesomeBlockEntity
import io.github.shkschneider.awesome.core.AwesomeDetector
import io.github.shkschneider.awesome.core.AwesomeInputs
import io.github.shkschneider.awesome.core.AwesomeUtils
import io.github.shkschneider.awesome.custom.InputOutput
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.client.item.TooltipContext
import net.minecraft.item.ItemStack
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import net.minecraft.util.Formatting
import net.minecraft.util.math.BlockPos
import net.minecraft.world.BlockView
import net.minecraft.world.World

@Suppress("RemoveRedundantQualifierName")
class WeatherDetector : AwesomeDetector<WeatherDetector.Entity>("weather_detector") {

    companion object {

        private const val RAIN = 1
        private const val THUNDER = 2

    }

    override fun appendTooltip(stack: ItemStack, world: BlockView?, tooltip: MutableList<Text>, options: TooltipContext) {
        if (AwesomeInputs.shift()) {
            tooltip.add(TranslatableText(AwesomeUtils.translatable("block", id.path, "hint1")).formatted(Formatting.GRAY))
            tooltip.add(TranslatableText(AwesomeUtils.translatable("block", id.path, "hint2")).formatted(Formatting.GRAY))
        } else {
            tooltip.add(TranslatableText(AwesomeUtils.translatable("item", "hint")).formatted(Formatting.GRAY))
        }
    }

    override fun detect(world: World, pos: BlockPos, state: BlockState, blockEntity: WeatherDetector.Entity): Int {
        val raining = if (world.isRaining) RAIN else 0
        val thundering = if (world.isThundering) THUNDER else 0
        return raining + thundering
    }

    override fun createBlockEntity(pos: BlockPos, state: BlockState): Entity =
        WeatherDetector.Entity(id.path, this.entityType, pos, state)

    class Entity(
        id: String, type: BlockEntityType<Entity>, pos: BlockPos, state: BlockState,
    ) : AwesomeBlockEntity(id, type, pos, state, InputOutput(), 0 to 0)

}

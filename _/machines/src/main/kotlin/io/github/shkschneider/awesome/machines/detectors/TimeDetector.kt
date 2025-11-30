package io.github.shkschneider.awesome.machines.detectors

import io.github.shkschneider.awesome.core.AwesomeBlockEntity
import io.github.shkschneider.awesome.core.AwesomeDetector
import io.github.shkschneider.awesome.core.AwesomeInputs
import io.github.shkschneider.awesome.core.AwesomeUtils
import io.github.shkschneider.awesome.custom.InputOutput
import io.github.shkschneider.awesome.custom.Minecraft
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.client.item.TooltipContext
import net.minecraft.item.ItemStack
import net.minecraft.state.property.Properties
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import net.minecraft.util.Formatting
import net.minecraft.util.math.BlockPos
import net.minecraft.world.BlockView
import net.minecraft.world.World

@Suppress("RemoveRedundantQualifierName")
class TimeDetector : AwesomeDetector<TimeDetector.Entity>("time_detector") {

    companion object {

        private const val SECOND = 1
        private const val /*10*/ SECONDS = 2
        private const val MINUTE = 4
        private const val HOUR = 8

    }

    override fun appendTooltip(stack: ItemStack, world: BlockView?, tooltip: MutableList<Text>, options: TooltipContext) {
        if (AwesomeInputs.shift()) {
            tooltip.add(TranslatableText(AwesomeUtils.translatable("block", id.path, "hint1")).formatted(Formatting.GRAY))
            tooltip.add(TranslatableText(AwesomeUtils.translatable("block", id.path, "hint2")).formatted(Formatting.GRAY))
            tooltip.add(TranslatableText(AwesomeUtils.translatable("block", id.path, "hint4")).formatted(Formatting.GRAY))
            tooltip.add(TranslatableText(AwesomeUtils.translatable("block", id.path, "hint8")).formatted(Formatting.GRAY))
        } else {
            tooltip.add(TranslatableText(AwesomeUtils.translatable("item", "hint")).formatted(Formatting.GRAY))
        }
    }

    override fun tick(world: World, pos: BlockPos, state: BlockState, blockEntity: Entity) {
        world.setBlockState(pos, state.with(Properties.POWER, detect(world, pos, state, blockEntity)))
    }

    override fun detect(world: World, pos: BlockPos, state: BlockState, blockEntity: TimeDetector.Entity): Int {
        if (world.time % (Minecraft.TICKS * 60 * 60) == 0L) return HOUR
        if (world.time % (Minecraft.TICKS * 60) == 0L) return MINUTE
        if (world.time % (Minecraft.TICKS * 10) == 0L) return SECONDS
        if (world.time % Minecraft.TICKS == 0L) return SECOND
        return 0
    }

    override fun createBlockEntity(pos: BlockPos, state: BlockState): Entity =
        TimeDetector.Entity(id.path, this.entityType, pos, state)

    class Entity(
        id: String, type: BlockEntityType<Entity>, pos: BlockPos, state: BlockState,
    ) : AwesomeBlockEntity(id, type, pos, state, InputOutput(), 0 to 0)

}

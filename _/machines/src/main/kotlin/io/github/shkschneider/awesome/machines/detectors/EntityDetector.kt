package io.github.shkschneider.awesome.machines.detectors

import com.google.common.base.Predicates
import io.github.shkschneider.awesome.core.AwesomeBlockEntity
import io.github.shkschneider.awesome.core.AwesomeDetector
import io.github.shkschneider.awesome.core.AwesomeInputs
import io.github.shkschneider.awesome.core.AwesomeUtils
import io.github.shkschneider.awesome.core.ext.toBox
import io.github.shkschneider.awesome.custom.InputOutput
import io.github.shkschneider.awesome.custom.Minecraft
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.client.item.TooltipContext
import net.minecraft.entity.mob.HostileEntity
import net.minecraft.entity.passive.PassiveEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import net.minecraft.util.Formatting
import net.minecraft.util.math.BlockPos
import net.minecraft.world.BlockView
import net.minecraft.world.World

@Suppress("RemoveRedundantQualifierName")
class EntityDetector : AwesomeDetector<EntityDetector.Entity>("entity_detector") {

    companion object {

        private const val PLAYERS = 1
        private const val PASSIVES = 2
        private const val HOSTILES = 4

    }

    override fun appendTooltip(stack: ItemStack, world: BlockView?, tooltip: MutableList<Text>, options: TooltipContext) {
        if (AwesomeInputs.shift()) {
            tooltip.add(TranslatableText(AwesomeUtils.translatable("block", id.path, "hint1")).formatted(Formatting.GRAY))
            tooltip.add(TranslatableText(AwesomeUtils.translatable("block", id.path, "hint2")).formatted(Formatting.GRAY))
            tooltip.add(TranslatableText(AwesomeUtils.translatable("block", id.path, "hint4")).formatted(Formatting.GRAY))
        } else {
            tooltip.add(TranslatableText(AwesomeUtils.translatable("item", "hint")).formatted(Formatting.GRAY))
        }
    }

    override fun detect(world: World, pos: BlockPos, state: BlockState, blockEntity: EntityDetector.Entity): Int {
        val players = if (world.getEntitiesByClass(PlayerEntity::class.java, pos.toBox(radius = Minecraft.CHUNK / 2.0), Predicates.alwaysTrue()).isNotEmpty()) PLAYERS else 0
        val passives = if (world.getEntitiesByClass(PassiveEntity::class.java, pos.toBox(radius = Minecraft.CHUNK / 2.0), Predicates.alwaysTrue()).isNotEmpty()) PASSIVES else 0
        val hostiles = if (world.getEntitiesByClass(HostileEntity::class.java, pos.toBox(radius = Minecraft.CHUNK / 2.0), Predicates.alwaysTrue()).isNotEmpty()) HOSTILES else 0
        return players + passives + hostiles
    }

    override fun createBlockEntity(pos: BlockPos, state: BlockState): Entity =
        EntityDetector.Entity(id.path, this.entityType, pos, state)

    class Entity(
        id: String, type: BlockEntityType<Entity>, pos: BlockPos, state: BlockState,
    ) : AwesomeBlockEntity(id, type, pos, state, InputOutput(), 0 to 0)

}

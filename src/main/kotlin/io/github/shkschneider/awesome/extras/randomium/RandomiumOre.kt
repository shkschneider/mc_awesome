package io.github.shkschneider.awesome.extras.randomium

import io.github.shkschneider.awesome.Awesome
import io.github.shkschneider.awesome.core.AwesomeColors
import io.github.shkschneider.awesome.core.AwesomeParticles
import io.github.shkschneider.awesome.core.AwesomeRegistries
import io.github.shkschneider.awesome.core.AwesomeUtils
import io.github.shkschneider.awesome.core.ext.toVec3d
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.block.OreBlock
import net.minecraft.block.ShapeContext
import net.minecraft.command.argument.EntityAnchorArgumentType
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.World
import kotlin.math.max
import kotlin.math.min

class RandomiumOre(
    name: String,
) : OreBlock( // 1.19 ExperienceDroppingBlock
    FabricBlockSettings.copyOf(Blocks.REDSTONE_BLOCK)
) {

    init {
        init(AwesomeUtils.identifier(name))
    }

    private fun init(id: Identifier) {
        AwesomeRegistries.blockWithItem(id, this as Block, Awesome.GROUP)
    }

    @Suppress("DEPRECATION")
    override fun onBlockBreakStart(state: BlockState, world: World, pos: BlockPos, player: PlayerEntity) {
        super.onBlockBreakStart(state, world, pos, player)
        Direction.values().forEach { direction ->
            AwesomeParticles(world, pos, direction, color = AwesomeColors.randomium, offset = 0.5625) // net.minecraft.block.RedstoneOreBlock.class
        }
    }

    override fun onBreak(world: World, pos: BlockPos, state: BlockState, player: PlayerEntity) {
        super.onBreak(world, pos, state, player)
        if (world.registryKey != World.END) return
        val start = pos.mutableCopy().add(-1, -1, -1)
        val end = pos.mutableCopy().add(1, 1, 1)
        BlockPos.iterate(
            min(start.x, end.x), min(start.y, end.y), min(start.z, end.z),
            max(start.x, end.x), max(start.y, end.y), max(start.z, end.z),
        ).toList().filter {
            world.canPlace(state, it, ShapeContext.of(player))
        }.takeUnless { it.isEmpty() }?.random()?.let { tp ->
            player.teleport(tp.x.toDouble() + 0.5, tp.y.toDouble() + 0.25, tp.z.toDouble() + 0.5, false)
            player.lookAt(EntityAnchorArgumentType.EntityAnchor.EYES, pos.toVec3d())
        }
    }

}

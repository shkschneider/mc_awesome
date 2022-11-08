package io.github.shkschneider.awesome.custom

import io.github.shkschneider.awesome.Awesome
import io.github.shkschneider.awesome.core.ext.isOre
import io.github.shkschneider.awesome.enchantments.AwesomeEnchantments
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.enchantment.EnchantmentHelper
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.tag.BlockTags
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import kotlin.math.max
import kotlin.math.min

object VeinMining {

    private var isVeinMining = false

    operator fun invoke() = Unit

    init {
        if (Awesome.CONFIG.veinMiningEnchantment) {
            PlayerBlockBreakEvents.AFTER.register(PlayerBlockBreakEvents.After(VeinMining::invoke))
        }
    }

    operator fun invoke(world: World, player: PlayerEntity, pos: BlockPos, state: BlockState, blockEntity: BlockEntity?) {
        if (!Awesome.CONFIG.veinMiningEnchantment) throw IllegalStateException()
        val veinMining = EnchantmentHelper.getLevel(AwesomeEnchantments.veinMining, player.mainHandStack)
        if (!isVeinMining && !player.isSneaking && veinMining > 0) {
            if (state.block.isOre && state.isIn(BlockTags.PICKAXE_MINEABLE)) {
                veinMining(world, pos, player, veinMining, state.block.asItem())
            } else if (state.isIn(BlockTags.LOGS) && state.isIn(BlockTags.AXE_MINEABLE)) {
                veinMining(world, pos, player, veinMining * 2, state.block.asItem())
            }
        }
    }

    // TODO iterate while touching a similar item, not in a cube around
    private fun veinMining(world: World, pos: BlockPos, playerEntity: PlayerEntity, level: Int, item: Item) {
        if (!Awesome.CONFIG.veinMiningEnchantment) throw IllegalStateException()
        isVeinMining = true
        val start = pos.mutableCopy().add(-level, -level, -level)
        val end = pos.mutableCopy().add(level, level, level)
        val candidates = BlockPos.iterate(
            min(start.x, end.x), min(start.y, end.y), min(start.z, end.z),
            max(start.x, end.x), max(start.y, end.y), max(start.z, end.z)
        )
        for (candidate in candidates) {
            if (candidate.asLong() == pos.asLong()) continue
            val other = world.getBlockState(candidate).block
            if (other.asItem() === item) {
                other.afterBreak(world, playerEntity, candidate, world.getBlockState(candidate), world.getBlockEntity(candidate), ItemStack.EMPTY)
                world.removeBlock(candidate, false)
                world.markDirty(candidate)
            }
        }
        isVeinMining = false
    }

}

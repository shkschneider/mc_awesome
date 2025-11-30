package io.github.shkschneider.awesome.enchantments

import io.github.shkschneider.awesome.core.AwesomeEnchantment
import io.github.shkschneider.awesome.core.AwesomeUtils
import io.github.shkschneider.awesome.core.ext.id
import io.github.shkschneider.awesome.core.ext.isLog
import io.github.shkschneider.awesome.core.ext.isOre
import io.github.shkschneider.awesome.custom.Event
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents
import net.minecraft.block.BlockState
import net.minecraft.enchantment.EnchantmentHelper
import net.minecraft.enchantment.EnchantmentTarget
import net.minecraft.entity.EquipmentSlot
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import kotlin.math.pow

class VeinMiningEnchantment : AwesomeEnchantment(
    id = AwesomeUtils.identifier("vein_mining"),
    Rarity.RARE,
    levels = 1 to 5,
    EnchantmentTarget.DIGGER,
    listOf(EquipmentSlot.MAINHAND),
) {

    private var veinMining = 0

    init {
        @Event("PlayerBlockBreakEvents")
        PlayerBlockBreakEvents.AFTER.register(PlayerBlockBreakEvents.After { world, player, pos, state, _ ->
            invoke(world, player, pos, state)
        })
    }

    private operator fun invoke(world: World, player: PlayerEntity, pos: BlockPos, state: BlockState) {
        if (!player.mainHandStack.isSuitableFor(state)) return
        if (this.veinMining > 0 || player.isSneaking) return
        val veinMining = EnchantmentHelper.getLevel(this, player.mainHandStack)
        if (veinMining > 0) {
            this.veinMining = 1
            if (state.isOre) {
                vein(world, pos, player, veinMining, state.block.id())
            } else if (state.isLog) {
                vein(world, pos, player, veinMining, state.block.id())
            }
            this.veinMining = 0
        }
    }

    private fun vein(world: World, blockPos: BlockPos, playerEntity: PlayerEntity, level: Int, type: Identifier) {
        listOf(
            blockPos.up(), blockPos.down(), blockPos.north(), blockPos.south(), blockPos.east(), blockPos.west(),
        ).filter { pos -> world.getBlockState(pos).block.id() == type }.forEach { pos ->
            val state = world.getBlockState(pos)
            state.block.afterBreak(world, playerEntity, pos, state, world.getBlockEntity(pos), playerEntity.mainHandStack)
            world.removeBlock(pos, false)
            world.markDirty(pos)
            if (++this.veinMining < 2.0.pow(1 + level)) {
                vein(world, pos, playerEntity, level, type)
            } else {
                this.veinMining = 0
                return@forEach
            }
        }
    }

}

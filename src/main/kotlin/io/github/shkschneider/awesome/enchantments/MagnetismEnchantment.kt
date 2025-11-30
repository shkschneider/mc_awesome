package io.github.shkschneider.awesome.enchantments

import com.google.common.base.Predicates
import io.github.shkschneider.awesome.core.AwesomeColors
import io.github.shkschneider.awesome.core.AwesomeEnchantment
import io.github.shkschneider.awesome.core.AwesomeParticles
import io.github.shkschneider.awesome.core.AwesomeUtils
import io.github.shkschneider.awesome.custom.Event
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents
import net.minecraft.enchantment.EnchantmentHelper
import net.minecraft.enchantment.EnchantmentTarget
import net.minecraft.entity.EquipmentSlot
import net.minecraft.entity.ItemEntity
import net.minecraft.entity.player.PlayerEntity

class MagnetismEnchantment : AwesomeEnchantment(
    id = AwesomeUtils.identifier("magnetism"),
    Rarity.RARE,
    levels = 1 to 1,
    EnchantmentTarget.DIGGER,
    listOf(EquipmentSlot.MAINHAND),
) {

    init {
        @Event("ServerTickEvents.EndTIck")
        ServerTickEvents.END_SERVER_TICK.register(ServerTickEvents.EndTick { server ->
            server.playerManager.playerList.forEach { player ->
                val magnetism = EnchantmentHelper.getLevel(this, player.mainHandStack)
                if (player.isAlive && player.isSneaking.not() && magnetism > 0) {
                    magnetize(player, magnetism)
                }
            }
        })
    }

    private fun magnetize(player: PlayerEntity, level: Int) {
        check(level > 0)
        player.world.getEntitiesByClass(ItemEntity::class.java, player.boundingBox.expand(level.toDouble().times(8)), Predicates.alwaysTrue())
            .stream().map { it as ItemEntity }.forEach { itemEntity ->
                itemEntity.onPlayerCollision(player)
                AwesomeParticles(player.world, itemEntity.pos, color = listOf(AwesomeColors.red, AwesomeColors.blue).random(), offset = 0.0)
            }
    }

}

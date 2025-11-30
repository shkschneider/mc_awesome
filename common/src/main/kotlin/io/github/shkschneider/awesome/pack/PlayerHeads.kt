package io.github.shkschneider.awesome.pack

import io.github.shkschneider.awesome.custom.Event
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityCombatEvents
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.inventory.SimpleInventory
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.nbt.NbtCompound
import net.minecraft.util.ItemScatterer

object PlayerHeads {

    operator fun invoke() {
        @Event("ServerEntityCombatEvents")
        ServerEntityCombatEvents.AFTER_KILLED_OTHER_ENTITY.register(ServerEntityCombatEvents.AfterKilledOtherEntity { world, entity, killedEntity ->
            if (entity is PlayerEntity && killedEntity is PlayerEntity) {
                ItemScatterer.spawn(world, killedEntity.blockPos, SimpleInventory(head(killedEntity)))
            }
        })
    }

    private fun head(player: PlayerEntity): ItemStack =
        ItemStack(Items.PLAYER_HEAD).apply {
            setCustomName(player.name)
            nbt = NbtCompound().apply {
                putString("SkullOwner", player.entityName)
            }
        }

}

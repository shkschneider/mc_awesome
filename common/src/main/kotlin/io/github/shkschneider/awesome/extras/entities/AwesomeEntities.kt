package io.github.shkschneider.awesome.extras.entities

import io.github.shkschneider.awesome.Awesome
import io.github.shkschneider.awesome.core.AwesomeColors
import io.github.shkschneider.awesome.core.AwesomeRegistries
import io.github.shkschneider.awesome.core.AwesomeUtils
import io.github.shkschneider.awesome.custom.Minecraft
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.fabricmc.fabric.api.`object`.builder.v1.entity.FabricDefaultAttributeRegistry
import net.fabricmc.fabric.api.`object`.builder.v1.entity.FabricEntityTypeBuilder
import net.minecraft.entity.EntityDimensions
import net.minecraft.entity.EntityType
import net.minecraft.entity.SpawnGroup
import net.minecraft.entity.mob.MobEntity
import net.minecraft.item.ItemGroup
import net.minecraft.item.SpawnEggItem

object AwesomeEntities {

    operator fun invoke() {
        AwesomeRegistries.hostileEntity(
            AwesomeUtils.identifier(Herobrine.ID),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER) { type, world ->
                Herobrine(type, world)
            }.dimensions(EntityDimensions.fixed(Herobrine.SIZE.first, Herobrine.SIZE.second))
        ).also { entityType ->
            if (Minecraft.isClient) {
                FabricDefaultAttributeRegistry.register(entityType, Herobrine.attributes())
                EntityRendererRegistry.register(entityType, ::HerobrineRenderer)
            }
            Herobrine.spawnRules(entityType)
            spawnEgg(Herobrine.ID, AwesomeColors.valencia to AwesomeColors.tuna, entityType)
        }
        spawnEgg("wither", AwesomeColors.black to AwesomeColors.white, EntityType.WITHER)
        spawnEgg("dragon", AwesomeColors.black to AwesomeColors.valencia, EntityType.ENDER_DRAGON)
    }

    private fun spawnEgg(name: String, colors: Pair<Int, Int>, entityType: EntityType<out MobEntity>, group: ItemGroup = Awesome.GROUP): SpawnEggItem {
        val spawnEgg = SpawnEggItem(entityType, colors.first, colors.second, FabricItemSettings().group(Awesome.GROUP))
        AwesomeRegistries.item(AwesomeUtils.identifier("${name}_spawn_egg"), spawnEgg, group)
        return spawnEgg
    }

}

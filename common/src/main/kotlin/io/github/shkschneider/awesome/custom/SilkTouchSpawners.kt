package io.github.shkschneider.awesome.custom

import io.github.shkschneider.awesome.core.AwesomeUtils
import net.fabricmc.fabric.api.loot.v2.LootTableEvents
import net.minecraft.enchantment.Enchantments
import net.minecraft.item.Items
import net.minecraft.loot.LootPool
import net.minecraft.loot.LootTable
import net.minecraft.loot.condition.MatchToolLootCondition
import net.minecraft.loot.entry.ItemEntry
import net.minecraft.loot.function.CopyNbtLootFunction
import net.minecraft.loot.provider.nbt.ContextLootNbtProvider
import net.minecraft.loot.provider.number.ConstantLootNumberProvider
import net.minecraft.predicate.NumberRange
import net.minecraft.predicate.item.EnchantmentPredicate
import net.minecraft.predicate.item.ItemPredicate
import net.minecraft.util.Identifier

/**
 * /give @s minecraft:spawner
 */
object SilkTouchSpawners {

    operator fun invoke() {
        @Event("LootTableEvents.Modify")
        LootTableEvents.MODIFY.register { _, _, id, supplier, _ ->
            if (id == Identifier("blocks/spawner")) {
                invoke(supplier)
            }
        }
    }

    private operator fun invoke(supplier: LootTable.Builder) {
        supplier.pool(
            LootPool.Builder()
                .rolls(ConstantLootNumberProvider.create(1.toFloat()))
                .with(ItemEntry.builder(Items.SPAWNER).build())
                .apply(
                    CopyNbtLootFunction.builder(ContextLootNbtProvider.BLOCK_ENTITY)
                        .withOperation("SpawnData", "${AwesomeUtils.BLOCK_ENTITY_TAG}.SpawnData")
                        .withOperation("SpawnPotentials", "${AwesomeUtils.BLOCK_ENTITY_TAG}.SpawnPotentials")
                        .build()
                )
                .conditionally(
                    MatchToolLootCondition.builder(
                        ItemPredicate.Builder.create().enchantment(
                            EnchantmentPredicate(Enchantments.SILK_TOUCH, NumberRange.IntRange.atLeast(1))
                        )
                    ).build()
                ).build()
        )
    }

}

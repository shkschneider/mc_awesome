package io.github.shkschneider.awesome.extras.randomium

import io.github.shkschneider.awesome.core.ext.id
import net.fabricmc.fabric.api.biome.v1.BiomeModifications
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors
import net.minecraft.block.Blocks
import net.minecraft.structure.rule.BlockMatchRuleTest
import net.minecraft.world.gen.GenerationStep
import net.minecraft.world.gen.YOffset
import net.minecraft.world.gen.feature.ConfiguredFeatures
import net.minecraft.world.gen.feature.Feature
import net.minecraft.world.gen.feature.OreFeatureConfig
import net.minecraft.world.gen.feature.PlacedFeatures
import net.minecraft.world.gen.placementmodifier.BiomePlacementModifier
import net.minecraft.world.gen.placementmodifier.CountPlacementModifier
import net.minecraft.world.gen.placementmodifier.HeightRangePlacementModifier
import net.minecraft.world.gen.placementmodifier.SquarePlacementModifier

object Randomium {

    const val ID = "randomium"

    val ore = RandomiumOre("${ID}_ore")
    val deepslateOre = RandomiumOre("deepslate_${ID}_ore")
    val endOre = RandomiumOre("end_${ID}_ore")

    operator fun invoke() {
        BiomeModifications.addFeature(BiomeSelectors.foundInTheEnd(), GenerationStep.Feature.UNDERGROUND_ORES, PlacedFeatures.register(
            // name
            endOre.id().toString(),
            // configured_feature
            ConfiguredFeatures.register(endOre.id().toString(), Feature.ORE, OreFeatureConfig(
                listOf(OreFeatureConfig.createTarget(BlockMatchRuleTest(Blocks.END_STONE), endOre.defaultState)),
                3)),
            // modifiers
            listOf(
                CountPlacementModifier.of(30),
                SquarePlacementModifier.of(),
                HeightRangePlacementModifier.trapezoid(YOffset.aboveBottom(-64), YOffset.aboveBottom(64)),
                BiomePlacementModifier.of()
            )
        ).key.get())
        /*BiomeModifications.addFeature(
            BiomeSelectors.vanilla(),
            GenerationStep.Feature.UNDERGROUND_ORES,
            PlacedFeatures.of(AwesomeUtils.identifier("randomium_ore").toString()), // 1.19
        )*/
    }

}

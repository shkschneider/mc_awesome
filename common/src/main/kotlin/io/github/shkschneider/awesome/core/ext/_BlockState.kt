package io.github.shkschneider.awesome.core.ext

import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.block.MushroomBlock
import net.minecraft.block.OreBlock
import net.minecraft.block.PillarBlock
import net.minecraft.tag.BlockTags

val BlockState.isOre: Boolean get() =
    this.isIn(BlockTags.PICKAXE_MINEABLE) && (
        // OreBlock
        this.block is OreBlock
        // or tagged as ore
        || this.isIn(BlockTags.IRON_ORES)
        || this.isIn(BlockTags.COPPER_ORES)
        || this.isIn(BlockTags.REDSTONE_ORES)
        || this.isIn(BlockTags.GOLD_ORES)
        || this.isIn(BlockTags.COAL_ORES)
        || this.isIn(BlockTags.DIAMOND_ORES)
        || this.isIn(BlockTags.LAPIS_ORES)
        || this.isIn(BlockTags.EMERALD_ORES)
        // or nether quartz or ancient debris
        || this.block == Blocks.NETHER_QUARTZ_ORE
        || this.block == Blocks.ANCIENT_DEBRIS
        // lastly from other mods
        || (this.block.id().path.endsWith("_ore"))
    )

val BlockState.isLog: Boolean get() =
    this.isIn(BlockTags.AXE_MINEABLE) && (
        this.isIn(BlockTags.LOGS)
            || (this.block is PillarBlock && this.block.id().path.endsWith("_log"))
            || (this.block is MushroomBlock && this.block.id().path.endsWith("_stem"))
    )

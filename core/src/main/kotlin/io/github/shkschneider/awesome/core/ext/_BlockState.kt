package io.github.shkschneider.awesome.core.ext

import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.block.Material
import net.minecraft.block.OreBlock
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
        || (this.material == Material.METAL && this.isToolRequired && this.block.id().path.endsWith("_ore"))
    )

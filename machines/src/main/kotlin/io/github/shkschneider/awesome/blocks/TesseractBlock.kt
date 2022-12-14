package io.github.shkschneider.awesome.blocks

import io.github.shkschneider.awesome.core.AwesomeUtils
import io.github.shkschneider.awesome.core.AwesomeBlock
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.block.Blocks

class TesseractBlock : AwesomeBlock(
    id = AwesomeUtils.identifier("tesseract"),
    settings = FabricBlockSettings.copyOf(Blocks.AMETHYST_BLOCK).nonOpaque(),
)

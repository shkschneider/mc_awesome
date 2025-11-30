package io.github.shkschneider.awesome.extras.tool

import net.minecraft.item.ToolMaterials

object AwesomeTools {

    operator fun invoke() {
        AwesomeTool(ToolMaterials.WOOD)
        AwesomeTool(ToolMaterials.STONE)
        AwesomeTool(ToolMaterials.IRON)
        AwesomeTool(ToolMaterials.GOLD)
        AwesomeTool(ToolMaterials.DIAMOND)
        AwesomeTool(ToolMaterials.NETHERITE)
    }

}

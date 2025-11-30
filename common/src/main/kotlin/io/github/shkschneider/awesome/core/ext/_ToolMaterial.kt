package io.github.shkschneider.awesome.core.ext

import net.minecraft.item.AxeItem
import net.minecraft.item.HoeItem
import net.minecraft.item.Items
import net.minecraft.item.PickaxeItem
import net.minecraft.item.ShovelItem
import net.minecraft.item.ToolMaterial
import net.minecraft.item.ToolMaterials

val ToolMaterial.name: String get() = when (this) {
    ToolMaterials.WOOD -> "wooden"
    ToolMaterials.STONE -> "stone"
    ToolMaterials.IRON -> "iron"
    ToolMaterials.GOLD -> "golden"
    ToolMaterials.DIAMOND -> "diamond"
    ToolMaterials.NETHERITE -> "netherite"
    else -> throw IllegalStateException()
}

fun ToolMaterial.axe(): AxeItem = when (this) {
    ToolMaterials.WOOD -> Items.WOODEN_AXE
    ToolMaterials.STONE -> Items.STONE_AXE
    ToolMaterials.IRON -> Items.IRON_AXE
    ToolMaterials.GOLD -> Items.GOLDEN_AXE
    ToolMaterials.DIAMOND -> Items.DIAMOND_AXE
    ToolMaterials.NETHERITE -> Items.NETHERITE_AXE
    else -> throw IllegalStateException()
} as AxeItem

fun ToolMaterial.hoe(): HoeItem = when (this) {
    ToolMaterials.WOOD -> Items.WOODEN_HOE
    ToolMaterials.STONE -> Items.STONE_HOE
    ToolMaterials.IRON -> Items.IRON_HOE
    ToolMaterials.GOLD -> Items.GOLDEN_HOE
    ToolMaterials.DIAMOND -> Items.DIAMOND_HOE
    ToolMaterials.NETHERITE -> Items.NETHERITE_HOE
    else -> throw IllegalStateException()
} as HoeItem

fun ToolMaterial.pickaxe(): PickaxeItem = when (this) {
    ToolMaterials.WOOD -> Items.WOODEN_PICKAXE
    ToolMaterials.STONE -> Items.STONE_PICKAXE
    ToolMaterials.IRON -> Items.IRON_PICKAXE
    ToolMaterials.GOLD -> Items.GOLDEN_PICKAXE
    ToolMaterials.DIAMOND -> Items.DIAMOND_PICKAXE
    ToolMaterials.NETHERITE -> Items.NETHERITE_PICKAXE
    else -> throw IllegalStateException()
} as PickaxeItem

fun ToolMaterial.shovel(): ShovelItem = when (this) {
    ToolMaterials.WOOD -> Items.WOODEN_SHOVEL
    ToolMaterials.STONE -> Items.STONE_SHOVEL
    ToolMaterials.IRON -> Items.IRON_SHOVEL
    ToolMaterials.GOLD -> Items.GOLDEN_SHOVEL
    ToolMaterials.DIAMOND -> Items.DIAMOND_SHOVEL
    ToolMaterials.NETHERITE -> Items.NETHERITE_SHOVEL
    else -> throw IllegalStateException()
} as ShovelItem

val ToolMaterial.size: Int get() = 4

val ToolMaterial.attackSpeed: Float get() = when (this) {
    // axes
    ToolMaterials.WOOD, ToolMaterials.STONE -> -3.2F
    ToolMaterials.IRON -> -3.1F
    ToolMaterials.GOLD, ToolMaterials.DIAMOND, ToolMaterials.NETHERITE -> -3.0F
    else -> throw IllegalStateException()
}

package io.github.shkschneider.awesome.extras

import io.github.shkschneider.awesome.core.AwesomeRegistries
import io.github.shkschneider.awesome.core.AwesomeSounds
import io.github.shkschneider.awesome.core.AwesomeUtils
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.block.Block
import net.minecraft.block.Blocks
import net.minecraft.block.PlantBlock
import net.minecraft.enchantment.EnchantmentHelper
import net.minecraft.enchantment.Enchantments
import net.minecraft.entity.EquipmentSlot
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemUsageContext
import net.minecraft.item.MiningToolItem
import net.minecraft.item.ToolMaterials
import net.minecraft.tag.BlockTags
import net.minecraft.util.ActionResult
import net.minecraft.util.math.BlockPos

class Scythe : MiningToolItem(
    /* attackDamage */ 1F,
    /* attackSpeed */ -3F,
    ToolMaterials.WOOD,
    BlockTags.HOE_MINEABLE,
    FabricItemSettings().group(ItemGroup.TOOLS).maxCount(1).maxDamage(ToolMaterials.WOOD.durability * 2),
) {

    init {
        AwesomeRegistries.item(AwesomeUtils.identifier("scythe"), this, ItemGroup.TOOLS)
    }

    private fun check(block: Block): Boolean =
        block is PlantBlock

    override fun useOnBlock(context: ItemUsageContext): ActionResult {
        val efficiency = EnchantmentHelper.getLevel(Enchantments.EFFICIENCY, context.player?.mainHandStack)
        val range = 2 + efficiency
        val yOffset = if (check(context.world.getBlockState(context.blockPos).block)) 0 else 1
        var used = 0
        (context.blockPos.x - range until context.blockPos.x + range).forEach { x ->
            (context.blockPos.z - range until context.blockPos.z + range).forEach { z ->
                val pos = BlockPos(x, context.blockPos.y + yOffset, z)
                val state = context.world.getBlockState(pos)
                if (check(state.block)) {
                    mow(context, pos)
                    used++
                }
            }
        }
        return if (used > 0) {
            context.stack.damage(range, context.player) { player -> player?.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND) }
            ActionResult.success(context.world.isClient)
        } else {
            ActionResult.PASS
        }
    }

    private fun mow(context: ItemUsageContext, pos: BlockPos) {
        val state = Blocks.AIR.defaultState
        context.world.setBlockState(pos, state)
        // 1.19 context.world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(context.player, state))
        AwesomeSounds(context.world to pos, AwesomeSounds.crop)
    }

}

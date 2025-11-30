package io.github.shkschneider.awesome.extras.tool

import io.github.shkschneider.awesome.core.AwesomeInputs
import io.github.shkschneider.awesome.core.AwesomeRegistries
import io.github.shkschneider.awesome.core.AwesomeUtils
import io.github.shkschneider.awesome.core.ext.attackSpeed
import io.github.shkschneider.awesome.core.ext.axe
import io.github.shkschneider.awesome.core.ext.hoe
import io.github.shkschneider.awesome.core.ext.name
import io.github.shkschneider.awesome.core.ext.pickaxe
import io.github.shkschneider.awesome.core.ext.shovel
import io.github.shkschneider.awesome.core.ext.size
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.block.BlockState
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.item.ItemUsageContext
import net.minecraft.item.MiningToolItem
import net.minecraft.item.ToolMaterial
import net.minecraft.tag.BlockTags
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand

class AwesomeTool(
    private val material: ToolMaterial,
) : MiningToolItem(
    /* attackDamage */ material.axe().attackDamage,
    /* attackSpeed */ material.attackSpeed,
    material,
    BlockTags.PICKAXE_MINEABLE,
    FabricItemSettings().group(ItemGroup.TOOLS).maxDamage(material.durability * material.size),
) {

    init {
        AwesomeRegistries.item(AwesomeUtils.identifier("${material.name}_tool"), this, ItemGroup.TOOLS)
    }

    private fun tools(material: ToolMaterial): List<MiningToolItem> =
        listOf(material.axe(), material.hoe(), material.pickaxe(), material.shovel()).let { tools ->
            if (AwesomeInputs.alt()) tools.reversed() else tools
        }

    override fun getMiningSpeedMultiplier(stack: ItemStack, state: BlockState): Float =
        if (isSuitableFor(stack, state)) miningSpeed else 1F

    override fun useOnBlock(context: ItemUsageContext): ActionResult {
        tools(material).forEach { tool ->
            if (tool.useOnBlock(context).isAccepted) return ActionResult.success(context.world.isClient)
        }
        return ActionResult.FAIL
    }

    override fun useOnEntity(stack: ItemStack, user: PlayerEntity, entity: LivingEntity, hand: Hand): ActionResult {
        tools(material).forEach { tool ->
            if (tool.useOnEntity(stack, user, entity, hand).isAccepted) return ActionResult.success(user.world.isClient)
        }
        return ActionResult.FAIL
    }

    override fun isSuitableFor(state: BlockState): Boolean =
        tools(material).any { it.isSuitableFor(state) }

    override fun isSuitableFor(stack: ItemStack, state: BlockState): Boolean =
        tools(material).any { it.isSuitableFor(stack, state) }

}

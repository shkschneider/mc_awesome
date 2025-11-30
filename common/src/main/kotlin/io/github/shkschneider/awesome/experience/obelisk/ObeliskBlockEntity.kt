package io.github.shkschneider.awesome.experience.obelisk

import io.github.shkschneider.awesome.core.AwesomeBlockEntity
import io.github.shkschneider.awesome.custom.InputOutput
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.util.ActionResult
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.math.MathHelper

class ObeliskBlockEntity(
    id: String, type: BlockEntityType<out AwesomeBlockEntity>, pos: BlockPos, state: BlockState, io: InputOutput, delegates: Pair<Int, Int>,
) : AwesomeBlockEntity.WithInventory(id, type, pos, state, io, delegates) {

    var bottles: Int
        get() = items[0].count
        set(value) { items[0] = ItemStack(Items.EXPERIENCE_BOTTLE, MathHelper.clamp(value, Obelisk.LEVELS.values.min(), Obelisk.LEVELS.values.max())) }

    private val stack: ItemStack get() = items[0]

    // limited to a stack's size
    fun store(player: PlayerEntity): ActionResult {
        val levels = when {
            player.experienceLevel >= 10 -> if (stack.maxCount - stack.count >= 10) 10 else if (stack.maxCount - stack.count >= 1) 1 else 0
            player.experienceLevel >= 1 -> if (stack.maxCount - stack.count >= 1) 1 else 0
            else -> 0
        }
        if (levels > 0) {
            bottles += levels
            player.addExperienceLevels(-levels)
            return ActionResult.SUCCESS
        }
        return ActionResult.PASS
    }

    fun retrieve(player: PlayerEntity): ActionResult {
        val levels = when {
            bottles >= 10 -> 10
            bottles >= 1 -> 1
            else -> 0
        }
        if (levels > 0) {
            player.addExperienceLevels(levels)
            bottles -= levels
            return ActionResult.SUCCESS
        }
        return ActionResult.PASS
    }

    override fun canInsert(slot: Int, stack: ItemStack, dir: Direction?): Boolean = false

    override fun canExtract(slot: Int, stack: ItemStack, dir: Direction?): Boolean = false

}

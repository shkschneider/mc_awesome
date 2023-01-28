package io.github.shkschneider.awesome.machines.quarry

import io.github.shkschneider.awesome.core.AwesomeBlockEntity
import io.github.shkschneider.awesome.core.AwesomeUtils
import io.github.shkschneider.awesome.custom.Minecraft
import net.minecraft.block.BlockState
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.SidedInventory
import net.minecraft.item.ItemStack
import net.minecraft.screen.ScreenHandler
import net.minecraft.text.Text
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction

class QuarryBlockEntity(
    pos: BlockPos,
    state: BlockState,
) : AwesomeBlockEntity.WithInventory(
    id = Quarry.ID,
    type = Quarry.block.entityType,
    pos = pos,
    state = state,
    io = Quarry.IO,
    delegates = Quarry.PROPERTIES to 0,
), AwesomeBlockEntity.WithScreen {

    var progress: Int
        get() = properties.get(0)
        set(value) = properties.set(0, value)
    var duration: Int
        get() = properties.get(1)
        set(value) = properties.set(1, value)
    var efficiency: Int
        get() = properties.get(2)
        set(value) = properties.set(2, value)
    var fortune: Int
        get() = properties.get(3)
        set(value) = properties.set(3, value)

    init {
        progress = 0
        efficiency = 1
        fortune = 1
        duration = Minecraft.TICKS / efficiency
    }

    override fun getDisplayName(): Text =
        Text.translatable(AwesomeUtils.translatable("block", Quarry.ID))

    override fun createMenu(syncId: Int, playerInventory: PlayerInventory, player: PlayerEntity): ScreenHandler =
        QuarryScreenHandler(syncId, this as SidedInventory, playerInventory, properties)

    override fun canInsert(slot: Int, stack: ItemStack, dir: Direction?): Boolean =
        io.isInput(slot)

    override fun canExtract(slot: Int, stack: ItemStack, dir: Direction?): Boolean =
        io.isOutput(slot)

    fun insert(stack: ItemStack) {
        while (stack.count > 0) {
            if (getStack(1).isEmpty) {
                setStack(1, stack)
                break
            }  else if (getStack(1).item == stack.item && getStack(1).count < getStack(1).maxCount) {
                getStack(1).count++
                stack.count--
            } else {
                break
            }
        }
        markDirty()
    }

}

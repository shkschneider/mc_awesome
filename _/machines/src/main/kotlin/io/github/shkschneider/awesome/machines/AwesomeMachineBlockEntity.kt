package io.github.shkschneider.awesome.machines

import io.github.shkschneider.awesome.core.AwesomeBlockEntity
import io.github.shkschneider.awesome.core.AwesomeUtils
import io.github.shkschneider.awesome.core.ext.getStacks
import io.github.shkschneider.awesome.core.ext.isFull
import net.minecraft.block.BlockState
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.SidedInventory
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound
import net.minecraft.screen.PropertyDelegate
import net.minecraft.screen.ScreenHandler
import net.minecraft.state.property.Properties
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction

abstract class AwesomeMachineBlockEntity<BE : AwesomeBlockEntity.WithInventory, SH : AwesomeMachineScreenHandler<BE>>(
    protected val machine: AwesomeMachine<BE, SH>,
    pos: BlockPos,
    state: BlockState,
) : AwesomeBlockEntity.WithInventory(
    id = machine.id,
    type = machine.block.entityType,
    pos = pos,
    state = state,
    io = machine.io,
    delegates = machine.properties to 0,
), AwesomeBlockEntity.WithScreen {

    open var progress: Int
        get() = properties.get(0)
        set(value) = properties.set(0, value)
    open var duration: Int
        get() = properties.get(1)
        set(value) = properties.set(1, value)
    open var fuel: Int
        get() = properties.get(2)
        set(value) = properties.set(2, value)

    fun getCustomProperty(index: Int): Int =
        properties.get(AwesomeMachine.PROPERTIES + index)

    fun setCustomProperty(index: Int, value: Int) {
        properties.set(AwesomeMachine.PROPERTIES + index, value)
    }

    open fun on() {
        setPropertyState(state.with(Properties.LIT, true))
        markDirty()
    }

    open fun off() {
        setPropertyState(state.with(Properties.LIT, false))
        progress = 0
        markDirty()
    }

    fun getSlot(index: Int): Pair<Int, ItemStack> =
        index to items[index]

    override fun getDisplayName(): Text =
        TranslatableText(AwesomeUtils.translatable("block", machine.id))

    override fun createMenu(syncId: Int, playerInventory: PlayerInventory, player: PlayerEntity): ScreenHandler =
        screenHandler(syncId, playerInventory, this as SidedInventory, properties)

    abstract fun screenHandler(syncId: Int, playerInventory: PlayerInventory, sidedInventory: SidedInventory, properties: PropertyDelegate): ScreenHandler

    override fun canInsert(slot: Int, stack: ItemStack, dir: Direction?): Boolean =
        io.isInput(slot)

    override fun canExtract(slot: Int, stack: ItemStack, dir: Direction?): Boolean =
        io.isOutput(slot)

    override fun insert(stack: ItemStack): ItemStack {
        val stacks = getStacks().mapIndexed { index, itemStack -> index to itemStack }.filter { io.isOutput(it.first) }
        stacks.filter { it.second.item == stack.item }.map { it.first }.forEach { slot ->
            if (stack.isEmpty) return@forEach
            while (stack.count > 0 && !getStack(slot).isFull) {
                getStack(slot).count++
                stack.count--
            }
        }
        if (!stack.isEmpty) {
            stacks.filter { it.second.isEmpty }.map { it.first }.forEach { slot ->
                setStack(slot, stack.copy())
                stack.count = 0
            }
        }
        markDirty()
        return stack
    }

    override fun writeNbt(nbt: NbtCompound) {
        super.writeNbt(nbt)
        nbt.putInt("progress", progress)
        nbt.putInt("duration", duration)
        nbt.putInt("fuel", fuel)
    }

    override fun readNbt(nbt: NbtCompound) {
        fuel = nbt.getInt("fuel")
        duration = nbt.getInt("duration")
        progress = nbt.getInt("progress")
        super.readNbt(nbt)
    }

}

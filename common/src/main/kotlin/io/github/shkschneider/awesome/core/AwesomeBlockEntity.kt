package io.github.shkschneider.awesome.core

import io.github.shkschneider.awesome.core.ext.getStacks
import io.github.shkschneider.awesome.core.ext.isFull
import io.github.shkschneider.awesome.core.ext.readNbt
import io.github.shkschneider.awesome.core.ext.relativeFace
import io.github.shkschneider.awesome.core.ext.writeNbt
import io.github.shkschneider.awesome.custom.IInventory
import io.github.shkschneider.awesome.custom.InputOutput
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.inventory.Inventories
import net.minecraft.inventory.SidedInventory
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound
import net.minecraft.screen.NamedScreenHandlerFactory
import net.minecraft.screen.PropertyDelegate
import net.minecraft.state.property.Property
import net.minecraft.util.collection.DefaultedList
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.event.GameEvent

abstract class AwesomeBlockEntity(
    private val id: String,
    type: BlockEntityType<out AwesomeBlockEntity>,
    pos: BlockPos,
    state: BlockState,
    private val io: InputOutput,
    delegates: Pair<Int, Int>,
) : BlockEntity(type, pos, state) {

    private val _properties: MutableMap<Int, Int> = buildMap {
        (0 until delegates.first).forEach { i ->
            put(i, delegates.second)
        }
    }.toMutableMap()

    protected val properties = object : PropertyDelegate {
        override fun get(index: Int): Int = _properties.getValue(index)
        override fun set(index: Int, value: Int) { _properties[index] = value }
        override fun size(): Int = _properties.size
    }

    fun <T : Comparable<T>> getPropertyState(state: BlockState, property: Property<T>): T {
        if (state.properties.none { it == property }) throw IllegalArgumentException()
        return world?.getBlockState(pos)?.get(property) ?: state.get(property)
    }

    fun setPropertyState(state: BlockState) {
        world?.setBlockState(pos, state)
        world?.markDirty(pos)
    }

    override fun writeNbt(nbt: NbtCompound) {
        super.writeNbt(nbt)
        properties.writeNbt(nbt)
    }

    override fun readNbt(nbt: NbtCompound) {
        properties.readNbt(nbt)
        super.readNbt(nbt)
    }

    abstract class WithInventory(
        id: String,
        type: BlockEntityType<out AwesomeBlockEntity>,
        pos: BlockPos,
        val state: BlockState,
        val io: InputOutput,
        delegates: Pair<Int, Int>,
    ) : AwesomeBlockEntity(id, type, pos, state, io, delegates), IInventory, SidedInventory {

        override val items: DefaultedList<ItemStack> = DefaultedList.ofSize(io.size, ItemStack.EMPTY)

        override fun onOpen(player: PlayerEntity?) {
            world?.emitGameEvent(player, GameEvent.CONTAINER_OPEN, pos)
        }

        override fun onClose(player: PlayerEntity?) {
            world?.emitGameEvent(player, GameEvent.CONTAINER_CLOSE, pos)
        }

        override fun getAvailableSlots(side: Direction?): IntArray =
            (0 until io.size).toList().toIntArray()

        override fun canInsert(slot: Int, stack: ItemStack, dir: Direction?): Boolean =
            if (dir == null) io.isInput(slot)
            else io.canInsert(slot, dir.relativeFace(state))

        override fun canExtract(slot: Int, stack: ItemStack, dir: Direction?): Boolean =
            if (dir == null) io.isOutput(slot)
            else io.canExtract(slot, dir.relativeFace(state))

        open fun insert(stack: ItemStack): ItemStack {
            val stacks = getStacks().mapIndexed { index, itemStack -> index to itemStack }
            stacks.filter { it.second.item == stack.item }.map { it.first }.forEach { slot ->
                if (stack.isEmpty) return@forEach
                while (stack.count > 0 && !getStack(slot).isFull) {
                    getStack(slot).count++
                    stack.count--
                }
            }
            stacks.filter { it.second.isEmpty }.map { it.first }.forEach { slot ->
                setStack(slot, stack.copy())
                stack.count = 0
            }
            markDirty()
            return stack
        }

        public override fun writeNbt(nbt: NbtCompound) {
            super.writeNbt(nbt)
            Inventories.writeNbt(nbt, items)
        }

        override fun readNbt(nbt: NbtCompound) {
            Inventories.readNbt(nbt, items)
            super.readNbt(nbt)
        }

    }

    interface WithScreen : NamedScreenHandlerFactory

}

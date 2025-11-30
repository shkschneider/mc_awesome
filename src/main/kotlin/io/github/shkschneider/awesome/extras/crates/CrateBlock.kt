package io.github.shkschneider.awesome.extras.crates

import io.github.shkschneider.awesome.core.AwesomeBlockEntity
import io.github.shkschneider.awesome.core.AwesomeBlockWithEntity
import io.github.shkschneider.awesome.core.AwesomeUtils
import io.github.shkschneider.awesome.custom.InputOutput
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.block.ShapeContext
import net.minecraft.client.item.TooltipContext
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.Inventories
import net.minecraft.item.BlockItem
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtElement
import net.minecraft.screen.ScreenHandler
import net.minecraft.text.LiteralText
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import net.minecraft.util.Formatting
import net.minecraft.util.collection.DefaultedList
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.shape.VoxelShape
import net.minecraft.world.BlockView
import net.minecraft.world.World

@Suppress("RemoveRedundantQualifierName")
class CrateBlock(
    private val crate: Crate,
) : AwesomeBlockWithEntity<CrateBlock.Entity>(
    crate.id, FabricBlockSettings.copy(Blocks.BARREL).nonOpaque(),
), AwesomeBlockWithEntity.RetainsInventory {

    override fun getOutlineShape(state: BlockState, world: BlockView, pos: BlockPos, context: ShapeContext): VoxelShape =
        crate.size.shape

    override fun createBlockEntity(pos: BlockPos, state: BlockState): CrateBlock.Entity =
        CrateBlock.Entity(crate, pos, state)

    override fun appendTooltip(stack: ItemStack, world: BlockView?, tooltip: MutableList<Text>, options: TooltipContext) {
        super.appendTooltip(stack, world, tooltip, options)
        val n = tooltip.size
        BlockItem.getBlockEntityNbt(stack)?.let { nbt ->
            if (nbt.contains("Items", NbtElement.LIST_TYPE.toInt())) {
                val inventory = DefaultedList.ofSize(crate.size.total, ItemStack.EMPTY).apply {
                    Inventories.readNbt(nbt, this)
                }
                tooltip.add(LiteralText("${inventory.sumOf { it.count }} items").formatted(Formatting.GRAY))
            }
        }
        if (tooltip.size == n) {
            tooltip.add(TranslatableText(AwesomeUtils.translatable("block", id.path, "hint")).formatted(Formatting.GRAY))
        }
    }

    override fun hasComparatorOutput(state: BlockState): Boolean = true

    override fun getComparatorOutput(state: BlockState, world: World, pos: BlockPos): Int =
        ScreenHandler.calculateComparatorOutput(world.getBlockEntity(pos))

    override fun tick(world: World, pos: BlockPos, state: BlockState, blockEntity: CrateBlock.Entity) = Unit

    class Entity(
        private val crate: Crate, pos: BlockPos, state: BlockState,
    ) : AwesomeBlockEntity.WithInventory(
        crate.id.path, crate.block.entityType, pos, state, InputOutput(outputs = crate.size.total), 0 to 0,
    ), AwesomeBlockEntity.WithScreen {

        override fun getDisplayName(): Text =
            TranslatableText(AwesomeUtils.translatable("block", crate.id.path))

        override fun createMenu(syncId: Int, playerInventory: PlayerInventory, player: PlayerEntity): ScreenHandler =
            CrateScreen.Handler(crate, syncId, playerInventory, this)

        override fun canInsert(slot: Int, stack: ItemStack, dir: Direction?): Boolean = true

        override fun canExtract(slot: Int, stack: ItemStack, dir: Direction?): Boolean = true

    }

}

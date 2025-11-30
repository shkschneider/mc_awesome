package io.github.shkschneider.awesome.core

import io.github.shkschneider.awesome.Awesome
import net.minecraft.block.Block
import net.minecraft.block.BlockRenderType
import net.minecraft.block.BlockState
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemPlacementContext
import net.minecraft.util.Identifier

abstract class AwesomeBlock(
    val id: Identifier,
    settings: Settings,
    group: ItemGroup = Awesome.GROUP,
) : Block(settings) {

    private lateinit var _block: Block
    val block: Block get() = _block

    private lateinit var blockItem: BlockItem

    init {
        init(group)
    }

    private fun init(group: ItemGroup) {
        blockItem = AwesomeRegistries.blockWithItem(id, this as Block, group)
        _block = blockItem.block
    }

    override fun asItem(): Item = blockItem

    @Suppress("OVERRIDE_DEPRECATION")
    override fun getRenderType(state: BlockState): BlockRenderType =
        BlockRenderType.MODEL

    override fun getPlacementState(ctx: ItemPlacementContext): BlockState =
        defaultState

}

package io.github.shkschneider.awesome.extras.void

import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.block.entity.EndPortalBlockEntity
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction

class VoidBlockEntity(type: BlockEntityType<VoidBlockEntity>, pos: BlockPos, state: BlockState) : EndPortalBlockEntity(type, pos, state) {

    override fun shouldDrawSide(direction: Direction): Boolean = true

}

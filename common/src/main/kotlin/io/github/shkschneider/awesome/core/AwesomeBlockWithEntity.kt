package io.github.shkschneider.awesome.core

import io.github.shkschneider.awesome.Awesome
import io.github.shkschneider.awesome.core.ext.id
import net.fabricmc.fabric.api.`object`.builder.v1.block.entity.FabricBlockEntityTypeBuilder
import net.minecraft.block.Block
import net.minecraft.block.BlockRenderType
import net.minecraft.block.BlockState
import net.minecraft.block.BlockWithEntity
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityTicker
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.entity.ItemEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.inventory.Inventory
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemPlacementContext
import net.minecraft.item.ItemStack
import net.minecraft.loot.context.LootContext
import net.minecraft.loot.context.LootContextParameters
import net.minecraft.nbt.NbtCompound
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.Identifier
import net.minecraft.util.ItemScatterer
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.util.registry.Registry
import net.minecraft.world.World

abstract class AwesomeBlockWithEntity<BE : BlockEntity>(
    val id: Identifier,
    settings: Settings,
    group: ItemGroup = Awesome.GROUP,
) : BlockWithEntity(settings), BlockEntityTicker<BE> {

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

    val entityType: BlockEntityType<BE> = Registry.register(
        Registry.BLOCK_ENTITY_TYPE, id,
        FabricBlockEntityTypeBuilder.create({ pos, state -> createBlockEntity(pos, state) }, block).build(null)
    )

    abstract override fun createBlockEntity(pos: BlockPos, state: BlockState): BE

    override fun onUse(state: BlockState, world: World, pos: BlockPos, player: PlayerEntity, hand: Hand, hit: BlockHitResult): ActionResult {
        if (!world.isClient) state.createScreenHandlerFactory(world, pos)?.let(player::openHandledScreen)
        return ActionResult.SUCCESS
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : BlockEntity> getTicker(_world: World, _state: BlockState, type: BlockEntityType<T>): BlockEntityTicker<T>? =
        checkType(type, type) { world, pos, state, entity ->
            // BEWARE: world.isClient
            tick(world, pos, state, entity as BE)
        }

    interface RetainsInventory

    override fun onBreak(world: World, pos: BlockPos, state: BlockState, player: PlayerEntity) {
        val block = world.getBlockState(pos).block as? RetainsInventory
        val entity = world.getBlockEntity(pos) as? AwesomeBlockEntity.WithInventory
        if (entity != null && block != null) {
            if (!world.isClient && !player.isCreative) {
                val stack = ItemStack(world.getBlockState(pos).block)
                if (!entity.isEmpty) {
                    setBlockEntityTag(entity, stack)
                }
                world.spawnEntity(ItemEntity(world, pos.x + 0.5, pos.y + 0.5, pos.z + 0.5, stack).apply {
                    setToDefaultPickupDelay()
                })
            }
        }
        super.onBreak(world, pos, state, player)
    }

    @Suppress("DEPRECATION")
    override fun getDroppedStacks(state: BlockState, builder: LootContext.Builder): MutableList<ItemStack> {
        val items = super.getDroppedStacks(state, builder)
        if (this is RetainsInventory) {
            for (stack in items) {
                if (stack.item.id() == id) {
                    setBlockEntityTag(builder.get(LootContextParameters.BLOCK_ENTITY) as AwesomeBlockEntity.WithInventory, stack)
                }
            }
        } else {
            (builder.get(LootContextParameters.BLOCK_ENTITY) as? AwesomeBlockEntity.WithInventory)?.let { blockEntity ->
                ItemScatterer.spawn(builder.world as World, blockEntity.pos as BlockPos, blockEntity as Inventory)
            }
        }
        return items
    }

    private fun setBlockEntityTag(crate: AwesomeBlockEntity.WithInventory, stack: ItemStack) {
        if (!crate.items.isEmpty()) {
            stack.setSubNbt(AwesomeUtils.BLOCK_ENTITY_TAG, NbtCompound().apply {
                crate.writeNbt(this)
            })
        }
    }

    @Suppress("DEPRECATION")
    override fun onStateReplaced(state: BlockState, world: World, pos: BlockPos, newState: BlockState, moved: Boolean) {
        // do NOT ItemScatterer.spawn()
        super.onStateReplaced(state, world, pos, newState, moved)
    }

    abstract class WithInventory<BE : BlockEntity>(
        id: Identifier,
        settings: Settings,
        group: ItemGroup = Awesome.GROUP,
    ) : AwesomeBlockWithEntity<BE>(id, settings, group) {

        override fun onStateReplaced(state: BlockState, world: World, pos: BlockPos, newState: BlockState, moved: Boolean) {
            if (state.block != newState.block) {
                (world.getBlockEntity(pos) as? Inventory)?.let { ItemScatterer.spawn(world, pos, it) }
                world.updateComparators(pos, this)
                @Suppress("DEPRECATION")
                super.onStateReplaced(state, world, pos, newState, moved)
            }
        }

        override fun onUse(state: BlockState, world: World, pos: BlockPos, player: PlayerEntity, hand: Hand, hit: BlockHitResult): ActionResult {
            if (!world.isClient) {
                state.createScreenHandlerFactory(world, pos)?.let(player::openHandledScreen)
            }
            return ActionResult.SUCCESS
        }

    }

}

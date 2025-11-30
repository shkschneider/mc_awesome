package io.github.shkschneider.awesome.machines.recycler

import io.github.shkschneider.awesome.custom.InputOutput
import io.github.shkschneider.awesome.custom.Minecraft
import io.github.shkschneider.awesome.machines.AwesomeMachine
import io.github.shkschneider.awesome.machines.AwesomeMachineBlock
import io.github.shkschneider.awesome.machines.AwesomeMachineScreen
import net.minecraft.block.BlockState
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.item.ItemStack
import net.minecraft.recipe.Recipe
import net.minecraft.recipe.RecipeType
import net.minecraft.text.Text
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class Recycler : AwesomeMachine<RecyclerBlock.Entity, RecyclerScreen.Handler>(
    id = "recycler",
    io = InputOutput(inputs = 1, outputs = 9),
) {

    companion object {

        private const val EFFICIENCY = 0.6 // much like GregTech's Disassembler

    }

    init {
        check(EFFICIENCY > 0.0 && EFFICIENCY <= 1.0)
    }

    override fun block(): AwesomeMachineBlock<RecyclerBlock.Entity, RecyclerScreen.Handler> =
        RecyclerBlock(this)

    override fun screenHandler(syncId: Int, playerInventory: PlayerInventory): RecyclerScreen.Handler =
        RecyclerScreen.Handler(this, null, syncId, playerInventory)

    override fun screen(handler: RecyclerScreen.Handler, playerInventory: PlayerInventory, title: Text): AwesomeMachineScreen<RecyclerBlock.Entity, RecyclerScreen.Handler> =
        RecyclerScreen(this, handler, playerInventory, title)

    override fun tick(world: World, pos: BlockPos, state: BlockState, blockEntity: RecyclerBlock.Entity) {
        if (world.isClient) return
        val recipe = getRecipe(world, blockEntity.getInputSlot().second)
        if (recipe == null) { blockEntity.off() ; return }
        if (blockEntity.duration == 0) {
            blockEntity.duration = Minecraft.TICKS
            blockEntity.progress = 0
            blockEntity.off()
            return
        }
        blockEntity.progress++
        blockEntity.on()
        if (blockEntity.progress >= blockEntity.duration) {
            recycle(world, blockEntity, recipe)
            blockEntity.progress = 0
        }
    }

    private fun getRecipe(world: World, itemStack: ItemStack): Recipe<*>? {
        return world.recipeManager.values().filter { it.type == RecipeType.CRAFTING }
            .filter { itemStack.item == it.output.item && itemStack.count >= it.output.count }
            .filterNot { it.isEmpty }
            .randomOrNull()
    }

    private fun recycle(world: World, blockEntity: RecyclerBlock.Entity, recipe: Recipe<*>) {
        val n =  blockEntity.getInputSlot().second.count / recipe.output.count // n >= 1
        recipe.ingredients.mapNotNull { it.matchingStacks.randomOrNull()?.item }.forEachIndexed { index, item ->
            val i = blockEntity.getOutputSlots()[index].first
            if (world.random.nextInt(100) < (EFFICIENCY * 100)) {
                blockEntity.setStack(i, ItemStack(item, n))
            }
        }
        blockEntity.getInputSlot().second.decrement(recipe.output.count * n)
    }

}

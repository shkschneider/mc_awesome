package io.github.shkschneider.awesome.machines.crafter

import io.github.shkschneider.awesome.core.ext.canInsert
import io.github.shkschneider.awesome.core.ext.test
import io.github.shkschneider.awesome.custom.DummyCraftingInventory
import io.github.shkschneider.awesome.custom.InputOutput
import io.github.shkschneider.awesome.custom.Minecraft
import io.github.shkschneider.awesome.machines.AwesomeMachine
import io.github.shkschneider.awesome.machines.AwesomeMachineBlock
import io.github.shkschneider.awesome.machines.AwesomeMachineScreen
import net.minecraft.block.BlockState
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.CraftingInventory
import net.minecraft.item.ItemStack
import net.minecraft.recipe.CraftingRecipe
import net.minecraft.recipe.RecipeType
import net.minecraft.text.Text
import net.minecraft.util.collection.DefaultedList
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class Crafter : AwesomeMachine<CrafterBlock.Entity, CrafterScreen.Handler>(
    id = "crafter",
    io = InputOutput(inputs = 3 * 3, outputs = 1),
) {

    override fun block(): AwesomeMachineBlock<CrafterBlock.Entity, CrafterScreen.Handler> =
        CrafterBlock(this)

    override fun screenHandler(syncId: Int, playerInventory: PlayerInventory): CrafterScreen.Handler =
        CrafterScreen.Handler(this, null, syncId, playerInventory)

    override fun screen(handler: CrafterScreen.Handler, playerInventory: PlayerInventory, title: Text): AwesomeMachineScreen<CrafterBlock.Entity, CrafterScreen.Handler> =
        CrafterScreen(this, handler, playerInventory, title)

    override fun tick(world: World, pos: BlockPos, state: BlockState, blockEntity: CrafterBlock.Entity) {
        if (world.isClient) return
        val craftingGrid = DummyCraftingInventory(3, 3, DefaultedList.ofSize<ItemStack?>(3 * 3).apply {
            blockEntity.getInputSlots().forEach { inputSlot ->
                this.add(inputSlot.second)
            }
        })
        val recipe = getRecipe(world, blockEntity, craftingGrid)
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
            craft(blockEntity, craftingGrid, recipe)
            blockEntity.progress = 0
        }
    }

    private fun getRecipe(world: World, blockEntity: CrafterBlock.Entity, craftingGrid: CraftingInventory): CraftingRecipe? {
        return world.recipeManager.getFirstMatch(RecipeType.CRAFTING, craftingGrid, world).orElse(null)
            ?.takeUnless { recipe -> recipe.test(blockEntity.getInputSlots().map { it.second.copy().apply { decrement(1) } }) < 0 }
    }

    private fun craft(blockEntity: CrafterBlock.Entity, inventory: CraftingInventory, recipe: CraftingRecipe) {
        val output = recipe.craft(inventory)
        val outputSlot = blockEntity.getOutputSlot()
        if (outputSlot.second.canInsert(output)) {
            recipe.ingredients.forEach { ingredient ->
                blockEntity.removeStack(blockEntity.getInputSlots().indexOfFirst { ingredient.test(it.second) && it.second.count > 1 }, 1)
            }
            blockEntity.insert(output)
        }
    }

}

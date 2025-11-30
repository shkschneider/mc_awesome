package io.github.shkschneider.awesome.machines.recycler

import io.github.shkschneider.awesome.custom.SimpleSidedInventory
import io.github.shkschneider.awesome.machines.AwesomeMachine
import io.github.shkschneider.awesome.machines.AwesomeMachineScreen
import io.github.shkschneider.awesome.machines.AwesomeMachineScreenHandler
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.SidedInventory
import net.minecraft.screen.ArrayPropertyDelegate
import net.minecraft.screen.PropertyDelegate
import net.minecraft.screen.ScreenHandlerType
import net.minecraft.text.Text
import kotlin.math.roundToInt

@Suppress("RemoveRedundantQualifierName")
class RecyclerScreen(
    machine: AwesomeMachine<RecyclerBlock.Entity, RecyclerScreen.Handler>,
    handler: RecyclerScreen.Handler,
    playerInventory: PlayerInventory,
    title: Text?,
) : AwesomeMachineScreen<RecyclerBlock.Entity, RecyclerScreen.Handler>(machine, handler, playerInventory, title) {

    override fun drawBackground(matrices: MatrixStack, delta: Float, mouseX: Int, mouseY: Int) {
        super.drawBackground(matrices, delta, mouseX, mouseY)
        if (handler.duration > 0) {
            // arrow
            val progress = ((handler.progress.toFloat() / handler.duration.toFloat()) * (197 - 176 + 1).toFloat()).roundToInt()
            drawTexture(matrices, x + 62 - 1, y + 36 - 1, 176, 15, progress, 30 - 15)
        }
    }
    
    class Handler(
        machine: AwesomeMachine<RecyclerBlock.Entity, RecyclerScreen.Handler>,
        type: ScreenHandlerType<RecyclerScreen.Handler>?,
        syncId: Int,
        playerInventory: PlayerInventory,
        sidedInventory: SidedInventory = SimpleSidedInventory(machine.io.size),
        properties: PropertyDelegate = ArrayPropertyDelegate(machine.properties),
    ) : AwesomeMachineScreenHandler<RecyclerBlock.Entity>(
        type, syncId, playerInventory, sidedInventory, properties
    ) {
        
        init {
            addSlots(
                30 + 4 to 31 + 4,
                92 to 17, 110 to 17, 128 to 17,
                92 to 35, 110 to 35, 128 to 35,
                92 to 53, 110 to 53, 128 to 53,
            )
            addPlayerSlots()
        }

    }

}

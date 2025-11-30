package io.github.shkschneider.awesome.machines.factory

import io.github.shkschneider.awesome.AwesomeMachines
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
class FactoryScreen(
    machine: AwesomeMachine<FactoryBlock.Entity, FactoryScreen.Handler>,
    handler: FactoryScreen.Handler,
    playerInventory: PlayerInventory,
    title: Text?,
) : AwesomeMachineScreen<FactoryBlock.Entity, FactoryScreen.Handler>(machine, handler, playerInventory, title) {

    override fun drawBackground(matrices: MatrixStack, delta: Float, mouseX: Int, mouseY: Int) {
        super.drawBackground(matrices, delta, mouseX, mouseY)
        if (handler.fuel > 0) {
            // flames
            val progress = ((handler.fuel.toFloat() / AwesomeMachines.fuel.time.toFloat()) * (189 - 176 + 1).toFloat()).roundToInt()
            drawTexture(matrices, x + 63 - 1, y + 37 + 14 - progress - 1, 176, 14 - progress - 1, 14, progress + 1)
        }
        if (handler.progress > 0 && !handler.getSlot(3).stack.isEmpty) {
            // arrow
            val progress = ((handler.progress.toFloat() / handler.duration.toFloat()) * (197 - 176 + 1).toFloat()).roundToInt()
            drawTexture(matrices, x + 102 - 1, y + 37 - 1, 176, 15, progress, 30 - 15)
        } else if (handler.progress > 0) {
            // bubbles
            val progress = ((handler.progress.toFloat() / handler.duration.toFloat()) * (60 - 32 + 1).toFloat()).roundToInt()
            drawTexture(matrices, x + 47 - 1, y + 43 + 28 - progress - 1, 176, 61 - progress - 1, 186 - 176, progress + 1)
            drawTexture(matrices, x + 83 - 1, y + 43 + 28 - progress - 1, 176, 61 - progress - 1, 186 - 176, progress + 1)
        }
    }

    class Handler(
        machine: AwesomeMachine<FactoryBlock.Entity, FactoryScreen.Handler>,
        type: ScreenHandlerType<FactoryScreen.Handler>?,
        syncId: Int,
        playerInventory: PlayerInventory,
        sidedInventory: SidedInventory = SimpleSidedInventory(machine.io.size),
        properties: PropertyDelegate = ArrayPropertyDelegate(machine.properties),
    ) : AwesomeMachineScreenHandler<FactoryBlock.Entity>(
        type, syncId, playerInventory, sidedInventory, properties
    ) {
        
        init {
            addSlots(
                17 to 35,
                44 to 17, 62 to 17, 80 to 17,
                62 to 53,
                135 + 4 to 31 + 4,
            )
            addPlayerSlots()
        }

    }

}

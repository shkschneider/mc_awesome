package io.github.shkschneider.awesome.machines.cultivator

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
class CultivatorScreen(
    machine: AwesomeMachine<CultivatorBlock.Entity, CultivatorScreen.Handler>,
    handler: CultivatorScreen.Handler,
    playerInventory: PlayerInventory,
    title: Text?,
) : AwesomeMachineScreen<CultivatorBlock.Entity, CultivatorScreen.Handler>(machine, handler, playerInventory, title) {

    override fun drawBackground(matrices: MatrixStack, delta: Float, mouseX: Int, mouseY: Int) {
        super.drawBackground(matrices, delta, mouseX, mouseY)
        if (handler.fuel > 0) {
            // flames
            val progress = ((handler.fuel.toFloat() / AwesomeMachines.fuel.time.toFloat()) * (189 - 176 + 1).toFloat()).roundToInt()
            drawTexture(matrices, x + 72 - 1, y + 37 + 14 - progress - 1, 176, 14 - progress - 1, 14, progress + 1)
        }
        if (handler.progress > 0) {
            // arrow
            val progress = ((handler.progress.toFloat() / handler.duration.toFloat()) * (197 - 176 + 1).toFloat()).roundToInt()
            drawTexture(matrices, x + 97 - 1, y + 36 - 1, 176, 15, progress, 30 - 15)
        }
    }

    class Handler(
        machine: AwesomeMachine<CultivatorBlock.Entity, CultivatorScreen.Handler>,
        type: ScreenHandlerType<CultivatorScreen.Handler>?,
        syncId: Int,
        playerInventory: PlayerInventory,
        sidedInventory: SidedInventory = SimpleSidedInventory(machine.io.size),
        properties: PropertyDelegate = ArrayPropertyDelegate(machine.properties),
    ) : AwesomeMachineScreenHandler<CultivatorBlock.Entity>(
        type, syncId, playerInventory, sidedInventory, properties
    ) {

        val efficiency: Int get() = getCustomProperty(0)
        val fortune: Int get() = getCustomProperty(1)

        init {
            addSlots(
                44 to 35,
                71 to 17,
                71 to 53,
                129 + 4 to 31 + 4,
                133 to 61,
            )
            addPlayerSlots()
        }

    }

}

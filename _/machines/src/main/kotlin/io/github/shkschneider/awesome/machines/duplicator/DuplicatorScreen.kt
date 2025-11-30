package io.github.shkschneider.awesome.machines.duplicator

import io.github.shkschneider.awesome.AwesomeMachines
import io.github.shkschneider.awesome.core.AwesomeColors
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
class DuplicatorScreen(
    machine: AwesomeMachine<DuplicatorBlock.Entity, DuplicatorScreen.Handler>,
    handler: DuplicatorScreen.Handler,
    playerInventory: PlayerInventory,
    title: Text?,
) : AwesomeMachineScreen<DuplicatorBlock.Entity, DuplicatorScreen.Handler>(machine, handler, playerInventory, title) {

    override fun drawBackground(matrices: MatrixStack, delta: Float, mouseX: Int, mouseY: Int) {
        super.drawBackground(matrices, delta, mouseX, mouseY)
        if (handler.fuel > 0) {
            // flames
            val progress = ((handler.fuel.toFloat() / AwesomeMachines.fuel.time.toFloat()) * (189 - 176 + 1).toFloat()).roundToInt()
            drawTexture(matrices, x + 81 - 1, y + 36 + 14 - progress - 1, 176, 14 - progress - 1, 14, progress + 1)
        }
        if (handler.progress > 0) {
            // gauge
            val progress = handler.progress * 55 / handler.duration
            drawTexture(matrices, x + 8, y + 7 + 55 - progress - 1, 176, 111, 192 - 176, progress + 1)
        }
        drawTextWithShadow(matrices, textRenderer, Text.of("up ${handler.efficiency} b"), x + 26, y + 54, AwesomeColors.white)
    }

    class Handler(
        machine: AwesomeMachine<DuplicatorBlock.Entity, DuplicatorScreen.Handler>,
        type: ScreenHandlerType<DuplicatorScreen.Handler>?,
        syncId: Int,
        playerInventory: PlayerInventory,
        sidedInventory: SidedInventory = SimpleSidedInventory(machine.io.size),
        properties: PropertyDelegate = ArrayPropertyDelegate(machine.properties),
    ) : AwesomeMachineScreenHandler<DuplicatorBlock.Entity>(
        type, syncId, playerInventory, sidedInventory, properties
    ) {
        
        val efficiency: Int get() = getCustomProperty(0)

        init {
            addSlots(
                44 to 35,
                80 to 53,
                116 to 35,
            )
            addPlayerSlots()
        }

    }

}

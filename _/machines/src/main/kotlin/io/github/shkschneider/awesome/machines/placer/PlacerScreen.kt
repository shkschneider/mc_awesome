package io.github.shkschneider.awesome.machines.placer

import io.github.shkschneider.awesome.core.AwesomeColors
import io.github.shkschneider.awesome.custom.Minecraft
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

@Suppress("RemoveRedundantQualifierName")
class PlacerScreen(
    machine: AwesomeMachine<PlacerBlock.Entity, PlacerScreen.Handler>,
    handler: PlacerScreen.Handler,
    playerInventory: PlayerInventory,
    title: Text?,
) : AwesomeMachineScreen<PlacerBlock.Entity, PlacerScreen.Handler>(machine, handler, playerInventory, title) {

    override fun drawBackground(matrices: MatrixStack, delta: Float, mouseX: Int, mouseY: Int) {
        super.drawBackground(matrices, delta, mouseX, mouseY)
        if (handler.progress > 0) {
            // gauge
            val progress = handler.progress * 55 / handler.duration
            drawTexture(matrices, x + 8, y + 7 + 55 - progress - 1, 176, 111, 192 - 176, progress + 1)
        }
        drawTextWithShadow(matrices, textRenderer, Text.of("${Minecraft.TICKS / handler.duration}/s"), x + 26, y + 54, AwesomeColors.white)
    }

    class Handler(
        machine: AwesomeMachine<PlacerBlock.Entity, PlacerScreen.Handler>,
        type: ScreenHandlerType<PlacerScreen.Handler>?,
        syncId: Int,
        playerInventory: PlayerInventory,
        sidedInventory: SidedInventory = SimpleSidedInventory(machine.io.size),
        properties: PropertyDelegate = ArrayPropertyDelegate(machine.properties),
    ) : AwesomeMachineScreenHandler<PlacerBlock.Entity>(
        type, syncId, playerInventory, sidedInventory, properties
    ) {
        
        val efficiency: Int get() = getCustomProperty(0)

        init {
            addSlots(
                44 to 35,
                116 to 35,
            )
            addPlayerSlots()
        }

    }

}

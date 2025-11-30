package io.github.shkschneider.awesome.machines.collector

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

@Suppress("RemoveRedundantQualifierName")
class CollectorScreen(
    machine: AwesomeMachine<CollectorBlock.Entity, CollectorScreen.Handler>,
    handler: CollectorScreen.Handler,
    playerInventory: PlayerInventory,
    title: Text?,
) : AwesomeMachineScreen<CollectorBlock.Entity, CollectorScreen.Handler>(machine, handler, playerInventory, title) {

    override fun drawBackground(matrices: MatrixStack, delta: Float, mouseX: Int, mouseY: Int) {
        super.drawBackground(matrices, delta, mouseX, mouseY)
        drawTextWithShadow(matrices, textRenderer, Text.of("${handler.efficiency}x${handler.efficiency}"), x + 26, y + 54, AwesomeColors.white)
    }

    class Handler(
        machine: AwesomeMachine<CollectorBlock.Entity, CollectorScreen.Handler>,
        type: ScreenHandlerType<CollectorScreen.Handler>?,
        syncId: Int,
        playerInventory: PlayerInventory,
        sidedInventory: SidedInventory = SimpleSidedInventory(machine.io.size),
        properties: PropertyDelegate = ArrayPropertyDelegate(machine.properties),
    ) : AwesomeMachineScreenHandler<CollectorBlock.Entity>(
        type, syncId, playerInventory, sidedInventory, properties
    ) {

        val efficiency: Int get() = getCustomProperty(0)

        init {
            addSlots(
                26 to 35,
                62 to 17, 80 to 17, 98 to 17,
                62 to 35, 80 to 35, 98 to 35,
                62 to 53, 80 to 53, 98 to 53,
            )
            addPlayerSlots()
        }

    }

}

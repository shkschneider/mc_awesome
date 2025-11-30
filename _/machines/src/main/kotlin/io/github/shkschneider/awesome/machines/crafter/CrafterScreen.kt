package io.github.shkschneider.awesome.machines.crafter

import io.github.shkschneider.awesome.custom.SimpleSidedInventory
import io.github.shkschneider.awesome.custom.TemplateSlot
import io.github.shkschneider.awesome.machines.AwesomeMachine
import io.github.shkschneider.awesome.machines.AwesomeMachineScreen
import io.github.shkschneider.awesome.machines.AwesomeMachineScreenHandler
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.SidedInventory
import net.minecraft.screen.ArrayPropertyDelegate
import net.minecraft.screen.PropertyDelegate
import net.minecraft.screen.ScreenHandlerType
import net.minecraft.screen.slot.Slot
import net.minecraft.screen.slot.SlotActionType
import net.minecraft.text.Text
import kotlin.math.roundToInt

@Suppress("RemoveRedundantQualifierName")
class CrafterScreen(
    machine: AwesomeMachine<CrafterBlock.Entity, CrafterScreen.Handler>,
    handler: CrafterScreen.Handler,
    playerInventory: PlayerInventory,
    title: Text?,
) : AwesomeMachineScreen<CrafterBlock.Entity, CrafterScreen.Handler>(machine, handler, playerInventory, title) {

    override fun drawBackground(matrices: MatrixStack, delta: Float, mouseX: Int, mouseY: Int) {
        super.drawBackground(matrices, delta, mouseX, mouseY)
        if (handler.progress > 0) {
            // arrow
            val progress = ((handler.progress.toFloat() / handler.duration.toFloat()) * (197 - 176 + 1).toFloat()).roundToInt()
            drawTexture(matrices, x + 90 - 1, y + 35 - 1, 176, 15, progress, 30 - 15)
        }
    }

    class Handler(
        machine: AwesomeMachine<CrafterBlock.Entity, CrafterScreen.Handler>,
        type: ScreenHandlerType<CrafterScreen.Handler>?,
        syncId: Int,
        playerInventory: PlayerInventory,
        sidedInventory: SidedInventory = SimpleSidedInventory(machine.io.size),
        properties: PropertyDelegate = ArrayPropertyDelegate(machine.properties),
    ) : AwesomeMachineScreenHandler<CrafterBlock.Entity>(
        type, syncId, playerInventory, sidedInventory, properties
    ) {

        init {
            addSlots(
                // inputs
                TemplateSlot(internalInventory, 0, 30, 17),
                TemplateSlot(internalInventory, 1, 48, 17),
                TemplateSlot(internalInventory, 2, 66, 17),
                TemplateSlot(internalInventory, 3, 30, 35),
                TemplateSlot(internalInventory, 4, 48, 35),
                TemplateSlot(internalInventory, 5, 66, 35),
                TemplateSlot(internalInventory, 6, 30, 53),
                TemplateSlot(internalInventory, 7, 48, 53),
                TemplateSlot(internalInventory, 8, 66, 53),
                // output
                Slot(internalInventory, 9, 120 + 4, 31 + 4),
            )
            addPlayerSlots()
        }

        override fun onSlotClick(slotIndex: Int, button: Int, actionType: SlotActionType, player: PlayerEntity) {
            TemplateSlot.onSlotClick(slots, slotIndex, internalInventory, cursorStack)?.let { cursorStack ->
                this.cursorStack = cursorStack
            } ?: run {
                super.onSlotClick(slotIndex, button, actionType, player)
            }
        }

    }

}

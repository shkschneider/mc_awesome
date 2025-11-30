package io.github.shkschneider.awesome.machines

import io.github.shkschneider.awesome.core.AwesomeBlockEntity
import io.github.shkschneider.awesome.core.AwesomeBlockScreen
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.SidedInventory
import net.minecraft.screen.PropertyDelegate
import net.minecraft.screen.ScreenHandler
import net.minecraft.screen.ScreenHandlerType

abstract class AwesomeMachineScreenHandler<BE : AwesomeBlockEntity.WithInventory>(
    type: ScreenHandlerType<out ScreenHandler>?,
    syncId: Int,
    playerInventory: PlayerInventory,
    internalInventory: SidedInventory,
    properties: PropertyDelegate,
) : AwesomeBlockScreen.Handler(type, syncId, playerInventory, internalInventory, properties) {

    val progress: Int get() = getProperty(0)
    val duration: Int get() = getProperty(1)
    val fuel: Int get() = getProperty(2)

    fun getCustomProperty(index: Int): Int =
        getProperty(AwesomeMachine.PROPERTIES + index)

    /*
    init {
        addSlots()
        addPlayerSlots()
    }
    */

}

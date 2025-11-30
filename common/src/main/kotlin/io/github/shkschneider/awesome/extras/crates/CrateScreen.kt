package io.github.shkschneider.awesome.extras.crates

import io.github.shkschneider.awesome.core.AwesomeBlockScreen
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.Inventory
import net.minecraft.inventory.SimpleInventory
import net.minecraft.screen.slot.Slot
import net.minecraft.text.Text

@Suppress("RemoveRedundantQualifierName")
class CrateScreen(
    private val crate: Crate,
    handler: CrateScreen.Handler, playerInventory: PlayerInventory, title: Text,
) : AwesomeBlockScreen<CrateScreen.Handler>(
    crate.id.path, handler, playerInventory, title,
) {

    override fun init() {
        backgroundHeight = crate.size.backgroundHeight
        playerInventoryTitleY = backgroundHeight - 94
        super.init()
    }

    class Handler(
        private val crate: Crate,
        syncId: Int,
        playerInventory: PlayerInventory,
        internalInventory: Inventory = SimpleInventory(crate.size.total),
    ) : AwesomeBlockScreen.Handler(crate.screen, syncId, playerInventory, internalInventory) {

        init {
            addProperties(properties)
            (0 until crate.size.height).forEach { row ->
                (0 until crate.size.width).forEach { column ->
                    addSlot(Slot(internalInventory, row * crate.size.width + column, 8 + column * 18, 17 + row * 18))
                }
            }
            addPlayerSlots()
        }

        override fun addPlayerSlots() {
            // inventory
            (0 until 3).forEach { row ->
                (0 until 9).forEach { column ->
                    addSlot(Slot(playerInventory, row * 9 + column + 9, 8 + column * 18, crate.size.playerInventoryOffset + row * 18))
                }
            }
            // hotbar
            (0 until 9).forEach { column ->
                addSlot(Slot(playerInventory, column, 8 + column * 18, crate.size.playerHotbarOffset))
            }
        }

    }

}

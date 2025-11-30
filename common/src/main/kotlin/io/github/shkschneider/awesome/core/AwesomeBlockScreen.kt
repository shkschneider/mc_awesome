package io.github.shkschneider.awesome.core

import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.gui.screen.ingame.HandledScreen
import net.minecraft.client.render.GameRenderer
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.Inventory
import net.minecraft.inventory.SimpleInventory
import net.minecraft.item.ItemStack
import net.minecraft.screen.ArrayPropertyDelegate
import net.minecraft.screen.PropertyDelegate
import net.minecraft.screen.ScreenHandler
import net.minecraft.screen.ScreenHandlerType
import net.minecraft.screen.slot.Slot
import net.minecraft.text.Text

abstract class AwesomeBlockScreen<SH : AwesomeBlockScreen.Handler>(
    private val name: String,
    handler: SH,
    playerInventory: PlayerInventory,
    title: Text,
) : HandledScreen<SH>(handler, playerInventory, title) {

    override fun init() {
        super.init()
        titleX = (backgroundWidth - textRenderer.getWidth(title)) / 2
    }

    override fun drawBackground(matrices: MatrixStack, delta: Float, mouseX: Int, mouseY: Int) {
        RenderSystem.setShaderTexture(0, AwesomeUtils.identifier("textures/gui/$name.png"))
        val x = (width - backgroundWidth) / 2
        val y = (height - backgroundHeight) / 2
        drawTexture(matrices, x, y, 0, 0, backgroundWidth, backgroundHeight)
    }

    override fun drawForeground(matrices: MatrixStack, mouseX: Int, mouseY: Int) {
        RenderSystem.setShader(GameRenderer::getRenderTypeTextShader)
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f)
        super.drawForeground(matrices, mouseX, mouseY)
    }

    override fun render(matrices: MatrixStack, mouseX: Int, mouseY: Int, delta: Float) {
        renderBackground(matrices)
        super.render(matrices, mouseX, mouseY, delta)
        drawMouseoverTooltip(matrices, mouseX, mouseY)
    }

    abstract class Handler(
        type: ScreenHandlerType<out ScreenHandler>?,
        syncId: Int,
        protected val playerInventory: PlayerInventory,
        protected var internalInventory: Inventory = SimpleInventory(0),
        protected var properties: PropertyDelegate = ArrayPropertyDelegate(0)
    ) : ScreenHandler(type, syncId) {

        init {
            addProperties()
            internalInventory.onOpen(playerInventory.player)
            // addSlots(...)
            // addPlayerSlots()
        }

        private fun addProperties() {
            addProperties(properties)
        }

        open fun getProperty(index: Int): Int =
            properties.get(index)

        fun addSlots(vararg slots: Pair<Int, Int>) {
            slots.forEachIndexed { index, pair ->
                addSlot(Slot(internalInventory, index, pair.first, pair.second))
            }
        }

        fun addSlots(vararg slots: Slot) {
            slots.forEach { slot ->
                addSlot(slot)
            }
        }

        // https://fabricmc.net/wiki/tutorial:containers
        protected open fun addPlayerSlots() {
            // inventory
            for (i in 0..2) {
                for (l in 0..8) {
                    addSlot(Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 84 + i * 18))
                }
            }
            // hotbar
            for (i in 0..8) {
                addSlot(Slot(playerInventory, i, 8 + i * 18, 142))
            }
        }

        /**
         * Thanks to Kaupenjoe
         * Link: https://www.youtube.com/c/TKaupenjoe
         * Link: https://fabricmc.net/wiki/tutorial:containers
         * Link: https://github.com/FabricMC/yarn/issues/2944
         */
        override fun transferSlot(player: PlayerEntity, index: Int): ItemStack {
            val slot = slots.getOrNull(index)?.takeIf { it.hasStack() } ?: return ItemStack.EMPTY
            val stack = slot.stack.copy()
            if (index < internalInventory.size()) {
                if (!insertItem(slot.stack, internalInventory.size(), slots.size, true)) {
                    return ItemStack.EMPTY
                }
            } else if (!insertItem(slot.stack, 0, internalInventory.size(), false)) {
                return ItemStack.EMPTY
            }
            slot.markDirty()
            return stack
        }

        override fun canUse(player: PlayerEntity): Boolean =
            internalInventory.canPlayerUse(player)

    }

}


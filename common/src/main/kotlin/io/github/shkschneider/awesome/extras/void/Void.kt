package io.github.shkschneider.awesome.extras.void

import io.github.shkschneider.awesome.custom.Minecraft
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry

object Void {

    const val ID = "void"

    operator fun invoke() {
        val block = VoidBlock()
        if (Minecraft.isClient) {
            BlockEntityRendererRegistry.register(block.entityType) { VoidBlockEntityRenderer(it) }
            // 1.19 BlockEntityRendererFactories.register(block.entityType) { VoidBlockEntityRenderer(it) }
        }
    }

}

package io.github.shkschneider.awesome.extras.void

import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.render.RenderLayer
import net.minecraft.client.render.VertexConsumer
import net.minecraft.client.render.VertexConsumerProvider
import net.minecraft.client.render.block.entity.BlockEntityRenderer
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.util.math.Direction
import net.minecraft.util.math.Matrix4f

@Environment(EnvType.CLIENT)
class VoidBlockEntityRenderer(context: BlockEntityRendererFactory.Context) : BlockEntityRenderer<VoidBlockEntity> {

    override fun render(blockEntity: VoidBlockEntity, f: Float, matrixStack: MatrixStack, vertexConsumerProvider: VertexConsumerProvider, i: Int, j: Int) {
        renderSides(blockEntity, matrixStack.peek().positionMatrix, vertexConsumerProvider.getBuffer(RenderLayer.getEndGateway()))
    }

    private fun renderSides(entity: VoidBlockEntity, matrix: Matrix4f, vertexConsumer: VertexConsumer) {
        renderSide(entity, matrix, vertexConsumer, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, Direction.SOUTH)
        renderSide(entity, matrix, vertexConsumer, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, Direction.NORTH)
        renderSide(entity, matrix, vertexConsumer, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, Direction.EAST)
        renderSide(entity, matrix, vertexConsumer, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, Direction.WEST)
        renderSide(entity, matrix, vertexConsumer, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, Direction.DOWN)
        renderSide(entity, matrix, vertexConsumer, 0.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, Direction.UP)
    }

    private fun renderSide(entity: VoidBlockEntity, model: Matrix4f, vertices: VertexConsumer, x1: Float, x2: Float, y1: Float, y2: Float, z1: Float, z2: Float, z3: Float, z4: Float, side: Direction) {
        if (entity.shouldDrawSide(side)) {
            vertices.vertex(model, x1, y1, z1).next()
            vertices.vertex(model, x2, y1, z2).next()
            vertices.vertex(model, x2, y2, z3).next()
            vertices.vertex(model, x1, y2, z4).next()
        }
    }

}

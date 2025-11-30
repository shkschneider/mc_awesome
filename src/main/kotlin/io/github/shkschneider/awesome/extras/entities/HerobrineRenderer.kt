package io.github.shkschneider.awesome.extras.entities

import io.github.shkschneider.awesome.core.AwesomeUtils
import io.github.shkschneider.awesome.core.ext.isBeingLookedAt
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.render.RenderLayer
import net.minecraft.client.render.VertexConsumerProvider
import net.minecraft.client.render.entity.EntityRendererFactory
import net.minecraft.client.render.entity.MobEntityRenderer
import net.minecraft.client.render.entity.feature.EyesFeatureRenderer
import net.minecraft.client.render.entity.feature.FeatureRendererContext
import net.minecraft.client.render.entity.model.EntityModelLayers
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.util.Identifier

@Environment(EnvType.CLIENT)
class HerobrineRenderer(
    context: EntityRendererFactory.Context,
) : MobEntityRenderer<Herobrine, HerobrineModel<Herobrine>>(
    context, HerobrineModel(context.getPart(EntityModelLayers.PLAYER)), /* shadowRadius */ 0.5F,
) {

    init {
        addFeature(Eyes(this))
    }

    override fun getTexture(entity: Herobrine): Identifier =
        AwesomeUtils.identifier("textures/entity/steve.png")

    @Environment(EnvType.CLIENT)
    class Eyes(
        featureRendererContext: FeatureRendererContext<Herobrine, HerobrineModel<Herobrine>>,
    ) : EyesFeatureRenderer<Herobrine, HerobrineModel<Herobrine>>(
        featureRendererContext,
    ) {

        override fun render(matrices: MatrixStack, vertexConsumers: VertexConsumerProvider, light: Int, entity: Herobrine, limbAngle: Float, limbDistance: Float, tickDelta: Float, animationProgress: Float, headYaw: Float, headPitch: Float) {
            if (entity.isAttacking || entity.target?.let { entity.isBeingLookedAt(it) } == true) {
                super.render(matrices, vertexConsumers, light * light, entity, limbAngle, limbDistance, tickDelta, animationProgress, headYaw, headPitch)
            }
        }

        override fun getEyesTexture(): RenderLayer =
            RenderLayer.getEyes(AwesomeUtils.identifier("textures/entity/eyes.png"))

    }

}

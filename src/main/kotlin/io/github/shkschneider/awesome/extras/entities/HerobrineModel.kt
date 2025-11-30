package io.github.shkschneider.awesome.extras.entities

import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.model.ModelPart
import net.minecraft.client.render.entity.model.PlayerEntityModel
import net.minecraft.entity.LivingEntity

@Environment(EnvType.CLIENT)
class HerobrineModel<T : LivingEntity>(modelPart: ModelPart) : PlayerEntityModel<T>(modelPart, false)

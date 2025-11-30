package io.github.shkschneider.awesome.mixins;

import io.github.shkschneider.awesome.Awesome;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.village.VillagerType;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Author: Hyperbean18
 * License: MIT
 * Source: https://github.com/Hyperbean18/Villagers-Follow-Emeralds-Fabric
 */
@Mixin(VillagerEntity.class)
abstract class VillagersFollowEmeraldBlockMixin extends MerchantEntity {

    public VillagersFollowEmeraldBlockMixin(EntityType<? extends MerchantEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "<init>(Lnet/minecraft/entity/EntityType;Lnet/minecraft/world/World;Lnet/minecraft/village/VillagerType;)V", at = @At(value = "TAIL"))
    private void inject(EntityType<? extends VillagerEntity> entityType, World world, VillagerType type, CallbackInfo ci) {
        if (Awesome.INSTANCE.getCONFIG().getExtras().getVillagersFollowEmeraldBlock()) {
            goalSelector.add(2, new TemptGoal(this, 0.5D, Ingredient.ofItems(Items.EMERALD_BLOCK), false));
        }
    }

}

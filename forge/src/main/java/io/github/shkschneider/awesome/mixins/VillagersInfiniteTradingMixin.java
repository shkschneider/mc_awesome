package io.github.shkschneider.awesome.mixins;

import io.github.shkschneider.awesome.Awesome;
import net.minecraft.entity.passive.VillagerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Author: ClusterFluxMC
 * License: MIT
 * Source: https://github.com/ClusterFluxMC/extension
 */
@Mixin(VillagerEntity.class)
public class VillagersInfiniteTradingMixin {

    @Shadow
    private int restocksToday;

    @Inject(method = "tick", at = @At("HEAD"))
    public void tick(CallbackInfo info) {
        if (Awesome.INSTANCE.getCONFIG().getExtras().getVillagersInfiniteTrading()) {
            restocksToday = 0;
            //noinspection ConstantConditions
            ((VillagerEntity) (Object) this).restock();
        }
    }

}

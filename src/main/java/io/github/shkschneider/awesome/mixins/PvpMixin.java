package io.github.shkschneider.awesome.mixins;

import io.github.shkschneider.awesome.Awesome;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PvpMixin {

    @Inject(method = "damage", at = @At("HEAD"), cancellable = true)
    private void damage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> info) {
        if (!Awesome.INSTANCE.getCONFIG().getExtras().getPvp()) {
            if (source.getSource() instanceof PlayerEntity) {
                info.cancel();
            }
        }
    }

}

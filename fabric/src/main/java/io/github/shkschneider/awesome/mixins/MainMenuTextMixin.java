package io.github.shkschneider.awesome.mixins;

import io.github.shkschneider.awesome.Awesome;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.SharedConstants;
import net.minecraft.client.resource.SplashTextResourceSupplier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Thanks Linkie & Shedaniel
 * Link: https://linkie.shedaniel.me/mappings
 */
@Pseudo
@Environment(EnvType.CLIENT)
@Mixin(SplashTextResourceSupplier.class)
public class MainMenuTextMixin {

    @Inject(method = "get()Ljava/lang/String;", at = @At("HEAD"), cancellable = true)
    public void get(CallbackInfoReturnable<String> info) {
        info.setReturnValue(
                String.format("%s %s!",
                        Awesome.INSTANCE.getID().substring(0, 1).toUpperCase() + Awesome.INSTANCE.getID().substring(1).toLowerCase(),
                        SharedConstants.getGameVersion().getName()
                )
        );
    }

}

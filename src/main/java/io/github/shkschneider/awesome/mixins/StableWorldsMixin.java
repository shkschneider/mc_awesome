package io.github.shkschneider.awesome.mixins;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

/**
 * Author: rdvdev2
 * License: LGPL3
 * Source: https://github.com/rdvdev2/DisableCustomWorldsAdvice
 */
@Pseudo
@Environment(EnvType.CLIENT)
@Mixin(MinecraftClient.class)
public abstract class StableWorldsMixin {

    // In 1.19.4, the method signature has changed
    @ModifyVariable(
        method = "startIntegratedServer",
        at = @At(value = "STORE"),
        ordinal = 0,
        name = "bl",
        require = 0
    )
    private boolean startIntegratedServer_getLifecycle(boolean bl) {
        return false;
    }

}

package io.github.shkschneider.awesome.mixins;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
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
@OnlyIn(Dist.CLIENT)
@Mixin(MinecraftClient.class)
public abstract class StableWorldsMixin {

    // boolean bl2 = saveProperties.getLifecycle() != Lifecycle.stable();
    @ModifyVariable(
        method = "startIntegratedServer(Ljava/lang/String;Ljava/util/function/Function;Ljava/util/function/Function;ZLnet/minecraft/client/MinecraftClient$WorldLoadAction;)V",
        at = @At(value = "FIELD", target = "Lnet/minecraft/client/MinecraftClient$WorldLoadAction;NONE:Lnet/minecraft/client/MinecraftClient$WorldLoadAction;", ordinal = 0),
        ordinal = 2,
        index = 11,
        name = "bl2",
        require = 1
    )
    private boolean startIntegratedServer_getLifecycle(boolean bl2) {
        return false;
    }

}

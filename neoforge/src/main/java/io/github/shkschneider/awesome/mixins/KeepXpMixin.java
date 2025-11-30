package io.github.shkschneider.awesome.mixins;

import io.github.shkschneider.awesome.experience.gamerules.KeepXpGameRule;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public abstract class KeepXpMixin {

    @Inject(method = "copyFrom", at = @At("TAIL"))
    private void copyFrom(ServerPlayerEntity oldPlayer, boolean alive, CallbackInfo ci) {
        if (!oldPlayer.world.getGameRules().getBoolean(GameRules.KEEP_INVENTORY) && oldPlayer.world.getGameRules().getBoolean(KeepXpGameRule.INSTANCE.getKey())) {
            @SuppressWarnings("DataFlowIssue") ServerPlayerEntity newPlayer = (ServerPlayerEntity) (Object) this;
            KeepXpGameRule.INSTANCE.invoke(oldPlayer, newPlayer);
        }
    }

}

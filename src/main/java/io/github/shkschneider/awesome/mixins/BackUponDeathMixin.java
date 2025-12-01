package io.github.shkschneider.awesome.mixins;

import io.github.shkschneider.awesome.Awesome;
import io.github.shkschneider.awesome.core.AwesomeLogger;
import io.github.shkschneider.awesome.custom.IEntityData;
import io.github.shkschneider.awesome.custom.Location;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public abstract class BackUponDeathMixin {

    @Inject(method = "onDeath", at = @At("TAIL"))
    private void onDeath(DamageSource damageSource, CallbackInfo ci) {
        AwesomeLogger.INSTANCE.debug("onDeath()");
    }

    @Inject(method = "copyFrom", at = @At("TAIL"))
    private void copyFrom(ServerPlayerEntity oldPlayer, boolean alive, CallbackInfo ci) {
        if (Awesome.INSTANCE.getCONFIG().getCommands().getBackUponDeath()) {
            @SuppressWarnings("DataFlowIssue") ServerPlayerEntity newPlayer = (ServerPlayerEntity) (Object) this;
            final Location location = new Location(oldPlayer.getWorld().getRegistryKey(), oldPlayer.getX(), oldPlayer.getY(), oldPlayer.getZ(), oldPlayer.getYaw(), oldPlayer.getPitch());
            AwesomeLogger.INSTANCE.debug("Saving death location: " + location);
            Location.Companion.writeLocation(((IEntityData) newPlayer).getData(), location, "back");
        }
    }

}

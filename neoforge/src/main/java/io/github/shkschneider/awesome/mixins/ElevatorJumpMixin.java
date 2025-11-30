package io.github.shkschneider.awesome.mixins;

import io.github.shkschneider.awesome.Awesome;
import io.github.shkschneider.awesome.extras.elevator.Elevator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class ElevatorJumpMixin extends Entity {

    public ElevatorJumpMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "jump()V", at = @At("INVOKE"))
    public void onJump(CallbackInfo ci) {
        if (Awesome.INSTANCE.getCONFIG().getExtras().getElevator()) {
            Elevator.INSTANCE.onJump((LivingEntity) (Object) this);
        }
    }

}

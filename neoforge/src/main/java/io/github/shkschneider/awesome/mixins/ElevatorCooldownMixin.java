package io.github.shkschneider.awesome.mixins;

import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(LivingEntity.class)
public interface ElevatorCooldownMixin {

    @Accessor
    int getJumpingCooldown();

    @Accessor("jumpingCooldown")
    void setJumpingCooldown(int cd);

}

package io.github.shkschneider.awesome.mixins;

import io.github.shkschneider.awesome.Awesome;
import io.github.shkschneider.awesome.AwesomeEnchantments;
import io.github.shkschneider.awesome.enchantments.SixthSenseEnchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class PlayerSneakMixin {

    @Inject(method = "setSneaking", at = @At(value = "TAIL"))
    private void onSneak(boolean sneaking, CallbackInfo ci) {
        @SuppressWarnings("DataFlowIssue") final LivingEntity livingEntity = (LivingEntity) (Object) this;
        if (livingEntity instanceof final PlayerEntity player && sneaking && Awesome.INSTANCE.getCONFIG().getEnchantments().getSixthSense()) {
            if (EnchantmentHelper.getLevel(AwesomeEnchantments.INSTANCE.getSixthSense(), player.getEquippedStack(EquipmentSlot.HEAD)) > 0) {
                SixthSenseEnchantment.Companion.invoke(player);
            }
        }
    }

}

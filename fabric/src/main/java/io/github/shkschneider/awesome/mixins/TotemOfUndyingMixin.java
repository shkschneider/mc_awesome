package io.github.shkschneider.awesome.mixins;

import io.github.shkschneider.awesome.Awesome;
import io.github.shkschneider.awesome.core.AwesomeLogger;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class TotemOfUndyingMixin {

    @Inject(method = "tryUseTotem", at = @At("HEAD"), cancellable = true)
    private void tryUseTotem(DamageSource source, CallbackInfoReturnable<Boolean> cir) {
        if (Awesome.INSTANCE.getCONFIG().getExtras().getTotemFromInventory()) {
            LivingEntity entity = (LivingEntity) (Object) this;
            if (entity instanceof ServerPlayerEntity player) {
                int slot = player.getInventory().getSlotWithStack(new ItemStack(Items.TOTEM_OF_UNDYING));
                if (slot >= 0) {
                    AwesomeLogger.INSTANCE.info("swapSlotWithHotbar(" + slot + ")");
                    player.getInventory().swapSlotWithHotbar(slot);
                }
            }
        }
        // and let the regular method do its thing
    }

}

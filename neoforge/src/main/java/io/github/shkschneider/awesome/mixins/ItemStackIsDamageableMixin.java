package io.github.shkschneider.awesome.mixins;

import io.github.shkschneider.awesome.Awesome;
import io.github.shkschneider.awesome.AwesomeEnchantments;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public class ItemStackIsDamageableMixin {

    @Inject(method = "isDamageable", at = @At("HEAD"), cancellable = true)
    private void isDamageable(CallbackInfoReturnable<Boolean> cir) {
        @SuppressWarnings("DataFlowIssue") final ItemStack itemStack = (ItemStack) (Object) this;
        if (Awesome.INSTANCE.getCONFIG().getEnchantments().getUnbreakable() && EnchantmentHelper.getLevel(AwesomeEnchantments.INSTANCE.getUnbreakable(), itemStack) > 0) {
            itemStack.setDamage(0);
            cir.setReturnValue(true);
        }
    }

}

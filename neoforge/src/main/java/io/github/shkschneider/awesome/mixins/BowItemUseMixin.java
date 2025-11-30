package io.github.shkschneider.awesome.mixins;

import io.github.shkschneider.awesome.Awesome;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Author: ClusterFluxMC
 * License: MIT
 * Source: https://github.com/ClusterFluxMC/extension
 */
@Mixin(BowItem.class)
public class BowItemUseMixin {

    @Inject(method="use(Lnet/minecraft/world/World;Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/Hand;)Lnet/minecraft/util/TypedActionResult;", at=@At("HEAD"), cancellable=true)
    private void use(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir) {
        if (!Awesome.INSTANCE.getCONFIG().getEnchantments().getInfinity()) {
            cir.setReturnValue(cir.getReturnValue());
        } else {
            final ItemStack stack = user.getStackInHand(hand);
            if (EnchantmentHelper.getLevel(Enchantments.INFINITY, stack) > 0) {
                user.setCurrentHand(hand);
                cir.setReturnValue(TypedActionResult.pass(stack));
            }
        }
    }

}

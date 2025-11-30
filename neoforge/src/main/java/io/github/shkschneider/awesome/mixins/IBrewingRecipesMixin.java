package io.github.shkschneider.awesome.mixins;

import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.recipe.BrewingRecipeRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

/**
 * Thanks to Kaupenjoe
 * Link: https://www.youtube.com/c/TKaupenjoe
 */
@Mixin(BrewingRecipeRegistry.class)
public interface IBrewingRecipesMixin {

    @Invoker("registerPotionRecipe")
    static void registerPotionRecipe(Potion input, Item item, Potion output) {
        throw new AssertionError();
    }

}

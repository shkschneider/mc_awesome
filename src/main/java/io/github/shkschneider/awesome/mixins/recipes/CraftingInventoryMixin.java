package io.github.shkschneider.awesome.mixins.recipes;

import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(CraftingInventory.class)
public interface CraftingInventoryMixin {

    @Accessor
    DefaultedList<ItemStack> getStacks();

    @Accessor
    @Mutable
    void setStacks(DefaultedList<ItemStack> stacks);

}

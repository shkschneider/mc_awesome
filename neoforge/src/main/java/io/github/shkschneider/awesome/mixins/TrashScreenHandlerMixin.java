package io.github.shkschneider.awesome.mixins;

import io.github.shkschneider.awesome.Awesome;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@OnlyIn(Dist.CLIENT)
@Mixin(PlayerScreenHandler.class)
public abstract class TrashScreenHandlerMixin extends ScreenHandler {

    private SimpleInventory trashInventory = null;
    private int trashIndex = -1;

    public TrashScreenHandlerMixin(ScreenHandlerType<?> screenHandlerType, int i) {
        super(screenHandlerType, i);
    }

    @Inject(method = "<init>(Lnet/minecraft/entity/player/PlayerInventory;ZLnet/minecraft/entity/player/PlayerEntity;)V", at = @At(value = "TAIL"))
    private void init(PlayerInventory inventory, boolean onServer, final PlayerEntity owner, CallbackInfo ci) {
        if (Awesome.INSTANCE.getCONFIG().getExtras().getTrashSlot()) {
            trashInventory = new SimpleInventory(1);
            addSlot(new Slot(trashInventory, 0, 152, 66));
            trashIndex = slots.size() - 1;
        }
    }

    @Override
    public void onSlotClick(int slotIndex, int button, SlotActionType actionType, PlayerEntity player) {
        // Caused by: java.lang.IndexOutOfBoundsException: Index -999 out of bounds for length X
        if (slotIndex >= 0 && slotIndex == trashIndex) {
            final ItemStack cursorStack = getCursorStack() != null ? getCursorStack() : ItemStack.EMPTY;
            final ItemStack slotStack = getSlot(slotIndex) != null ? getSlot(slotIndex).getStack() : ItemStack.EMPTY;
            if (!cursorStack.isEmpty() && !slotStack.isEmpty()) {
                trashInventory.clear();
            }
        }
        super.onSlotClick(slotIndex, button, actionType, player);
    }

    @Override
    public void close(PlayerEntity player) {
        if (trashInventory != null) {
            trashInventory.clear();
        }
        super.close(player);
    }

}

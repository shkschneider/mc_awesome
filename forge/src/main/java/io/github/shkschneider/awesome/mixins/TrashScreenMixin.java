package io.github.shkschneider.awesome.mixins;

import com.mojang.blaze3d.systems.RenderSystem;
import io.github.shkschneider.awesome.Awesome;
import io.github.shkschneider.awesome.core.AwesomeUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.gui.screen.recipebook.RecipeBookProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(InventoryScreen.class)
public abstract class TrashScreenMixin extends AbstractInventoryScreen<PlayerScreenHandler> implements RecipeBookProvider {

    public TrashScreenMixin(PlayerScreenHandler screenHandler, PlayerInventory playerInventory, Text text) {
        super(screenHandler, playerInventory, text);
    }

    @Inject(method = "drawBackground", at = @At(value = "TAIL"))
    private void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY, CallbackInfo ci) {
        if (Awesome.INSTANCE.getCONFIG().getExtras().getTrashSlot()) {
            RenderSystem.setShaderTexture(0, AwesomeUtils.INSTANCE.identifier("textures/gui/trashslot.png"));
            drawTexture(matrices, x + 152 - 1, y + 66 - 1, 152, 65, 1 + 16 + 1, 1 + 16 + 1);
        }
    }

}

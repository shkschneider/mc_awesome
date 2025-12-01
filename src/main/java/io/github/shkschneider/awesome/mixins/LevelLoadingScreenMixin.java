package io.github.shkschneider.awesome.mixins;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.WorldGenerationProgressTracker;
import net.minecraft.client.gui.screen.LevelLoadingScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Environment(EnvType.CLIENT)
@Mixin(LevelLoadingScreen.class)
public class LevelLoadingScreenMixin extends Screen {

    @Shadow
    @Final
    private WorldGenerationProgressTracker progressProvider;

    protected LevelLoadingScreenMixin(Text title) {
        super(title);
    }

    @Inject(method = "drawChunkMap", at = @At("HEAD"), cancellable = true)
    private static void drawChunkMap(DrawContext context, WorldGenerationProgressTracker progressProvider, int centerX, int centerY, int pixelSize, int pixelMargin, CallbackInfo ci) {
        ci.cancel();
    }

}

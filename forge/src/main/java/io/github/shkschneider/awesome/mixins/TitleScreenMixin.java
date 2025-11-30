package io.github.shkschneider.awesome.mixins;

import io.github.shkschneider.awesome.core.AwesomeColors;
import io.github.shkschneider.awesome.custom.Minecraft;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Environment(EnvType.CLIENT)
@Mixin(TitleScreen.class)
public abstract class TitleScreenMixin extends Screen {

    protected TitleScreenMixin(Text title) {
        super(title);
    }

    private Integer mods = 0;

    // https://github.com/PlutoSolutions/bedtrap-rip/blob/ac5f0bfa3de2458922ea9e1bbf1e2fbe66dc8c2d/src/minegame159/meteorclient/mixin/TitleScreenMixin.java#L65
    @Inject(method = "render", at = @At(value="INVOKE", target="Lnet/minecraft/client/gui/screen/TitleScreen;drawStringWithShadow(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/font/TextRenderer;Ljava/lang/String;III)V", ordinal = 0), cancellable = true)
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if (mods == 0) mods = FabricLoader.getInstance().getAllMods().size();
        DrawableHelper.drawStringWithShadow(matrices, textRenderer,
                String.format("Minecraft/Fabric %s + %d Mods!", Minecraft.INSTANCE.getVERSION(), mods),
                2, height - 10, AwesomeColors.INSTANCE.getWhite());
        super.render(matrices, mouseX, mouseY, delta);
        ci.cancel();
    }

}

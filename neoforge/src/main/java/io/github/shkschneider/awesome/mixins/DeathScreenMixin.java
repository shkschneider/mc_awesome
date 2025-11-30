package io.github.shkschneider.awesome.mixins;

import io.github.shkschneider.awesome.core.AwesomeClock;
import io.github.shkschneider.awesome.core.AwesomeUtils;
import io.github.shkschneider.awesome.custom.Location;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.DeathScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@OnlyIn(Dist.CLIENT)
@Mixin(DeathScreen.class)
public class DeathScreenMixin {

    @Shadow
    private int ticksSinceDeath;

    @Shadow
    private Text scoreText;

    @Inject(method = "render", at = @At("TAIL"))
    private void onRender(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        final MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;
        final AwesomeClock clock = AwesomeClock.Companion.elapsed(ticksSinceDeath);
        final Location location = new Location(
                client.player.world.getRegistryKey(),
                client.player.getX(),
                client.player.getY(),
                client.player.getZ(),
                client.player.getYaw(),
                client.player.getPitch()
        );
        scoreText = new TranslatableText(AwesomeUtils.INSTANCE.translatable("ui", "died"))
                .append(" ")
                .append(new LiteralText(clock.getDays() + "d" + clock.getHours() + "h" + clock.getMinutes()).formatted(Formatting.YELLOW))
                .append(" @ ")
                .append(new LiteralText(location.toString()).formatted(Formatting.YELLOW));
    }

}

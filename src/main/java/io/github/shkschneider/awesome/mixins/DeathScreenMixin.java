package io.github.shkschneider.awesome.mixins;

import io.github.shkschneider.awesome.core.AwesomeClock;
import io.github.shkschneider.awesome.core.AwesomeUtils;
import io.github.shkschneider.awesome.custom.Location;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.DeathScreen;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Environment(EnvType.CLIENT)
@Mixin(DeathScreen.class)
public class DeathScreenMixin {

    @Shadow
    private int ticksSinceDeath;

    @Shadow
    private Text scoreText;

    @Inject(method = "render", at = @At("TAIL"))
    private void onRender(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        final MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;
        final AwesomeClock clock = AwesomeClock.Companion.elapsed(ticksSinceDeath);
        final Location location = new Location(
                client.player.getWorld().getRegistryKey(),
                client.player.getX(),
                client.player.getY(),
                client.player.getZ(),
                client.player.getYaw(),
                client.player.getPitch()
        );
        scoreText = Text.translatable(AwesomeUtils.INSTANCE.translatable("ui", "died"))
                .append(" ")
                .append(Text.literal(clock.getDays() + "d" + clock.getHours() + "h" + clock.getMinutes()).formatted(Formatting.YELLOW))
                .append(" @ ")
                .append(Text.literal(location.toString()).formatted(Formatting.YELLOW));
    }

}

package io.github.shkschneider.awesome.mixins;

import io.github.shkschneider.awesome.Awesome;
import io.github.shkschneider.awesome.extras.LavaSponge;
import net.minecraft.block.Blocks;
import net.minecraft.block.SpongeBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SpongeBlock.class)
public class SpongeMixin {

    @Inject(method = "update", at = @At("TAIL"))
    protected void update(World world, BlockPos pos, CallbackInfo ci) {
        if (Awesome.INSTANCE.getCONFIG().getExtras().getSpongesInLava()) {
            if (LavaSponge.INSTANCE.invoke(world, pos)) {
                world.setBlockState(pos, Blocks.OBSIDIAN.getDefaultState());
            }
        }
    }

}

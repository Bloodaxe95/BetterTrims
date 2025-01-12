package com.bawnorton.bettertrims.client.mixin;

import com.bawnorton.bettertrims.client.config.ClientConfigManager;
import com.bawnorton.bettertrims.effect.ArmorTrimEffects;
import com.bawnorton.bettertrims.extend.EntityExtender;
import com.bawnorton.bettertrims.util.NumberWrapper;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(BackgroundRenderer.class)
public abstract class BackgroundRendererMixin {
    @ModifyConstant(method = "render", constant = @Constant(floatValue = 0.0F, ordinal = 0), slice = @Slice(from = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/GameRenderer;getNightVisionStrength(Lnet/minecraft/entity/LivingEntity;F)F")))
    private static float improveNightVisionWhenWearingSilver(float original, @Local Entity entity) {
        NumberWrapper increase = NumberWrapper.zero();
        if (entity instanceof EntityExtender extender && extender.betterTrims$shouldSilverApply()) {
            ArmorTrimEffects.SILVER.apply(extender.betterTrims$getTrimmables(), () -> increase.increment(ClientConfigManager.getConfig().silverEffects.improveVision));
        }
        return original + increase.getFloat();
    }
}

package com.bawnorton.mixin;

import com.bawnorton.config.Config;
import com.bawnorton.effect.ArmorTrimEffects;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@SuppressWarnings("unused")
@Mixin(EnchantmentHelper.class)
public abstract class EnchantmentHelperMixin {
    @ModifyExpressionValue(method = "generateEnchantments", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/Item;getEnchantability()I"))
    private static int getTrimEnchantability(int orignal, Random random, ItemStack stack, int level, boolean treasureAllowed) {
        if (ArmorTrimEffects.LAPIS.appliesTo(stack)) return Config.getInstance().lapisEnchantability;
        return orignal;
    }
}

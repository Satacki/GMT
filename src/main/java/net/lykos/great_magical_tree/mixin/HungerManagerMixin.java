package net.lykos.great_magical_tree.mixin;

import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import net.lykos.great_magical_tree.effects.VoidPoisonSystem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HungerManager.class)
public class HungerManagerMixin {
    
    @Inject(method = "update", at = @At("HEAD"), cancellable = true)
    private void preventRegenerationWithVoidPoison(PlayerEntity player, CallbackInfo ci) {
        // Check if player has void poison
        if (VoidPoisonSystem.hasVoidPoison(player)) {
            // Cancel the hunger update to prevent regeneration
            ci.cancel();
        }
    }
}






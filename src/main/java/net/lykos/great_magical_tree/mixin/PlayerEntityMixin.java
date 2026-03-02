package net.lykos.great_magical_tree.mixin;

import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.lykos.great_magical_tree.effects.VoidPoisonSystem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {
    
    @Inject(method = "tick()V", at = @At("HEAD"))
    private void preventRegenerationWithVoidPoison(CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        
        // Check if player has void poison
        if (VoidPoisonSystem.hasVoidPoison(player)) {
            // Disable natural regeneration by removing regeneration effect
            if (player.hasStatusEffect(StatusEffects.REGENERATION)) {
                player.removeStatusEffect(StatusEffects.REGENERATION);
            }
        }
    }
}






package net.lykos.great_magical_tree.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.lykos.great_magical_tree.effects.VoidPoisonSystem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    
    @Inject(method = "damage", at = @At("HEAD"))
    private void bypassAbsorptionForVoidPoison(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity entity = (LivingEntity) (Object) this;
        
        // Check if the damage is magic damage and the entity has void poison
        if (source.isOf(DamageTypes.MAGIC) && entity instanceof net.minecraft.entity.player.PlayerEntity player && VoidPoisonSystem.hasVoidPoison(player)) {
            // Temporarily set absorption to 0 to bypass it
            entity.setAbsorptionAmount(0.0f);
        }
    }
}

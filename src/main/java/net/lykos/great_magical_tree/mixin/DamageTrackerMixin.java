package net.lykos.great_magical_tree.mixin;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.lykos.great_magical_tree.GMT;
import net.lykos.great_magical_tree.effects.VoidPoisonSystem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Mixin(net.minecraft.entity.damage.DamageTracker.class)
public class DamageTrackerMixin {
    
    @Shadow
    private net.minecraft.entity.LivingEntity entity;
    
    private static final List<String> DEATH_MESSAGES = Arrays.asList(
        "gmt.death.void.consumed",
        "gmt.death.void.azrael_toy",
        "gmt.death.void.entropy",
        "gmt.death.void.soul_couldnt_support"
    );
    
    @Inject(method = "getDeathMessage", at = @At("HEAD"), cancellable = true)
    private void modifyDeathMessage(CallbackInfoReturnable<Text> cir) {
        // Check if the entity is a player with void poison
        if (entity instanceof PlayerEntity player && VoidPoisonSystem.hasVoidPoison(player)) {
            // Create a new random instance with current time for better randomness
            Random random = new Random(System.currentTimeMillis() + player.getUuid().hashCode());
            String messageKey = DEATH_MESSAGES.get(random.nextInt(DEATH_MESSAGES.size()));
            
            // Log which message was selected for debugging
            GMT.LOGGER.info("Selected void death message for {}: {}", player.getName().getString(), messageKey);
            
            // Return the custom death message and cancel the original method
            cir.setReturnValue(Text.translatable(messageKey, player.getName()));
        }
    }
}

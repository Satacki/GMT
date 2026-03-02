package net.lykos.great_magical_tree.events;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.random.Random;
import net.lykos.great_magical_tree.GMT;
import net.lykos.great_magical_tree.effects.VoidPoisonSystem;

import java.util.Arrays;
import java.util.List;

public class VoidDeathHandler {
    
    private static final List<String> DEATH_MESSAGES = Arrays.asList(
        "gmt.death.void.consumed",
        "gmt.death.void.azrael_toy",
        "gmt.death.void.entropy",
        "gmt.death.void.soul_couldnt_support"
    );
    
    public static void init() {
        ServerLivingEntityEvents.AFTER_DEATH.register(VoidDeathHandler::handleVoidDeath);
    }
    
    private static void handleVoidDeath(LivingEntity entity, DamageSource damageSource) {
        if (!(entity instanceof ServerPlayerEntity player)) {
            return;
        }
        
        // Check if the player had void poison when they died
        if (VoidPoisonSystem.hasVoidPoison(player)) {
            // Select a random death message
            Random random = Random.create();
            String messageKey = DEATH_MESSAGES.get(random.nextInt(DEATH_MESSAGES.size()));
            
            // Send custom death message to all players
            Text deathMessage = Text.translatable(messageKey, player.getName());
            player.getServer().getPlayerManager().broadcast(deathMessage, false);
            
            GMT.LOGGER.info("Player {} died from void poison with message: {}", 
                player.getName().getString(), messageKey);
        }
    }
}

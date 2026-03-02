package net.lykos.great_magical_tree.effects;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.lykos.great_magical_tree.GMT;
import net.lykos.great_magical_tree.dimension.VoidDimension;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class VoidPoisonSystem {
    
    // Track players with void poison and their last damage time
    public static final Map<UUID, Integer> VOID_POISON_PLAYERS = new HashMap<>();
    private static final int DAMAGE_INTERVAL = 100; // 5 seconds in ticks
    
    public static void init() {
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            server.getPlayerManager().getPlayerList().forEach(VoidPoisonSystem::handleVoidPoison);
        });
    }
    
    private static void handleVoidPoison(ServerPlayerEntity player) {
        boolean inVoidDimension = player.getWorld().getRegistryKey().equals(VoidDimension.VOID_WORLD_KEY);
        boolean hasVoidPoison = VOID_POISON_PLAYERS.containsKey(player.getUuid());
        
        if (inVoidDimension) {
            // Player is in void dimension - apply void poison
            if (!hasVoidPoison) {
                VOID_POISON_PLAYERS.put(player.getUuid(), player.age);
                GMT.LOGGER.debug("Applied void poison to player: {}", player.getName().getString());
            }
            
            // Check if it's time to deal damage
            int lastDamageTime = VOID_POISON_PLAYERS.get(player.getUuid());
            if (player.age - lastDamageTime >= DAMAGE_INTERVAL) {
                // Deal 1 true damage that bypasses absorption
                DamageSource voidDamage = player.getDamageSources().magic();
                player.damage(voidDamage, 1.0f);
                
                // Update last damage time
                VOID_POISON_PLAYERS.put(player.getUuid(), player.age);
                
                GMT.LOGGER.debug("Dealt void poison damage to player: {}", player.getName().getString());
            }
        } else {
            // Player is not in void dimension
            if (hasVoidPoison) {
                // Check if 5 seconds (100 ticks) have passed since leaving void
                int lastDamageTime = VOID_POISON_PLAYERS.get(player.getUuid());
                
                // Handle respawn case: if player.age is much smaller than lastDamageTime, they respawned
                if (player.age < lastDamageTime) {
                    // Player respawned, reset the timer
                    VOID_POISON_PLAYERS.put(player.getUuid(), player.age);
                    GMT.LOGGER.debug("Player {} respawned, resetting void poison timer", player.getName().getString());
                } else if (player.age - lastDamageTime >= 100) {
                    // Remove void poison after 5 seconds outside void
                    VOID_POISON_PLAYERS.remove(player.getUuid());
                    GMT.LOGGER.debug("Removed void poison from player: {}", player.getName().getString());
                } else {
                    // Still in countdown, check if it's time to deal damage
                    if (player.age - lastDamageTime >= DAMAGE_INTERVAL) {
                        DamageSource voidDamage = player.getDamageSources().magic();
                        player.damage(voidDamage, 1.0f);
                        VOID_POISON_PLAYERS.put(player.getUuid(), player.age);
                    }
                }
            }
        }
    }
    
    public static boolean hasVoidPoison(PlayerEntity player) {
        return VOID_POISON_PLAYERS.containsKey(player.getUuid());
    }
    
    public static void removeVoidPoison(PlayerEntity player) {
        VOID_POISON_PLAYERS.remove(player.getUuid());
    }
}

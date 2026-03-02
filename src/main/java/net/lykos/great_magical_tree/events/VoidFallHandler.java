package net.lykos.great_magical_tree.events;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import net.lykos.great_magical_tree.GMT;
import net.lykos.great_magical_tree.dimension.VoidDimension;

public class VoidFallHandler {
    
    public static void init() {
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            // Check all players every tick
            server.getPlayerManager().getPlayerList().forEach(VoidFallHandler::checkPlayerVoidFall);
        });
    }
    
    private static void checkPlayerVoidFall(ServerPlayerEntity player) {
        // Don't check if player is already in void dimension
        if (player.getWorld().getRegistryKey() == VoidDimension.VOID_WORLD_KEY) {
            return;
        }
        
        // Check if player is below the void threshold for their dimension
        boolean inVoid = false;
        
        if (player.getWorld().getRegistryKey() == World.OVERWORLD) {
            // Overworld: below Y=-64
            inVoid = player.getY() < -64;
        } else if (player.getWorld().getRegistryKey() == World.NETHER) {
            // Nether: below Y=0 (Nether void)
            inVoid = player.getY() < 0;
        } else if (player.getWorld().getRegistryKey() == World.END) {
            // End: below Y=0 (End void)
            inVoid = player.getY() < 0;
        }
        
        if (inVoid) {
            // Teleport player to void dimension
            ServerWorld voidWorld = player.getServer().getWorld(VoidDimension.VOID_WORLD_KEY);
            if (voidWorld != null) {
                // Cancel fall damage by resetting fall distance before teleporting
                player.fallDistance = 0.0f;
                
                // Teleport to void dimension at Y=-63 (just above the barrier layer)
                player.teleport(voidWorld, 0, -63, 0, 0.0f, 0.0f);
                
                // Ensure fall distance remains 0 after teleportation
                player.fallDistance = 0.0f;
                
                // Send message to player
                player.sendMessage(
                    net.minecraft.text.Text.translatable("gmt.void.fell_into_void"), 
                    false
                );
                
                GMT.LOGGER.info("Player {} fell into void from {} and was teleported to void dimension", 
                    player.getName().getString(), player.getWorld().getRegistryKey().getValue());
            }
        }
    }
}

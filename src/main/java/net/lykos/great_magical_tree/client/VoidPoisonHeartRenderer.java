package net.lykos.great_magical_tree.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.lykos.great_magical_tree.effects.VoidPoisonSystem;

@Environment(EnvType.CLIENT)
public class VoidPoisonHeartRenderer {
    
    // Textures for different heart states
    private static final Identifier VOID_AFFECTED_HEART_FULL = new Identifier("great_magical_tree", "effects/void_affected_heart_full.png");
    private static final Identifier HALF_CORRUPTED_HEART = new Identifier("great_magical_tree", "effects/half_corrupted_void_affected_heart_full.png");
    private static final Identifier FULLY_CORRUPTED_HEART = new Identifier("great_magical_tree", "effects/fully_corrupted_void_affected_heart.png");
    
    public static void renderVoidPoisonHearts(DrawContext context, InGameHud hud, PlayerEntity player, int x, int y) {
        
        if (player == null || !VoidPoisonSystem.hasVoidPoison(player)) {
            return;
        }
        
        int health = (int) Math.ceil(player.getHealth());
        int maxHealth = (int) player.getMaxHealth();
        
        // Calculate how many hearts to render
        int heartsToRender = Math.min((health + 1) / 2, maxHealth / 2);
        
        // Render void poison hearts with custom 18x18 texture
        for (int i = 0; i < heartsToRender; i++) {
            int heartX = x + (i % 10) * 8; // Standard 8 pixel spacing
            int heartY = y + (i / 10) * 8; // Standard 8 pixel vertical spacing
            
            // Determine if this heart is half or full
            int currentHeart = i * 2;
            boolean isHalfHeart = (health == currentHeart + 1);
            boolean isFullHeart = (health >= currentHeart + 2);
            
            if (isFullHeart) {
                // Render full void affected heart (yet to be damaged)
                context.drawTexture(VOID_AFFECTED_HEART_FULL, heartX, heartY, 0, 0, 9, 9, 9, 9);
            } else if (isHalfHeart) {
                // Render half corrupted heart (damaged by void)
                context.drawTexture(HALF_CORRUPTED_HEART, heartX, heartY, 0, 0, 9, 9, 9, 9);
            }
        }
        
        // Render fully corrupted hearts for depleted health
        int remainingHearts = (maxHealth / 2) - heartsToRender;
        for (int i = 0; i < remainingHearts; i++) {
            int heartX = x + ((heartsToRender + i) % 10) * 8;
            int heartY = y + ((heartsToRender + i) / 10) * 8;
            
            // Render fully corrupted heart container (fully depleted)
            context.drawTexture(FULLY_CORRUPTED_HEART, heartX, heartY, 0, 0, 9, 9, 9, 9);
        }
    }
}

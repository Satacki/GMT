package net.lykos.great_magical_tree.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.entity.player.PlayerEntity;
import net.lykos.great_magical_tree.client.VoidPoisonHeartRenderer;
import net.lykos.great_magical_tree.effects.VoidPoisonSystem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(InGameHud.class)
public class InGameHudMixin {
    
    @Inject(method = "renderHealthBar", at = @At("HEAD"), cancellable = true)
    private void renderVoidPoisonHealthBar(DrawContext context, PlayerEntity player, int x, int y, 
                                         int lines, int regeneratingHeartIndex, float maxHealth, 
                                         int lastHealth, int health, int absorption, boolean blinking, 
                                         CallbackInfo ci) {
        
        // Check if player has void poison
        if (VoidPoisonSystem.hasVoidPoison(player)) {
            // Use custom void poison heart renderer
            VoidPoisonHeartRenderer.renderVoidPoisonHearts(context, (InGameHud) (Object) this, player, x, y);
            
            // Cancel the default heart rendering
            ci.cancel();
        }
    }
}


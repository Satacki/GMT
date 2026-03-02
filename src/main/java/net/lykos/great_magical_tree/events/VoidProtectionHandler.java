package net.lykos.great_magical_tree.events;

import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.lykos.great_magical_tree.dimension.VoidDimension;

public class VoidProtectionHandler {
    
    public static void init() {
        // Prevent block breaking in void dimension
        AttackBlockCallback.EVENT.register((player, world, hand, pos, direction) -> {
            if (world.getRegistryKey().equals(VoidDimension.VOID_WORLD_KEY)) {
                if (!player.isCreative()) {
                    player.sendMessage(Text.translatable("gmt.void.cannot_break_blocks"), true);
                    return ActionResult.FAIL;
                }
            }
            return ActionResult.PASS;
        });
        
        // Prevent block placing in void dimension
        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            if (world.getRegistryKey().equals(VoidDimension.VOID_WORLD_KEY)) {
                if (!player.isCreative()) {
                    player.sendMessage(Text.translatable("gmt.void.cannot_place_blocks"), true);
                    return ActionResult.FAIL;
                }
            }
            return ActionResult.PASS;
        });
    }
}

package net.lykos.great_magical_tree.items;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.PhantomEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;

import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RingOfBurningForThoseBastards extends Item {
    public RingOfBurningForThoseBastards(Settings settings) {
        super(settings);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {

        if (world.isClient) return;
        if (!(entity instanceof PlayerEntity player)) return;

        if (player.age % 20 != 0) return;

        ServerWorld serverWorld = (ServerWorld) world;

        // radius: 16 blocks.. BURN
        Box box = player.getBoundingBox().expand(16.0D, 16.0D, 16.0D);

        List<PhantomEntity> phantoms = serverWorld.getEntitiesByType(EntityType.PHANTOM, box, phantom -> true);
        if (phantoms.isEmpty()) return;

        DamageSource magic = player.getDamageSources().magic();

        for (PhantomEntity phantom : phantoms) {
            // set on fire for 4 seconds and apply small damage because fuck those bastards
            phantom.setOnFireFor(4);
            phantom.damage(magic, 2.0F);
        }
    }
    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if (Screen.hasShiftDown()) {
            tooltip.add(Text.translatable("item.gmt.ring_of_radiance.tooltip.shiftkey"));
        } else {
            tooltip.add(Text.translatable("item.gmt.ring_of_radiance.tooltip"));
        }
    }
}

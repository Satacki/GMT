package net.lykos.great_magical_tree.registry;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.lykos.great_magical_tree.GMT;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;

public class ModCreativeTabs {
    public static final ItemGroup MOD_TAB = Registry.register(
            Registries.ITEM_GROUP,
            GMT.id("test_group"),
            FabricItemGroup.builder()
                    .icon(() -> new ItemStack(ModItems.RING_OF_RADIANCE))
                    .displayName(Text.translatable("itemgroup.gmt_tab"))
                    .entries((context, entries) -> {



                        //Baseball uh.
                        entries.add(ModItems.RING_OF_RADIANCE);
                    })
                    .build()
    );

    public static void init() {
    }
}
package net.lykos.great_magical_tree.registry;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.lykos.great_magical_tree.GMT;
import net.lykos.great_magical_tree.items.RingOfBurningForThoseBastards;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Rarity;

public class ModItems {



    public static final Item RING_OF_RADIANCE = registerItem("ring_of_radiance", new RingOfBurningForThoseBastards(new FabricItemSettings().rarity(Rarity.RARE)));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, GMT.id(name), item);
    }

    public static void init() {
        GMT.LOGGER.info("Registering mod items: " + GMT.MOD_ID);
    }
}

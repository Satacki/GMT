package net.lykos.great_magical_tree.dimension;

import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.lykos.great_magical_tree.GMT;

public class VoidDimension {
    public static final RegistryKey<World> VOID_WORLD_KEY = RegistryKey.of(RegistryKeys.WORLD, GMT.id("void"));
    public static final RegistryKey<DimensionType> VOID_DIMENSION_TYPE_KEY = RegistryKey.of(RegistryKeys.DIMENSION_TYPE, GMT.id("void"));

    public static void init() {
        GMT.LOGGER.info("Initializing Void Dimension");
    }
}
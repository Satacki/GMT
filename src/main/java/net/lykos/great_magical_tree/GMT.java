package net.lykos.great_magical_tree;

import net.fabricmc.api.ModInitializer;
import net.lykos.great_magical_tree.commands.VoidCommand;
import net.lykos.great_magical_tree.commands.VoidPoisonCommand;
import net.lykos.great_magical_tree.dimension.VoidChunkGenerator;
import net.lykos.great_magical_tree.effects.VoidPoisonSystem;
import net.lykos.great_magical_tree.events.VoidFallHandler;
import net.lykos.great_magical_tree.events.VoidProtectionHandler;
import net.lykos.great_magical_tree.registry.ModCreativeTabs;
import net.lykos.great_magical_tree.registry.ModItems;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class GMT implements ModInitializer {
    public static final String MOD_ID = "gmt";
    public static  final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    

    public static Identifier id(String location) {
        return new Identifier(MOD_ID, location);
    }

    @Override
    public void onInitialize() {
        LOGGER.info("Welcome to the Tree of heaven.");

        ModCreativeTabs.init();
        ModItems.init();

        VoidCommand.register();
        VoidPoisonCommand.register();
        VoidFallHandler.init();
        VoidProtectionHandler.init();
        VoidPoisonSystem.init();
        
        // Register the chunk generator
        Registry.register(Registries.CHUNK_GENERATOR, id("void_chunk_generator"), VoidChunkGenerator.CODEC);
        
        LOGGER.info("Void Poison system registered and initialized");
    }
}

package net.lykos.great_magical_tree;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class GMT implements ModInitializer {
    public static final String MOD_ID = "gmt";
    public static  final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);


    @Override
    public void onInitialize() {
        LOGGER.info("Welcome to the Tree of heaven.");
    }
}

package net.lykos.great_magical_tree.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.lykos.great_magical_tree.effects.VoidPoisonSystem;

public class VoidPoisonCommand {
    
    public static void register() {
        CommandRegistrationCallback.EVENT.register(VoidPoisonCommand::register);
    }
    
    private static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess, CommandManager.RegistrationEnvironment environment) {
        dispatcher.register(CommandManager.literal("voidpoison")
            .requires(source -> source.hasPermissionLevel(2))
            .then(CommandManager.argument("duration", IntegerArgumentType.integer(1, 1000))
                .executes(VoidPoisonCommand::applyVoidPoison))
            .executes(VoidPoisonCommand::applyVoidPoisonDefault));
    }
    
    private static int applyVoidPoison(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        int duration = IntegerArgumentType.getInteger(context, "duration");
        var source = context.getSource();
        var player = source.getPlayerOrThrow();
        
        // Apply void poison manually for testing
        VoidPoisonSystem.VOID_POISON_PLAYERS.put(player.getUuid(), player.age);
        
        source.sendFeedback(() -> Text.literal("Applied Void Poison for " + duration + " seconds to " + player.getName().getString()), true);
        return 1;
    }
    
    private static int applyVoidPoisonDefault(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        // Apply default void poison
        var source = context.getSource();
        var player = source.getPlayerOrThrow();
        
        // Apply void poison manually for testing
        VoidPoisonSystem.VOID_POISON_PLAYERS.put(player.getUuid(), player.age);
        
        source.sendFeedback(() -> Text.literal("Applied Void Poison to " + player.getName().getString()), true);
        return 1;
    }
}

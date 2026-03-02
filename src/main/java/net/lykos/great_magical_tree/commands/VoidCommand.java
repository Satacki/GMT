package net.lykos.great_magical_tree.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.lykos.great_magical_tree.dimension.VoidDimension;

public class VoidCommand {
    public static void register() {
        CommandRegistrationCallback.EVENT.register(VoidCommand::register);
    }

    private static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess, CommandManager.RegistrationEnvironment environment) {
        dispatcher.register(CommandManager.literal("void")
            .requires(source -> source.hasPermissionLevel(2))
            .executes(VoidCommand::teleportToVoid)
            .then(CommandManager.argument("y", IntegerArgumentType.integer(-64, 320))
                .executes(context -> teleportToVoidAtY(context, IntegerArgumentType.getInteger(context, "y")))));
    }

    private static int teleportToVoid(CommandContext<ServerCommandSource> context) {
        return teleportToVoidAtY(context, -63);
    }

    private static int teleportToVoidAtY(CommandContext<ServerCommandSource> context, int y) {
        ServerCommandSource source = context.getSource();
        ServerPlayerEntity player = source.getPlayer();
        
        if (player == null) {
            source.sendError(Text.literal("This command can only be used by players"));
            return 0;
        }

        ServerWorld voidWorld = source.getServer().getWorld(VoidDimension.VOID_WORLD_KEY);
        if (voidWorld == null) {
            source.sendError(Text.literal("Void dimension not found"));
            return 0;
        }

        int targetY = y;

        // Cancel fall damage by resetting fall distance before teleporting
        player.fallDistance = 0.0f;

        BlockPos pos = new BlockPos(0, targetY, 0);
        player.teleport(voidWorld, pos.getX(), pos.getY(), pos.getZ(), 0.0f, 0.0f);
        
        // Ensure fall distance remains 0 after teleportation
        player.fallDistance = 0.0f;
        
        source.sendFeedback(() -> Text.literal("Teleported to void dimension at Y=" + targetY), false);
        return 1;
    }
}

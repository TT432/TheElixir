package com.nmmoc7.theelixir.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.tree.LiteralCommandNode;
import com.nmmoc7.theelixir.TheElixir;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * @author DustW
 */
@Mod.EventBusSubscriber
public class ModCommands {
    @SubscribeEvent
    public static void onServerStaring(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSource> dispatcher = event.getDispatcher();
        LiteralCommandNode<CommandSource> cmd = dispatcher.register(
                Commands.literal(TheElixir.MOD_ID).then(
                        Commands.literal("human").then(
                                Commands.argument("targets", EntityArgument.entities())
                                        .requires((commandSource) -> commandSource.hasPermissionLevel(2))
                                        .executes(HumanCommand.INSTANCE)
                        )
                )
        );
        dispatcher.register(Commands.literal("elixir").redirect(cmd));
    }
}
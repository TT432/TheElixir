package io.github.tt432.theelixir.common;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import io.github.tt432.theelixir.TheElixir;
import io.github.tt432.theelixir.capability.ElixirData;
import io.github.tt432.theelixir.capability.TheElixirAttachmentData;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

/**
 * @author DustW
 */
@EventBusSubscriber
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TheElixirCommands {
    @SubscribeEvent
    public static void onEvent(RegisterCommandsEvent event) {
        var dispatcher = event.getDispatcher();
        var cmd = dispatcher.register(
                Commands.literal(TheElixir.MOD_ID).then(
                        Commands.literal("human").then(
                                Commands.argument("targets", EntityArgument.entities())
                                        .requires(commandSource -> commandSource.hasPermission(2))
                                        .executes(TheElixirCommands::setHuman)
                        )
                )
        );
        dispatcher.register(Commands.literal("elixir").redirect(cmd));
    }

    private static int setHuman(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        var entity = EntityArgument.getEntities(context, "targets");

        entity.forEach(e -> {
            if (e instanceof Player player) {
                ElixirData cap = e.getData(TheElixirAttachmentData.ELIXIR);

                if (cap.isUsedElixir()) {
                    cap.setUsedElixir(false);
                    player.sendSystemMessage(Component.literal("[The Elixir] 你被强制变回了人类"));
                    context.getSource().sendSuccess(
                            () -> Component.literal("[The Elixir] 已将 ")
                                    .append(e.getDisplayName())
                                    .append(" 变回了人类"), false);
                } else {
                    context.getSource().sendFailure(Component.literal("[The Elixir]")
                            .append(e.getDisplayName())
                            .append(" 已经是人类了"));
                }
            } else {
                context.getSource().sendFailure(Component.literal("[The Elixir] 请对玩家使用"));
            }
        });

        return 0;
    }
}
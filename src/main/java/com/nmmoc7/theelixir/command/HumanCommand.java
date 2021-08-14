package com.nmmoc7.theelixir.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.nmmoc7.theelixir.capability.CapabilityRegistryHandler;
import com.nmmoc7.theelixir.capability.TheElixirCapability;
import net.minecraft.command.CommandSource;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.Collection;

/**
 * @author DustW
 */
public class HumanCommand implements Command<CommandSource> {
    public static final HumanCommand INSTANCE = new HumanCommand();

    @Override
    public int run(CommandContext<CommandSource> context) {
        try {
            Collection<? extends Entity> entity = EntityArgument.getEntities(context, "targets");

            entity.forEach(e -> {
                if (e instanceof ServerPlayerEntity) {
                    TheElixirCapability cap = e.getCapability(CapabilityRegistryHandler.THE_ELIXIR_CAPABILITY).orElse(null);

                    if (cap.isUsedElixir()) {
                        cap.setUsedElixir(false);
                        e.sendMessage(new StringTextComponent("[The Elixir] 你被强制变回了人类"), e.getUniqueID());
                        context.getSource().sendFeedback(new StringTextComponent(String.format("[The Elixir] 已将 %s 变回了人类", e.getDisplayName().getString())), true);
                    }
                    else {
                        context.getSource().sendFeedback(new StringTextComponent(String.format("[The Elixir] %s 已经是人类了", e.getDisplayName().getString())), true);
                    }
                }
                else {
                    context.getSource().sendFeedback(new StringTextComponent("[The Elixir] 请对玩家使用"), true);
                }
            });
        } catch (CommandSyntaxException e) {
            e.printStackTrace();
        }
        return 0;
    }
}

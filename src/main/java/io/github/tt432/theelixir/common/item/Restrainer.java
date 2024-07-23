package io.github.tt432.theelixir.common.item;

import io.github.tt432.theelixir.TheElixir;
import io.github.tt432.theelixir.capability.TheElixirAttachmentData;
import io.github.tt432.theelixir.common.TheElixirDamageTypes;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.SimpleTier;

import java.util.List;

/**
 * @author DustW
 */
public class Restrainer extends SwordItem {
    public Restrainer(Properties properties) {
        super(new SimpleTier(
                BlockTags.INCORRECT_FOR_WOODEN_TOOL, 1000000, 0,
                TheElixir.THE_ULTIMATE_ANSWER_TO_THE_UNIVERSE, 22, Ingredient::of), properties);
    }

    @Override
    public boolean hurtEnemy(ItemStack pStack, LivingEntity target, LivingEntity pAttacker) {
        if (target instanceof ServerPlayer && target.getData(TheElixirAttachmentData.ELIXIR).isUsedElixir()) {
            Level level = pAttacker.level();
            target.hurt(level.damageSources().source(TheElixirDamageTypes.RESTRAINER, pAttacker), 20000);
        }

        return super.hurtEnemy(pStack, target, pAttacker);
    }

    @Override
    public void appendHoverText(ItemStack pStack, TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pTooltipFlag) {
        super.appendHoverText(pStack, pContext, pTooltipComponents, pTooltipFlag);

        pTooltipComponents.add(Component.literal("能杀死拥有不死灵药效果的生物的程度"));
        pTooltipComponents.add(Component.literal(" "));
        pTooltipComponents.add(Component.literal("裁决！不死不灭之人的末日！")
                .withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC, ChatFormatting.BOLD));
    }
}

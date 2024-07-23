package io.github.tt432.theelixir.common.item;

import io.github.tt432.theelixir.capability.TheElixirAttachmentData;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

/**
 * @author DustW
 */
public class HumanGlass extends Item {
    public HumanGlass(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void appendHoverText(ItemStack pStack, TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pTooltipFlag) {
        super.appendHoverText(pStack, pContext, pTooltipComponents, pTooltipFlag);
        pTooltipComponents.add(Component.translatable("tooltip.human_glass.1"));
        pTooltipComponents.add(Component.literal(" "));
        pTooltipComponents.add(Component.translatable("tooltip.human_glass.2"));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if (!pLevel.isClientSide) {
            pPlayer.getData(TheElixirAttachmentData.ELIXIR).setUsedElixir(false);
            pPlayer.sendSystemMessage(Component.literal("成功洗掉了不死的诅咒"));
        }

        return super.use(pLevel, pPlayer, pUsedHand);
    }
}

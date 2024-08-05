package io.github.tt432.theelixir.common.item;

import io.github.tt432.theelixir.capability.ElixirData;
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
public class ElixirItem extends Item {
    public ElixirItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if (!pLevel.isClientSide) {
            ElixirData data = pPlayer.getData(TheElixirAttachmentData.ELIXIR);
            if (data.isUsedElixir()) {
                pPlayer.sendSystemMessage(Component.literal("已经用过了!"));
            } else {
                pPlayer.sendSystemMessage(Component.literal("使用了蓬莱之药，获得了不死的力量。"));

                if (!pPlayer.isCreative()) {
                    pPlayer.getItemInHand(pUsedHand).shrink(1);
                }

                data.setUsedElixir(true);
            }
        }

        return super.use(pLevel, pPlayer, pUsedHand);
    }

    @Override
    public void appendHoverText(ItemStack pStack, TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pTooltipFlag) {
        super.appendHoverText(pStack, pContext, pTooltipComponents, pTooltipFlag);
        pTooltipComponents.add(Component.translatable("tooltip.elixir_item.1"));
        pTooltipComponents.add(Component.literal(" "));
        pTooltipComponents.add(Component.translatable("tooltip.elixir_item.2"));
    }
}

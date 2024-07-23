package io.github.tt432.theelixir.common.item;

import io.github.tt432.theelixir.capability.TheElixirAttachmentData;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
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
public class FlowerItem extends Item {
    public FlowerItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if (!pLevel.isClientSide) {
            var cap = pPlayer.getData(TheElixirAttachmentData.FLOWER);

            if (!pPlayer.isCrouching()) {
                cap.setHasFlower(!cap.isHasFlower());
                cap.sync((ServerPlayer) pPlayer);
                pPlayer.getCooldowns().addCooldown(pPlayer.getItemInHand(pUsedHand).getItem(), 20);
            } else {
                cap.setFlowerSpeed(cap.getFlowerSpeed() < 60 ? cap.getFlowerSpeed() + 6 : 0);
                cap.sync((ServerPlayer) pPlayer);
                pPlayer.displayClientMessage(Component.literal("当前速度： " + cap.getFlowerSpeed()), false);
            }
        }

        return super.use(pLevel, pPlayer, pUsedHand);
    }

    @Override
    public void appendHoverText(ItemStack pStack, TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pTooltipFlag) {
        super.appendHoverText(pStack, pContext, pTooltipComponents, pTooltipFlag);
        pTooltipComponents.add(Component.translatable("tooltip.flower_item.1"));
        pTooltipComponents.add(Component.translatable("tooltip.flower_item.2"));
    }
}

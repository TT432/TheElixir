package io.github.tt432.theelixir.common.item;

import io.github.tt432.theelixir.common.entity.GrenadeEntity;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
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
public class RefreshmentGrenade extends Item {
    public RefreshmentGrenade(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack pStack, TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pTooltipFlag) {
        super.appendHoverText(pStack, pContext, pTooltipComponents, pTooltipFlag);
        pTooltipComponents.add(Component.literal("右键以丢出一个茶点手雷，恢复范围内玩家饱食度"));
        pTooltipComponents.add(Component.literal(" "));
        pTooltipComponents.add(Component.literal("§7§o§l保护好你的曲奇(cookie), 不要被其他人偷走."));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pUsedHand);
        if (!pLevel.isClientSide) {
            pLevel.playSound(null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(),
                    SoundEvents.SPLASH_POTION_THROW, SoundSource.PLAYERS,
                    0.5F, 0.4F / (pLevel.random.nextFloat() * 0.4F + 0.8F));

            GrenadeEntity entity = new GrenadeEntity(pPlayer, pLevel);
            entity.shootFromRotation(pPlayer, pPlayer.getXRot(), pPlayer.getYRot(),
                    -20.0F, 0.5F, 1.0F);
            pLevel.addFreshEntity(entity);
        }

        pPlayer.awardStat(Stats.ITEM_USED.get(this));

        if (!pPlayer.isCreative()) {
            itemstack.shrink(1);
        }

        return super.use(pLevel, pPlayer, pUsedHand);
    }
}

package io.github.tt432.theelixir.common.item;

import io.github.tt432.theelixir.capability.TheElixirAttachmentData;
import io.github.tt432.theelixir.utils.EntityUtils;
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

import javax.annotation.Nullable;
import java.util.List;

/**
 * @author DustW
 */
public class DirtBallItem extends Item {
    public DirtBallItem(Properties pProperties) {
        super(pProperties);
    }

    public float getDamageAmount(@Nullable Player player) {
        if (player != null) {
            return player.getData(TheElixirAttachmentData.FLOWER).isHasFlower() ? 8 : 4;
        }

        return 4;
    }

    @Override
    public void appendHoverText(ItemStack pStack, TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pTooltipFlag) {
        super.appendHoverText(pStack, pContext, pTooltipComponents, pTooltipFlag);
        pTooltipComponents.add(Component.translatable("tooltip.dirt_ball_item.1"));
        pTooltipComponents.add(Component.translatable("tooltip.dirt_ball_item.3"));
        pTooltipComponents.add(Component.literal(" "));
        pTooltipComponents.add(Component.translatable("tooltip.dirt_ball_item.2"));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if (!pLevel.isClientSide) {
            EntityUtils.defaultDirtBallEntityShoot(pLevel, pPlayer);

            if (!pPlayer.isCreative()) {
                pPlayer.getItemInHand(pUsedHand).shrink(1);
            }
        } else {
            pLevel.playSound(null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(),
                    SoundEvents.SNOWBALL_THROW, SoundSource.NEUTRAL,
                    0.5F, 0.4F / (pLevel.random.nextFloat() * 0.4F + 0.8F));
        }

        pPlayer.awardStat(Stats.ITEM_USED.get(this));
        return super.use(pLevel, pPlayer, pUsedHand);
    }
}

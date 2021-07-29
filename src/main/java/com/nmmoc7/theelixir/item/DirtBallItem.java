package com.nmmoc7.theelixir.item;

import com.nmmoc7.theelixir.capability.CapabilityRegistryHandler;
import com.nmmoc7.theelixir.capability.TheElixirCapability;
import com.nmmoc7.theelixir.utils.EntityUtils;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

/**
 * @author DustW
 */
public class DirtBallItem extends ModItemBase {
    public DirtBallItem() {
        super("dirt_ball_item", 64);
    }

    public float getDamageCount(@Nullable PlayerEntity player) {
        if (player != null) {
            TheElixirCapability cap = player.getCapability(CapabilityRegistryHandler.THE_ELIXIR_CAPABILITY).orElse(null);
            return cap.isHasFlower() ? 8 : 4;
        }
        return 4;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(new TranslationTextComponent("tooltip.dirt_ball_item.1"));
        tooltip.add(new TranslationTextComponent("tooltip.dirt_ball_item.3"));
        tooltip.add(new StringTextComponent(" "));
        tooltip.add(new TranslationTextComponent("tooltip.dirt_ball_item.2"));
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        if (!worldIn.isRemote) {
            if (playerIn.isSneaking()) {
                playerIn.getHeldItem(handIn).getOrCreateTag().putBoolean("mode",
                        !playerIn.getHeldItem(handIn).getOrCreateTag().getBoolean("mode"));
            }
            else {
                EntityUtils.defaultDirtBallEntityShoot(worldIn, playerIn);

                if (!playerIn.isCreative()) {
                    playerIn.getHeldItem(handIn).shrink(1);
                }
            }
        }
        else {
            worldIn.playSound(null, playerIn.getPosX(), playerIn.getPosY(), playerIn.getPosZ(), SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
        }
        playerIn.addStat(Stats.ITEM_USED.get(this));
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if (entityIn instanceof PlayerEntity && stack.getOrCreateTag().getBoolean("mode")) {
            EntityUtils.randomDirtBallEntityShoot(worldIn, (PlayerEntity) entityIn);

            if (!((PlayerEntity) entityIn).isCreative()) {
                stack.shrink(1);
            }
        }
        super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
    }
}

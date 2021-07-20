package com.nmmoc7.theelixir.item;

import com.nmmoc7.theelixir.capability.CapabilityRegistryHandler;
import com.nmmoc7.theelixir.capability.TheElixirCapability;
import com.nmmoc7.theelixir.utils.EntityUtils;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
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
        tooltip.add(new StringTextComponent(" "));
        tooltip.add(new TranslationTextComponent("tooltip.dirt_ball_item.2"));
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        if (!worldIn.isRemote) {
            EntityUtils.defaultDirtBallEntityShoot(worldIn, playerIn);

            if (!playerIn.isCreative()) {
                playerIn.getHeldItem(handIn).shrink(1);
            }

            playerIn.getCooldownTracker().setCooldown(playerIn.getHeldItem(handIn).getItem(), 20);
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }
}

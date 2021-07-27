package com.nmmoc7.theelixir.item;

import com.nmmoc7.theelixir.TheElixir;
import com.nmmoc7.theelixir.capability.CapabilityRegistryHandler;
import com.nmmoc7.theelixir.capability.TheElixirCapability;
import com.nmmoc7.theelixir.support.curios.CuriosCapProvider;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nullable;
import java.util.List;

/**
 * @author DustW
 */
public class FlowerItem extends ModItemBase {
    public FlowerItem() {
        super("flower_item", 1);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        if (!worldIn.isRemote) {
            TheElixirCapability cap = playerIn.getCapability(CapabilityRegistryHandler.THE_ELIXIR_CAPABILITY).orElse(null);

            if (!playerIn.isSneaking()) {
                if (!cap.curioFlower) {
                    cap.setHasFlower(!cap.isHasFlower());
                    playerIn.getCooldownTracker().setCooldown(playerIn.getHeldItem(handIn).getItem(), 20);
                }
            } else {
                cap.setFlowerSpeed(cap.getFlowerSpeed() < 60 ? cap.getFlowerSpeed() + 6 : 0);
                playerIn.sendStatusMessage(new StringTextComponent("当前速度： " + cap.getFlowerSpeed()), false);
            }
        }

        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(new TranslationTextComponent("tooltip.flower_item.1"));
        tooltip.add(new TranslationTextComponent("tooltip.flower_item.2"));
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
        if (TheElixir.curiosLoaded) {
            return new CuriosCapProvider();
        }
        return super.initCapabilities(stack, nbt);
    }
}

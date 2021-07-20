package com.nmmoc7.theelixir.item;

import com.nmmoc7.theelixir.capability.CapabilityRegistryHandler;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Util;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

/**
 * @author DustW
 */
public class ElixirItem extends ModItemBase {
    public ElixirItem() {
        super("elixir_item", 1);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        if (!worldIn.isRemote) {
            playerIn.getCapability(CapabilityRegistryHandler.THE_ELIXIR_CAPABILITY).ifPresent((theCap) -> {
                if (theCap.isUsedElixir()) {
                    playerIn.sendMessage(new StringTextComponent("已经用过了!"), Util.DUMMY_UUID);
                }
                else {
                    playerIn.sendMessage(new StringTextComponent("使用了蓬莱之药，获得了不死的力量。"), Util.DUMMY_UUID);

                    if (!playerIn.isCreative()) {
                        playerIn.getHeldItem(handIn).shrink(1);
                    }

                    theCap.setUsedElixir(true);
                }
            });
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(new TranslationTextComponent("tooltip.elixir_item.1"));
        tooltip.add(new StringTextComponent(" "));
        tooltip.add(new TranslationTextComponent("tooltip.elixir_item.2"));
    }
}

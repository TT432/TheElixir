package com.nmmoc7.theelixir.item;

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

import javax.annotation.Nullable;
import java.util.List;

/**
 * @author DustW
 */
public class MangoBox extends ModItemBase {
    public MangoBox() {
        super("mango_box", 1);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(new TranslationTextComponent("tooltip.mango_box.1"));
        tooltip.add(new StringTextComponent("存储的物品: " + (stack.getOrCreateTag().getBoolean("has") ? ItemStack.read(stack.getOrCreateTag().getCompound("item")).getDisplayName().getString() : "无")));
        tooltip.add(new StringTextComponent(" "));
        tooltip.add(new TranslationTextComponent("tooltip.mango_box.2"));
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        if (!worldIn.isRemote) {
            if (handIn == Hand.MAIN_HAND) {
                ItemStack mango = playerIn.getHeldItemMainhand();
                CompoundNBT mangoNbt = mango.getOrCreateTag();

                if (mangoNbt.getBoolean("has")) {
                    playerIn.addItemStackToInventory(ItemStack.read(mangoNbt.getCompound("item")));
                    mangoNbt.putBoolean("has", false);
                }
                else {
                    ItemStack offItem = playerIn.getHeldItem(Hand.OFF_HAND);

                    if (!offItem.isEmpty()) {
                        mangoNbt.putBoolean("has", true);
                        CompoundNBT itemNbt = new CompoundNBT();
                        offItem.write(itemNbt);
                        mangoNbt.put("item", itemNbt);
                        playerIn.setHeldItem(Hand.OFF_HAND, ItemStack.EMPTY);
                    }
                }
            }
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }
}

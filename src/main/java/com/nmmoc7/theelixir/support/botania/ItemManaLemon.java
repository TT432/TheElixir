package com.nmmoc7.theelixir.support.botania;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import vazkii.botania.api.mana.ICreativeManaProvider;
import vazkii.botania.api.mana.IManaItem;
import vazkii.botania.api.mana.IManaTooltipDisplay;

import java.util.List;

public class ItemManaLemon extends Item implements IManaItem, ICreativeManaProvider, IManaTooltipDisplay {
    public static final int MAX_MANA = 10000;

    public ItemManaLemon(Properties properties) {
        super(properties);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void addInformation(ItemStack stack, World world, List<ITextComponent> stacks, ITooltipFlag flags) {
        if (isCreative(stack)) {
            stacks.add(new TranslationTextComponent("botaniamisc.creative").mergeStyle(TextFormatting.GRAY));
        }
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        return !isCreative(stack);
    }

    @Override
    public boolean isCreative(ItemStack stack) {
        return stack.getOrCreateTag().getBoolean("creative");
    }

    @Override
    public int getMana(ItemStack stack) {
        return stack.getOrCreateTag().getInt("mana");
    }

    @Override
    public int getMaxMana(ItemStack stack) {
        return MAX_MANA;
    }

    @Override
    public void addMana(ItemStack stack, int mana) {
        if (!isCreative(stack)) {
            int beAddMana = stack.getOrCreateTag().getInt("mana") + mana;
            stack.getOrCreateTag().putInt("mana", Math.max(0, Math.min(beAddMana, MAX_MANA)));
        }
    }

    @Override
    public boolean canReceiveManaFromPool(ItemStack stack, TileEntity pool) {
        return !isCreative(stack);
    }

    @Override
    public boolean canReceiveManaFromItem(ItemStack stack, ItemStack otherStack) {
        return !isCreative(stack);
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        return 1.0 - getManaFractionForDisplay(stack);
    }

    @Override
    public boolean canExportManaToPool(ItemStack stack, TileEntity pool) {
        return true;
    }

    @Override
    public boolean canExportManaToItem(ItemStack stack, ItemStack otherStack) {
        return true;
    }

    @Override
    public boolean isNoExport(ItemStack stack) {
        return false;
    }

    @Override
    public float getManaFractionForDisplay(ItemStack stack) {
        return (float) getMana(stack) / (float) getMaxMana(stack);
    }
}

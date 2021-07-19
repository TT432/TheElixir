package com.nmmoc7.theelixir.item;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Food;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

/**
 * @author DustW
 */
public class MangoFood extends ModItemBase {
    public static final Food MANGO_FOOD = new Food.Builder()
            .effect(() -> new EffectInstance(Effects.NAUSEA, 10 * 10, 0), 1F)
            .hunger(10).build();

    public MangoFood() {
        super("mango_food", 64);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(new StringTextComponent("食用会得到10s反胃"));
        tooltip.add(new StringTextComponent(" "));
        tooltip.add(new StringTextComponent("§7§o§l哎呀妈呀真好吃，呕，真好吃"));
    }

    @Override
    public boolean isFood() {
        return true;
    }

    @Nullable
    @Override
    public Food getFood() {
        return MANGO_FOOD;
    }
}

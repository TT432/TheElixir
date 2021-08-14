package com.nmmoc7.theelixir.item;

import com.nmmoc7.theelixir.TheElixir;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.PotionItem;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.NonNullList;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author DustW
 */
public class SunroVientianeParaquatItem extends PotionItem {
    public SunroVientianeParaquatItem() {
        super(new Properties().group(ModItemGroup.INSTANCE));
        setRegistryName(new ResourceLocation(TheElixir.MOD_ID, "sunro_vientiane_paraquat"));
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(new StringTextComponent("右键生物以装满瓶子"));
        tooltip.add(new StringTextComponent("当前模式: " + getModeName(stack)));
    }

    public boolean isAll(ItemStack potion) {
        return getMode(potion) == EffectType.values().length;
    }

    public String getModeName(ItemStack potion) {
        return isAll(potion) ? "全部" : EffectType.values()[getMode(potion)].name();
    }

    public int getMode(ItemStack potion) {
        return potion.getOrCreateTag().getByte("modeP");
    }

    public void setMode(ItemStack potion, byte mode) {
        potion.getOrCreateTag().putByte("modeP", mode);
    }

    public void switchMode(ItemStack potion) {
        setMode(potion, (byte) ((getMode(potion) + 1) % EffectType.values().length + 1));
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        if (!context.getWorld().isRemote) {
            switchMode(context.getItem());

            Set<Map.Entry<RegistryKey<Effect>, Effect>> potions = Registry.EFFECTS.getEntries();
            ArrayList<EffectInstance> effects = new ArrayList<>();

            for (Map.Entry<RegistryKey<Effect>, Effect> entry : potions) {
                if (isAll(context.getItem()) || entry.getValue().getEffectType() == EffectType.values()[getMode(context.getItem())]) {
                    effects.add(new EffectInstance(entry.getValue(), 100, 100));
                }
            }

            PotionUtils.appendEffects(context.getItem(), effects);
        }

        return super.onItemUse(context);
    }

    @Override
    public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
        if (this.isInGroup(group)) {
            items.add(new ItemStack(this));
        }
    }
}

package com.nmmoc7.theelixir.item;

import com.nmmoc7.theelixir.TheElixir;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

/**
 * @author DustW
 */
public class ModItemBase extends Item {
    public ModItemBase(String regName, int maxStack) {
        super(new Properties().maxStackSize(maxStack).group(ModItemGroup.INSTANCE));

        this.setRegistryName(new ResourceLocation(TheElixir.MOD_ID, regName));
    }
}

package com.nmmoc7.theelixir.item;

import com.nmmoc7.theelixir.RegistryHandler;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

/**
 * @author DustW
 */
public class ModItemGroup extends ItemGroup {
    public static final ModItemGroup INSTANCE = new ModItemGroup();

    public ModItemGroup() {
        super("the_elixir_item_tab");
    }

    @Override
    public ItemStack createIcon() {
        return new ItemStack(RegistryHandler.ELIXIR_ITEM);
    }
}

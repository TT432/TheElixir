package com.nmmoc7.theelixir.support.botania;

import com.nmmoc7.theelixir.item.ModItemGroup;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;

public class BotaniaSupportRegistryHandler {
    public BotaniaSupportRegistryHandler() {
        ITEM_MANA_LEMON = new ItemManaLemon(unstackable());
    }

    public void register(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(
                ITEM_MANA_LEMON.setRegistryName("mana_lemon")
        );
    }

    public static Item ITEM_MANA_LEMON;

    public static Item.Properties defaultBuilder() {
        return new Item.Properties().group(ModItemGroup.INSTANCE);
    }

    private static Item.Properties unstackable() {
        return defaultBuilder().maxStackSize(1);
    }
}

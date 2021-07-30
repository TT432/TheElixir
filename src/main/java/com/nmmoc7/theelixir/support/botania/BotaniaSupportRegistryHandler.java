package com.nmmoc7.theelixir.support.botania;

import com.nmmoc7.theelixir.item.ModItemGroup;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.RegistryEvent;

import java.util.List;

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

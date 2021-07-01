package org.teacon.theelixir;

import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.teacon.theelixir.item.ElixirItem;
import org.teacon.theelixir.item.Restrainer;
import org.teacon.theelixir.item.TextItem;

/**
 * @author DustW
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class RegistryHandler {
    public static final ElixirItem ELIXIR_ITEM = new ElixirItem();
    public static final TextItem TEXT_ITEM = new TextItem();
    public static final Restrainer RESTRAINER = new Restrainer();

    @SubscribeEvent
    public static void onItemRegistry(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(ELIXIR_ITEM);
        event.getRegistry().register(TEXT_ITEM);
        event.getRegistry().register(RESTRAINER);
    }
}

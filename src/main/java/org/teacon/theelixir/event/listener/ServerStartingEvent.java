package org.teacon.theelixir.event.listener;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;

/**
 * @author DustW
 */
@Mod.EventBusSubscriber
public class ServerStartingEvent {
    @SubscribeEvent
    public static void onServerSetup(FMLServerStartedEvent event) {
        ModServerTickEvent.players = event.getServer().getPlayerList().getPlayers();
    }
}

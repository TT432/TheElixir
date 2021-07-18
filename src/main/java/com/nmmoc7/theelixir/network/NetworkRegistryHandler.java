package com.nmmoc7.theelixir.network;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

/**
 * @author DustW
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class NetworkRegistryHandler {
    @SubscribeEvent
    public static void networkRegistryEvent(FMLCommonSetupEvent event) {
        ModNetworkManager.registerPackets();
    }
}

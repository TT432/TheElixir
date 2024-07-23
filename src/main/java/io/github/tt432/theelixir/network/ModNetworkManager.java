package io.github.tt432.theelixir.network;

import io.github.tt432.theelixir.TheElixir;
import net.minecraft.client.Minecraft;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

/**
 * @author DustW
 */
@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class ModNetworkManager {
    @SubscribeEvent
    public static void onEvent(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar(TheElixir.MOD_ID);
        registrar.playToClient(TheElixirDataSyncPacketS2C.TYPE, TheElixirDataSyncPacketS2C.STREAM_CODEC, (packet, context) -> {
            Minecraft.getInstance().player.setData(packet.attachmentType(), packet.data());
        });
    }
}
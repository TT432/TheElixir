package io.github.tt432.theelixir.network;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.network.handling.IPayloadHandler;

/**
 * @author TT432
 */
public class ClientNetworkHandlers {
    public static final IPayloadHandler<TheElixirDataSyncPacketS2C<?>> dataSyncPacketHandler = (packet, context) -> {
        Entity entity = Minecraft.getInstance().level.getEntity(packet.entityId());
        if (entity != null)
            entity.setData(() -> cast(packet.attachmentType()), packet.data());
    };

    @SuppressWarnings("unchecked")
    private static <T> T cast(Object o) {
        return (T) o;
    }
}

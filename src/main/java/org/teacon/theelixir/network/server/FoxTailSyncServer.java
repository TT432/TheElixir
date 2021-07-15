package org.teacon.theelixir.network.server;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;
import org.teacon.theelixir.capability.CapabilityRegistryHandler;

import java.util.UUID;
import java.util.function.Supplier;

/**
 * @author DustW
 */
public class FoxTailSyncServer implements IServerMessage {
    boolean hasFoxTail;
    UUID ownerUUID;

    public FoxTailSyncServer(UUID ownerUUID, boolean hasFoxTail) {
        this.hasFoxTail = hasFoxTail;
        this.ownerUUID = ownerUUID;
    }

    public static void encode(FoxTailSyncServer packet, PacketBuffer pb) {
        pb.writeUniqueId(packet.ownerUUID);
        pb.writeBoolean(packet.hasFoxTail);
    }

    public static FoxTailSyncServer decode(PacketBuffer pb) {
        return new FoxTailSyncServer(pb.readUniqueId(), pb.readBoolean());
    }

    public static void handle(FoxTailSyncServer packet, Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection().equals(NetworkDirection.PLAY_TO_CLIENT)) {
            ctx.get().enqueueWork(() -> {
                PlayerEntity player = Minecraft.getInstance().world.getPlayerByUuid(packet.ownerUUID);

                if (player != null) {
                    player.getCapability(CapabilityRegistryHandler.THE_ELIXIR_CAPABILITY).ifPresent(theCap ->
                            theCap.setHasFoxTail(packet.hasFoxTail)
                    );
                }
            });
            ctx.get().setPacketHandled(true);
        }
    }
}

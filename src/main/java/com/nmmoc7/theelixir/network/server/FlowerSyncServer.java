package com.nmmoc7.theelixir.network.server;

import com.nmmoc7.theelixir.capability.CapabilityRegistryHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

/**
 * @author DustW
 */
public class FlowerSyncServer implements IServerMessage {
    boolean hasFlower;
    UUID ownerUUID;

    public FlowerSyncServer(UUID ownerUUID, boolean hasFlower) {
        this.hasFlower = hasFlower;
        this.ownerUUID = ownerUUID;
    }

    public static void encode(FlowerSyncServer packet, PacketBuffer pb) {
        pb.writeUniqueId(packet.ownerUUID);
        pb.writeBoolean(packet.hasFlower);
    }

    public static FlowerSyncServer decode(PacketBuffer pb) {
        return new FlowerSyncServer(pb.readUniqueId(), pb.readBoolean());
    }

    public static void handle(FlowerSyncServer packet, Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection().equals(NetworkDirection.PLAY_TO_CLIENT)) {
            ctx.get().enqueueWork(() -> {
                PlayerEntity player = Minecraft.getInstance().world.getPlayerByUuid(packet.ownerUUID);

                if (player != null) {
                    player.getCapability(CapabilityRegistryHandler.THE_ELIXIR_CAPABILITY).ifPresent(theCap ->
                            theCap.setHasFlower(packet.hasFlower)
                    );
                }
            });
            ctx.get().setPacketHandled(true);
        }
    }
}

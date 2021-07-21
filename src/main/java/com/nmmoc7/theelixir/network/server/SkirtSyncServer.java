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
public class SkirtSyncServer implements IServerMessage {
    boolean chestSkirt;
    boolean normalSkirt;
    UUID ownerUUID;

    public SkirtSyncServer(UUID ownerUUID, boolean chestSkirt, boolean normalSkirt) {
        this.ownerUUID = ownerUUID;
        this.chestSkirt = chestSkirt;
        this.normalSkirt = normalSkirt;
    }

    public static void encode(SkirtSyncServer packet, PacketBuffer pb) {
        pb.writeUniqueId(packet.ownerUUID);
        pb.writeBoolean(packet.chestSkirt);
        pb.writeBoolean(packet.normalSkirt);
    }

    public static SkirtSyncServer decode(PacketBuffer pb) {
        return new SkirtSyncServer(pb.readUniqueId(), pb.readBoolean(), pb.readBoolean());
    }

    public static void handle(SkirtSyncServer packet, Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection().equals(NetworkDirection.PLAY_TO_CLIENT)) {
            ctx.get().enqueueWork(() -> {
                PlayerEntity player = Minecraft.getInstance().world.getPlayerByUuid(packet.ownerUUID);

                if (player != null) {
                    player.getCapability(CapabilityRegistryHandler.THE_ELIXIR_CAPABILITY).ifPresent(theCap -> {
                        if (!packet.normalSkirt && !packet.chestSkirt) {
                            theCap.closeChestSkirt();
                        }
                        else {
                            theCap.setChestSkirt(packet.chestSkirt);
                        }
                    });
                }
            });
            ctx.get().setPacketHandled(true);
        }
    }
}

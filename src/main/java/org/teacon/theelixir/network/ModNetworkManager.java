package org.teacon.theelixir.network;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import org.teacon.theelixir.TheElixir;
import org.teacon.theelixir.network.client.IClientMessage;
import org.teacon.theelixir.network.server.FlowerSyncServer;
import org.teacon.theelixir.network.server.IServerMessage;

/**
 * @author DustW
 */
public class ModNetworkManager {
    private static final String PROTOCOL_VERSION = "1.0";
    private static int id = 0;

    public static SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel (
            new ResourceLocation(TheElixir.MOD_ID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    /** 不允许产生该类的实例 */
    private ModNetworkManager() {
        throw new UnsupportedOperationException("No instance");
    }

    public static <T extends IServerMessage> void serverSendToPlayer(T packet, ServerPlayerEntity player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), packet);
    }

    public static <T extends IClientMessage> void clientSendToServer(T packet) {
        INSTANCE.sendToServer(packet);
    }

    public static void registerPackets() {
        INSTANCE.registerMessage(id++,
                FlowerSyncServer.class,
                FlowerSyncServer::encode,
                FlowerSyncServer::decode,
                FlowerSyncServer::handle);
    }
}
package com.nmmoc7.theelixir.event.listener;

import com.nmmoc7.theelixir.capability.CapabilityRegistryHandler;
import com.nmmoc7.theelixir.capability.TheElixirCapability;
import com.nmmoc7.theelixir.network.ModNetworkManager;
import com.nmmoc7.theelixir.network.server.FlowerSyncServer;
import net.minecraft.util.math.ChunkPos;
import net.minecraftforge.event.world.ChunkWatchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * @author DustW
 */
@Mod.EventBusSubscriber
public class ChunkUpdateEvent {
    @SubscribeEvent
    public static void onChunkUpdate(ChunkWatchEvent.Watch event) {
        event.getWorld().getPlayers().stream().filter(player ->
                new ChunkPos(player.chunkCoordX, player.chunkCoordZ).equals(event.getPos())
        ).forEach(player -> {
            TheElixirCapability cap = player.getCapability(CapabilityRegistryHandler.THE_ELIXIR_CAPABILITY).orElse(null);
            ModNetworkManager.serverSendToPlayer(new FlowerSyncServer(player.getUniqueID(), cap.isHasFlower(), cap.getFlowerSpeed()), event.getPlayer());
        });
    }
}

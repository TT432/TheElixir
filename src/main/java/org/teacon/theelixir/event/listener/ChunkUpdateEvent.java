package org.teacon.theelixir.event.listener;

import net.minecraft.util.math.ChunkPos;
import net.minecraftforge.event.world.ChunkWatchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.teacon.theelixir.capability.CapabilityRegistryHandler;
import org.teacon.theelixir.capability.TheElixirCapability;
import org.teacon.theelixir.network.ModNetworkManager;
import org.teacon.theelixir.network.server.FlowerSyncServer;

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
            ModNetworkManager.serverSendToPlayer(new FlowerSyncServer(player.getUniqueID(), cap.isHasFlower()), event.getPlayer());
        });
    }
}

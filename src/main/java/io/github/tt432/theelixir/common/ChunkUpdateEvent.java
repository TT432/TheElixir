package io.github.tt432.theelixir.common;

import io.github.tt432.theelixir.capability.TheElixirAttachmentData;
import net.minecraft.world.level.ChunkPos;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.ChunkWatchEvent;

/**
 * @author DustW
 */
@EventBusSubscriber
public class ChunkUpdateEvent {
    @SubscribeEvent
    public static void onChunkUpdate(ChunkWatchEvent.Watch event) {
        event.getLevel().players().stream().filter(player ->
                new ChunkPos(player.chunkPosition().x, player.chunkPosition().z).equals(event.getPos())
        ).forEach(player -> {
            var cap = player.getData(TheElixirAttachmentData.FLOWER);
            cap.sync(player);
        });
    }
}

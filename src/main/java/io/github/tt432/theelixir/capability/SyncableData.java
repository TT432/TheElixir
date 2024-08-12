package io.github.tt432.theelixir.capability;

import io.github.tt432.theelixir.network.TheElixirDataSyncPacketS2C;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.PacketDistributor;

/**
 * @author TT432
 */
public interface SyncableData {
    default void sync(ServerPlayer player) {
        PacketDistributor.sendToPlayer(player, new TheElixirDataSyncPacketS2C<>(player.getId(), this));
    }
}

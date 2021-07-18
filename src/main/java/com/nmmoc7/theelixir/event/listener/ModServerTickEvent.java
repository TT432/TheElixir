package com.nmmoc7.theelixir.event.listener;

import com.nmmoc7.theelixir.capability.CapabilityRegistryHandler;
import com.nmmoc7.theelixir.capability.TheElixirCapability;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

/**
 * @author DustW
 */
@Mod.EventBusSubscriber
public class ModServerTickEvent {
    public static List<ServerPlayerEntity> players;

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        players.forEach(player -> player.getCapability(CapabilityRegistryHandler.THE_ELIXIR_CAPABILITY).ifPresent(TheElixirCapability::tick));
    }
}

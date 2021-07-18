package com.nmmoc7.theelixir.event.listener;

import com.nmmoc7.theelixir.capability.CapabilityRegistryHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.entity.living.PotionEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * @author DustW
 */
@Mod.EventBusSubscriber
public class ElixirPotionApplicableEvent {
    @SubscribeEvent
    public static void onPotionApply(PotionEvent.PotionApplicableEvent event) {
        if (event.getEntityLiving() instanceof PlayerEntity) {
            event.getEntityLiving().getCapability(CapabilityRegistryHandler.THE_ELIXIR_CAPABILITY).ifPresent(theCap -> {
                if (theCap.isUsedElixir()) {
                    event.setResult(Event.Result.DENY);
                }
            });
        }
    }
}

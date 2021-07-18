package com.nmmoc7.theelixir.event.listener;

import com.nmmoc7.theelixir.RegistryHandler;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.world.Explosion;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * @author DustW
 */
@Mod.EventBusSubscriber
public class OnItemPickup {
    @SubscribeEvent
    public static void onItemPickup(PlayerEvent.ItemPickupEvent event) {
        ItemEntity entity = event.getOriginalEntity();
        if (event.getStack().getItem() == RegistryHandler.SOUL_BOMB) {
            entity.world.createExplosion(null, DamageSource.MAGIC, null,
                    entity.getPosX(), entity.getPosY(), entity.getPosZ(),
                    1, false, Explosion.Mode.NONE);
        }
    }
}

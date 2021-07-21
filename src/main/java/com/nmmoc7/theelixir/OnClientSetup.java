package com.nmmoc7.theelixir;

import com.nmmoc7.theelixir.capability.CapabilityRegistryHandler;
import com.nmmoc7.theelixir.capability.TheElixirCapability;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

/**
 * @author DustW
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class OnClientSetup {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            registerProperties();
        });
    }

    static void registerProperties() {
        ItemModelsProperties.registerProperty(RegistryHandler.MANGO_BOX, new ResourceLocation("empty"), (item, name, entity) -> {
            return item.getOrCreateTag().getBoolean("has") ? 1 : 0;
        });

        ItemModelsProperties.registerProperty(RegistryHandler.DUSTY_SWORD, new ResourceLocation("flower"), (item, name, entity) -> {
            if (entity instanceof PlayerEntity) {
                TheElixirCapability cap = entity.getCapability(CapabilityRegistryHandler.THE_ELIXIR_CAPABILITY).orElse(null);
                return cap.isHasFlower() ? 1 : 0;
            }

            return 0;
        });
    }
}

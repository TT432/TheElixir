package com.nmmoc7.theelixir.particle;

import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * @author DustW
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ParticleClientRegistryHandler {
    @SubscribeEvent
    public static void onParticleClientRegister(ParticleFactoryRegisterEvent event) {
        Minecraft.getInstance().particles.registerFactory(ParticleRegistryHandler.REFRESHMENT, RefreshmentParticle.Factory::new);
        Minecraft.getInstance().particles.registerFactory(ParticleRegistryHandler.BLOOD_MOUSE, BloodMouseParticle.Factory::new);
    }
}

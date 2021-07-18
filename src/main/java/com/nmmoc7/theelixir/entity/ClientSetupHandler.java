package com.nmmoc7.theelixir.entity;

import com.nmmoc7.theelixir.RegistryHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

/**
 * @author DustW
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientSetupHandler {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        Minecraft mc = Minecraft.getInstance();
        mc.getRenderManager().register(RegistryHandler.GRENADE_ENTITY, new SpriteRenderer<>(mc.getRenderManager(), mc.getItemRenderer()));
        mc.getRenderManager().register(RegistryHandler.DIRT_BALL_ENTITY, new SpriteRenderer<>(mc.getRenderManager(), mc.getItemRenderer()));
    }
}

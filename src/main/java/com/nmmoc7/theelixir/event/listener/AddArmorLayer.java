package com.nmmoc7.theelixir.event.listener;

import com.nmmoc7.theelixir.render.FoxLayerRender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.client.renderer.entity.layers.BipedArmorLayer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;

import java.util.ArrayList;

/**
 * @author DustW
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class AddArmorLayer {
    @SubscribeEvent
    public static void loadComplete(FMLLoadCompleteEvent evt) {
        EntityRendererManager manager = Minecraft.getInstance().getRenderManager();
        for (PlayerRenderer renderer : manager.getSkinMap().values()) {
            addCustomArmorLayer(renderer);
        }
    }

    private static <T extends LivingEntity, M extends BipedModel<T>, A extends BipedModel<T>> void addCustomArmorLayer(LivingRenderer<T, M> renderer) {
        for (LayerRenderer<T, M> layerRenderer : new ArrayList<>(renderer.layerRenderers)) {
            if (layerRenderer.getClass() == BipedArmorLayer.class) {
                renderer.addLayer((LayerRenderer<T, M>) new FoxLayerRender((IEntityRenderer<PlayerEntity, PlayerModel<PlayerEntity>>) renderer));
                break;
            }
        }
    }
}

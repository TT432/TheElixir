package com.nmmoc7.theelixir.ister;

import com.nmmoc7.theelixir.RegistryHandler;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Map;

/**
 * @author DustW
 */

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class OnModelBaked {
    @SubscribeEvent
    public static void onModelBaked(ModelBakeEvent event) {
        Map<ResourceLocation, IBakedModel> modelRegistry = event.getModelRegistry();
        ModelResourceLocation location = new ModelResourceLocation(RegistryHandler.SUPER_FOOD.getRegistryName(), "inventory");
        IBakedModel existingModel = modelRegistry.get(location);
        if (existingModel == null) {
            throw new RuntimeException("Did not find Obsidian Hidden in registry");
        } else if (existingModel instanceof BakedModel) {
            throw new RuntimeException("Tried to replaceObsidian Hidden twice");
        } else {
            BakedModel obsidianWrenchBakedModel = new BakedModel(existingModel);
            event.getModelRegistry().put(location, obsidianWrenchBakedModel);
        }
    }
}
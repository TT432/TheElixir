package io.github.tt432.theelixir.client;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLLoadCompleteEvent;

/**
 * @author DustW
 */
@EventBusSubscriber(value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AddArmorLayerHandler {
    @SubscribeEvent
    public static void loadComplete(FMLLoadCompleteEvent evt) {
        Minecraft.getInstance().getEntityRenderDispatcher().getSkinMap().forEach((entity, skin) -> {
            if (skin instanceof PlayerRenderer playerRenderer) {

                // todo playerRenderer.addLayer(new FoxRenderLayer(playerRenderer));
                // todo playerRenderer.addLayer(new FlowerRenderLayer(playerRenderer));
                // todo playerRenderer.addLayer(new ClothesRenderLayer(playerRenderer));

            }
        });
    }
}

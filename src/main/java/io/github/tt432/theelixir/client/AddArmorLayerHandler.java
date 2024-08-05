package io.github.tt432.theelixir.client;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

/**
 * @author DustW
 */
@EventBusSubscriber(value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AddArmorLayerHandler {
    @SubscribeEvent
    public static void onEvent(EntityRenderersEvent.AddLayers event) {
        event.getSkins().forEach(model -> {
            if (event.getSkin(model) instanceof PlayerRenderer playerRenderer) {
                playerRenderer.addLayer(new FoxTailRenderLayer(playerRenderer));
                // todo playerRenderer.addLayer(new FlowerRenderLayer(playerRenderer));
                // todo playerRenderer.addLayer(new ClothesRenderLayer(playerRenderer));
            }
        });
    }
}

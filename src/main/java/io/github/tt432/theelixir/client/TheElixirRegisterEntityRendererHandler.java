package io.github.tt432.theelixir.client;


import io.github.tt432.theelixir.common.entity.TheElixirEntities;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

/**
 * @author DustW
 */
@EventBusSubscriber(value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TheElixirRegisterEntityRendererHandler {
    @SubscribeEvent
    public static void onClientSetup(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(TheElixirEntities.GRENADE.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(TheElixirEntities.DIRT_BALL.get(), ThrownItemRenderer::new);
    }
}

package io.github.tt432.theelixir.common.particle;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;

/**
 * @author DustW
 */
@EventBusSubscriber(value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ParticleClientRegistryHandler {
    @SubscribeEvent
    public static void onParticleClientRegister(RegisterParticleProvidersEvent event) {
        event.registerSprite(TheElixirParticles.REFRESHMENT.get(), new RefreshmentParticle.Factory());
    }
}

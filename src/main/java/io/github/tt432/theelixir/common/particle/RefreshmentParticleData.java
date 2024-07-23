package io.github.tt432.theelixir.common.particle;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;

/**
 * @author q2437
 */
public class RefreshmentParticleData implements ParticleOptions {

    public RefreshmentParticleData() {
    }

    @Override
    public ParticleType<?> getType() {
        return TheElixirParticles.REFRESHMENT.get();
    }
}

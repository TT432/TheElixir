package io.github.tt432.theelixir.common.particle;

import io.github.tt432.theelixir.TheElixir;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * @author DustW
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TheElixirParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES =
            DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, TheElixir.MOD_ID);

    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> REFRESHMENT =
            PARTICLE_TYPES.register("refreshment", () -> new SimpleParticleType(false));
}

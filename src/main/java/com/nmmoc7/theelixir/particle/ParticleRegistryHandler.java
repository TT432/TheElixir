package com.nmmoc7.theelixir.particle;

import com.mojang.serialization.Codec;
import com.nmmoc7.theelixir.TheElixir;
import net.minecraft.particles.ParticleType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.awt.*;

/**
 * @author DustW
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ParticleRegistryHandler {
    public static final RefreshmentParticleData REFRESHMENT_PARTICLE = new RefreshmentParticleData(new Color(0), 0);
    public static final BloodMouseParticleData BLOOD_MOUSE_PARTICLE = new BloodMouseParticleData(new Color(0), 0);

    public static final ParticleType<RefreshmentParticleData> REFRESHMENT = new ParticleType<RefreshmentParticleData>(false, RefreshmentParticleData.DESERIALIZER) {
        @Override
        public Codec<RefreshmentParticleData> func_230522_e_() {
            return Codec.unit(REFRESHMENT_PARTICLE);
        }
    };

    public static final ParticleType<BloodMouseParticleData> BLOOD_MOUSE = new ParticleType<BloodMouseParticleData>(false, BloodMouseParticleData.DESERIALIZER) {
        @Override
        public Codec<BloodMouseParticleData> func_230522_e_() {
            return Codec.unit(BLOOD_MOUSE_PARTICLE);
        }
    };

    @SubscribeEvent
    public static void onParticleRegister(RegistryEvent.Register<ParticleType<?>> event) {
        event.getRegistry().register(REFRESHMENT.setRegistryName(new ResourceLocation(TheElixir.MOD_ID, "refreshment")));
        event.getRegistry().register(BLOOD_MOUSE.setRegistryName(new ResourceLocation(TheElixir.MOD_ID, "blood_mouse")));
    }
}

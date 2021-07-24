package com.nmmoc7.theelixir.particle;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.nmmoc7.theelixir.TheElixir;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

import java.awt.*;
import java.util.Locale;

/**
 * @author q2437
 */
public class BloodMouseParticleData implements IParticleData {
    private final Color color;
    private final float diameter;
    public static final IDeserializer<BloodMouseParticleData> DESERIALIZER = new IDeserializer<BloodMouseParticleData>() {
        @Override
        public BloodMouseParticleData deserialize(ParticleType<BloodMouseParticleData> particleTypeIn, StringReader reader) throws CommandSyntaxException {
            return ParticleRegistryHandler.BLOOD_MOUSE_PARTICLE;
        }

        @Override
        public BloodMouseParticleData read(ParticleType<BloodMouseParticleData> particleTypeIn, PacketBuffer buffer) {
            final int MIN_COLOUR = 0;
            final int MAX_COLOUR = 255;
            int red = MathHelper.clamp(buffer.readInt(), MIN_COLOUR, MAX_COLOUR);
            int green = MathHelper.clamp(buffer.readInt(), MIN_COLOUR, MAX_COLOUR);
            int blue = MathHelper.clamp(buffer.readInt(), MIN_COLOUR, MAX_COLOUR);
            int alpha = MathHelper.clamp(buffer.readInt(), 1, MAX_COLOUR);
            float diameter = buffer.readFloat();
            return new BloodMouseParticleData(new Color(red, green, blue, alpha), diameter);
        }
    };

    public BloodMouseParticleData(Color color, float diameter) {
        this.color = color;
        this.diameter = diameter;
    }

    @Override
    public ParticleType<?> getType() {
        return ParticleRegistryHandler.BLOOD_MOUSE;
    }

    @Override
    public void write(PacketBuffer buffer) {
        buffer.writeInt(this.color.getRed());
        buffer.writeInt(this.color.getGreen());
        buffer.writeInt(this.color.getBlue());
        buffer.writeInt(this.color.getAlpha());
        buffer.writeFloat(this.diameter);
    }

    @Override
    public String getParameters() {
        return String.format(Locale.ROOT, "%s %.2f %s %s %s %s",
                new ResourceLocation(TheElixir.MOD_ID, "blood_mouse"), diameter, color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
    }

    public Color getColor() {
        return color;
    }

    public float getDiameter() {
        return diameter;
    }
}

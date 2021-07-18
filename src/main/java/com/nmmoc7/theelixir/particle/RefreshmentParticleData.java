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
public class RefreshmentParticleData implements IParticleData {
    private final Color color;
    private final float diameter;
    public static final IDeserializer<RefreshmentParticleData> DESERIALIZER = new IDeserializer<RefreshmentParticleData>() {
        @Override
        public RefreshmentParticleData deserialize(ParticleType<RefreshmentParticleData> particleTypeIn, StringReader reader) throws CommandSyntaxException {
            return ParticleRegistryHandler.REFRESHMENT_PARTICLE;
        }

        @Override
        public RefreshmentParticleData read(ParticleType<RefreshmentParticleData> particleTypeIn, PacketBuffer buffer) {
            final int MIN_COLOUR = 0;
            final int MAX_COLOUR = 255;
            int red = MathHelper.clamp(buffer.readInt(), MIN_COLOUR, MAX_COLOUR);
            int green = MathHelper.clamp(buffer.readInt(), MIN_COLOUR, MAX_COLOUR);
            int blue = MathHelper.clamp(buffer.readInt(), MIN_COLOUR, MAX_COLOUR);
            int alpha = MathHelper.clamp(buffer.readInt(), 1, MAX_COLOUR);
            float diameter = buffer.readFloat();
            return new RefreshmentParticleData(new Color(red, green, blue, alpha), diameter);
        }
    };

    public RefreshmentParticleData(Color color, float diameter) {
        this.color = color;
        this.diameter = diameter;
    }

    @Override
    public ParticleType<?> getType() {
        return ParticleRegistryHandler.REFRESHMENT;
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
                new ResourceLocation(TheElixir.MOD_ID, "refreshment"), diameter, color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
    }

    public Color getColor() {
        return color;
    }

    public float getDiameter() {
        return diameter;
    }
}

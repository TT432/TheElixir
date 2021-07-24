package com.nmmoc7.theelixir.particle;

import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * @author DustW
 */
public class BloodMouseParticle extends SpriteTexturedParticle {
    IAnimatedSprite spriteWithAge;

    protected BloodMouseParticle(ClientWorld world, double x, double y, double z, IAnimatedSprite spriteWithAge) {
        super(world, x, y, z, 0.0D, 0.0D, 0.0D);
        this.motionX *= 0.01F;
        this.motionY *= 0.01F;
        this.motionZ *= 0.01F;
        this.motionY += 0.1D;
        this.spriteWithAge = spriteWithAge;
        this.particleScale *= 1.5F;
        this.maxAge = 3;
        this.canCollide = false;
        this.selectSpriteWithAge(spriteWithAge);
    }

    @Override
    public float getScale(float scaleFactor) {
        return this.particleScale * MathHelper.clamp(((float)this.age + scaleFactor) / (float)this.maxAge * 32.0F, 0.0F, 1.0F);
    }

    @Override
    public void tick() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        if (this.age++ >= this.maxAge) {
            this.setExpired();
        } else {
            this.selectSpriteWithAge(this.spriteWithAge);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static class Factory implements IParticleFactory<BloodMouseParticleData> {
        private final IAnimatedSprite sprites;

        public Factory(IAnimatedSprite sprite) {
            this.sprites = sprite;
        }

        @Override
        public Particle makeParticle(BloodMouseParticleData typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new BloodMouseParticle(worldIn, x, y, z, sprites);
        }
    }

    @Override
    public IParticleRenderType getRenderType() {
        return IParticleRenderType.PARTICLE_SHEET_LIT;
    }
}

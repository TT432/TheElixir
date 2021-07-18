package com.nmmoc7.theelixir.particle;

import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * @author DustW
 */
public class RefreshmentParticle extends SpriteTexturedParticle {
    protected RefreshmentParticle(ClientWorld world, double x, double y, double z) {
        super(world, x, y, z, 0.0D, 0.0D, 0.0D);
        this.motionX *= 0.01F;
        this.motionY *= 0.01F;
        this.motionZ *= 0.01F;
        this.motionY += 0.1D;
        this.particleScale *= 1.5F;
        this.maxAge = 16;
        this.canCollide = false;
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
            this.move(this.motionX, this.motionY, this.motionZ);
            if (this.posY == this.prevPosY) {
                this.motionX *= 1.1D;
                this.motionZ *= 1.1D;
            }

            this.motionX *= 0.86F;
            this.motionY *= 0.86F;
            this.motionZ *= 0.86F;
            if (this.onGround) {
                this.motionX *= 0.7F;
                this.motionZ *= 0.7F;
            }

        }
    }

    @OnlyIn(Dist.CLIENT)
    public static class Factory implements IParticleFactory<RefreshmentParticleData> {
        private final IAnimatedSprite sprites;

        public Factory(IAnimatedSprite sprite) {
            this.sprites = sprite;
        }

        @Override
        public Particle makeParticle(RefreshmentParticleData typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            RefreshmentParticle a = new RefreshmentParticle(worldIn, x, y, z);
            a.selectSpriteRandomly(sprites);
            return a;
        }
    }

    @Override
    public IParticleRenderType getRenderType() {
        return IParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }
}

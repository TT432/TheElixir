package org.teacon.theelixir.entity;

import net.minecraft.entity.*;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.network.IPacket;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import org.teacon.theelixir.RegistryHandler;
import org.teacon.theelixir.particle.ParticleRegistryHandler;

/**
 * @author DustW
 */
public class GrenadeEntity extends ProjectileItemEntity {
    public GrenadeEntity(EntityType<? extends ProjectileItemEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public GrenadeEntity(double x, double y, double z, World worldIn) {
        super(RegistryHandler.GRENADE_ENTITY, x, y, z, worldIn);
    }

    public GrenadeEntity(LivingEntity player, World worldIn) {
        super(RegistryHandler.GRENADE_ENTITY, player, worldIn);
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected Item getDefaultItem() {
        return RegistryHandler.SMELLY_GRENADE;
    }

    @Override
    protected float getGravityVelocity() {
        return 0.05F;
    }

    @Override
    protected void onImpact(RayTraceResult result) {
        super.onImpact(result);

        if (!world.isRemote) {
            AreaEffectCloudEntity cloud = new AreaEffectCloudEntity(this.world, this.getPosX(), this.getPosY(), this.getPosZ());

            Entity entity = this.getShooter();
            if (entity instanceof LivingEntity) {
                cloud.setOwner((LivingEntity) entity);
            }

            cloud.setRadius(3.0F);
            cloud.setRadiusOnUse(-0.5F);
            cloud.setWaitTime(10);
            cloud.setRadiusPerTick(-cloud.getRadius() / (float) cloud.getDuration());
            cloud.setDuration(5 * 20);
            cloud.setParticleData(ParticleRegistryHandler.REFRESHMENT_PARTICLE);
            cloud.addEffect(new EffectInstance(Effects.SATURATION));

            this.world.addEntity(cloud);

            this.remove();
        }
        else {
            world.playSound(this.getPosX(), this.getPosY(), this.getPosZ(), SoundEvents.ENTITY_SPLASH_POTION_BREAK, SoundCategory.NEUTRAL, 1.0F, world.getRandom().nextFloat() * 0.1F + 0.9F, false);
        }
    }
}

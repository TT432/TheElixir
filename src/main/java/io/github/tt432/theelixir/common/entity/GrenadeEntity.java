package io.github.tt432.theelixir.common.entity;

import io.github.tt432.theelixir.common.item.TheElixirItems;
import io.github.tt432.theelixir.common.particle.TheElixirParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;

/**
 * @author DustW
 */
public class GrenadeEntity extends ThrowableItemProjectile {
    public GrenadeEntity(EntityType<? extends ThrowableItemProjectile> type, Level worldIn) {
        super(type, worldIn);
    }

    public GrenadeEntity(double x, double y, double z, Level worldIn) {
        super(TheElixirEntities.GRENADE.get(), x, y, z, worldIn);
    }

    public GrenadeEntity(LivingEntity player, Level worldIn) {
        super(TheElixirEntities.GRENADE.get(), player, worldIn);
    }

    @Override
    protected Item getDefaultItem() {
        return TheElixirItems.SMELLY_GRENADE.asItem();
    }

    @Override
    protected double getDefaultGravity() {
        return 0.05F;
    }

    @Override
    protected void onHit(HitResult pResult) {
        super.onHit(pResult);

        if (!level().isClientSide) {
            var cloud = new AreaEffectCloud(level(), position().x, position().y, position().z);

            if (this.getOwner() instanceof LivingEntity living) {
                cloud.setOwner(living);
            }

            cloud.setRadius(3.0F);
            cloud.setRadiusOnUse(-0.5F);
            cloud.setWaitTime(10);
            cloud.setRadiusPerTick(-cloud.getRadius() / (float) cloud.getDuration());
            cloud.setDuration(5 * 20);
            cloud.setParticle(TheElixirParticles.REFRESHMENT.get());
            cloud.addEffect(new MobEffectInstance(MobEffects.SATURATION));

            level().addFreshEntity(cloud);

            discard();
        } else {
            level().playSound(null,
                    new BlockPos((int) position().x, (int) position().y, (int) position().z),
                    SoundEvents.SPLASH_POTION_BREAK,
                    SoundSource.NEUTRAL,
                    1.0F, level().getRandom().nextFloat() * 0.1F + 0.9F);
        }
    }
}

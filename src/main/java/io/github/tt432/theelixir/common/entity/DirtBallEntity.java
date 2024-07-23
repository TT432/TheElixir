package io.github.tt432.theelixir.common.entity;

import io.github.tt432.theelixir.common.TheElixirDamageTypes;
import io.github.tt432.theelixir.common.item.DirtBallItem;
import io.github.tt432.theelixir.common.item.TheElixirItems;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

/**
 * @author DustW
 */
public class DirtBallEntity extends ThrowableItemProjectile {
    public DirtBallEntity(EntityType<? extends ThrowableItemProjectile> type, Level worldIn) {
        super(type, worldIn);
    }

    public DirtBallEntity(Level worldIn, double x, double y, double z) {
        super(TheElixirEntities.DIRT_BALL.get(), x, y, z, worldIn);
    }

    public DirtBallEntity(Level worldIn, LivingEntity livingEntityIn) {
        super(TheElixirEntities.DIRT_BALL.get(), livingEntityIn, worldIn);
    }

    int life = 0;

    @Override
    public void tick() {
        super.tick();
        if (++life > 100) {
            discard();
        }
    }

    @Override
    protected void onHit(HitResult pResult) {
        super.onHit(pResult);

        if (!level().isClientSide) {
            ((ServerLevel) level()).sendParticles(new ItemParticleOption(ParticleTypes.ITEM, getItem()),
                    position().x, position().y, position().z,
                    100,
                    1, 1, 1,
                    0.4);
        }

        discard();
    }

    @Override
    public boolean isNoGravity() {
        return true;
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);

        if (result.getEntity() instanceof LivingEntity) {
            if (!level().isClientSide) {
                float damage;

                if (getOwner() instanceof Player shooter) {
                    damage = ((DirtBallItem) getDefaultItem()).getDamageAmount(shooter);
                } else {
                    damage = ((DirtBallItem) getDefaultItem()).getDamageAmount(null);
                }

                result.getEntity().hurt(level().damageSources().source(TheElixirDamageTypes.DIRT_BALL,
                        getOwner(), this), damage);
            } else {
                level().playSound(null, position().x, position().y, position().z,
                        SoundEvents.GRASS_BREAK, SoundSource.NEUTRAL,
                        0.7F, 0.9F + level().getRandom().nextFloat());
            }
        }
    }

    @Override
    protected Item getDefaultItem() {
        return TheElixirItems.DIRT_BALL_ITEM.get();
    }
}

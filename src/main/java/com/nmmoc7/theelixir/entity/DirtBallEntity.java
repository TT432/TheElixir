package com.nmmoc7.theelixir.entity;

import com.nmmoc7.theelixir.RegistryHandler;
import com.nmmoc7.theelixir.damagesource.ModDamageSources;
import com.nmmoc7.theelixir.item.DirtBallItem;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.network.IPacket;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

/**
 * @author DustW
 */
public class DirtBallEntity extends ProjectileItemEntity {
    public DirtBallEntity(EntityType<? extends ProjectileItemEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public DirtBallEntity(World worldIn, double x, double y, double z) {
        super(RegistryHandler.DIRT_BALL_ENTITY, x, y, z, worldIn);
    }

    public DirtBallEntity(World worldIn, LivingEntity livingEntityIn) {
        super(RegistryHandler.DIRT_BALL_ENTITY, livingEntityIn, worldIn);
    }

    @Override
    protected void onImpact(RayTraceResult result) {
        super.onImpact(result);
        if (!world.isRemote) {
            world.createExplosion(null, getPosX(), getPosY(), getPosZ(), 1, Explosion.Mode.NONE);
        }
        remove();
    }

    @Override
    public boolean hasNoGravity() {
        return true;
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected void func_230299_a_(BlockRayTraceResult result) {
        super.func_230299_a_(result);
        world.playSound(getPosX(), getPosY(), getPosZ(), SoundEvents.BLOCK_GRASS_BREAK, SoundCategory.NEUTRAL, 0.7F, 0.9F + world.getRandom().nextFloat(), false);
    }

    @Override
    protected void onEntityHit(EntityRayTraceResult result) {
        super.onEntityHit(result);
        if (result.getEntity() instanceof LivingEntity) {
            if (!world.isRemote) {
                float damage;

                if (getShooter() instanceof PlayerEntity) {
                    damage = ((DirtBallItem) getDefaultItem()).getDamageCount((PlayerEntity) getShooter());
                }
                else {
                    damage = ((DirtBallItem) getDefaultItem()).getDamageCount(null);
                }

                result.getEntity().attackEntityFrom(ModDamageSources.DIRT_BALL, damage);
            }
            else {
                world.playSound(getPosX(), getPosY(), getPosZ(), SoundEvents.BLOCK_GRASS_BREAK, SoundCategory.NEUTRAL, 0.7F, 0.9F + world.getRandom().nextFloat(), false);
            }
        }
    }

    @Override
    protected Item getDefaultItem() {
        return RegistryHandler.DIRT_BALL_ITEM;
    }
}
package com.nmmoc7.theelixir.item;

import com.nmmoc7.theelixir.damagesource.ModDamageSources;
import com.nmmoc7.theelixir.particle.BloodMouseParticleData;
import com.nmmoc7.theelixir.particle.ParticleRegistryHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.world.BlockEvent;
import org.lwjgl.system.CallbackI;

import java.awt.*;
import java.util.List;

/**
 * @author DustW
 */
public class SwallowTheWorld extends ModItemBase {
    public SwallowTheWorld() {
        super("swallow_the_world", 1);
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BLOCK;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 80;
    }

    @Override
    public void onUse(World worldIn, LivingEntity livingEntityIn, ItemStack stack, int count) {
        if (livingEntityIn instanceof ServerPlayerEntity) {
            List<Entity> list = worldIn.getEntitiesWithinAABB(Entity.class,
                    new AxisAlignedBB(
                            livingEntityIn.getPosX() + 10,
                            livingEntityIn.getPosY() + 10,
                            livingEntityIn.getPosZ() + 10,
                            livingEntityIn.getPosX() - 10,
                            livingEntityIn.getPosY() - 10,
                            livingEntityIn.getPosZ() - 10
                    ), (entity -> !(entity instanceof PlayerEntity) && entity.isAlive()));
            list.forEach(entity -> {
                entity.attackEntityFrom(ModDamageSources.BITE, 10);
                if (!entity.isAlive()) {
                    ((PlayerEntity) livingEntityIn).getFoodStats().setFoodLevel(((PlayerEntity) livingEntityIn).getFoodStats().getFoodLevel() + 1);
                }
                ((ServerWorld) worldIn).spawnParticle(ParticleRegistryHandler.BLOOD_MOUSE_PARTICLE, entity.getPosX(), entity.getPosY(), entity.getPosZ(), 10, 1, 1, 1, 0);
            });
        }
        super.onUse(worldIn, livingEntityIn, stack, count);
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity entityLiving) {
        if (entityLiving instanceof ServerPlayerEntity) {
            ((ServerPlayerEntity) entityLiving).getCooldownTracker().setCooldown(stack.getItem(), 5 * 20);
        }
        return super.onItemUseFinish(stack, worldIn, entityLiving);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        playerIn.setActiveHand(handIn);
        return ActionResult.resultConsume(playerIn.getHeldItem(handIn));
    }
}

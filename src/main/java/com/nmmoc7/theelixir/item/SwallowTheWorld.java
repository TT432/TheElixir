package com.nmmoc7.theelixir.item;

import com.nmmoc7.theelixir.config.ServerConfigs;
import com.nmmoc7.theelixir.damagesource.ModDamageSources;
import com.nmmoc7.theelixir.particle.ParticleRegistryHandler;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
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
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        String regName = "tooltip.swallow_the_world.";
        tooltip.add(new TranslationTextComponent(regName + "1"));
        tooltip.add(new StringTextComponent(" "));
        tooltip.add(new TranslationTextComponent(regName + "2"));
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    @Override
    public void onUse(World worldIn, LivingEntity livingEntityIn, ItemStack stack, int count) {
        if (livingEntityIn instanceof ServerPlayerEntity) {
            boolean flag1 = ServerConfigs.INSTANCE.SWALLOW_NOT_LIVING.get();
            boolean flag2 = ServerConfigs.INSTANCE.SWALLOW_PLAYER.get();

            List<Entity> list = worldIn.getEntitiesWithinAABB(Entity.class,
                    new AxisAlignedBB(
                            livingEntityIn.getPosX() + 10,
                            livingEntityIn.getPosY() + 10,
                            livingEntityIn.getPosZ() + 10,
                            livingEntityIn.getPosX() - 10,
                            livingEntityIn.getPosY() - 10,
                            livingEntityIn.getPosZ() - 10
                    ), (entity -> {
                        boolean flag3 = flag1 || !(entity instanceof LivingEntity);
                        boolean flag4 = flag2 || !(entity instanceof PlayerEntity);
                        return !ServerConfigs.INSTANCE.SWALLOW_BLACK_LIST.contains(entity) && flag3 && flag4 && entity.isAlive();
                    }));
            list.forEach(entity -> {
                if (!entity.attackEntityFrom(ModDamageSources.withAttacker(ModDamageSources.BITE, livingEntityIn), 10)) {
                    if (!(entity instanceof LivingEntity)) {
                        entity.remove();
                    }
                }
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

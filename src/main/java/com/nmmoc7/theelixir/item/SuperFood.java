package com.nmmoc7.theelixir.item;

import com.nmmoc7.theelixir.RegistryHandler;
import com.nmmoc7.theelixir.TheElixir;
import com.nmmoc7.theelixir.ister.SuperFoodItemStackRender;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import org.lwjgl.system.CallbackI;

import javax.annotation.Nullable;
import java.util.List;

/**
 * @author DustW
 */
public class SuperFood extends Item {
    public SuperFood() {
        super(new Properties().group(ModItemGroup.INSTANCE).setISTER(() -> SuperFoodItemStackRender::new)
        .food(new Food.Builder().setAlwaysEdible().hunger(4).build()));

        setRegistryName(new ResourceLocation(TheElixir.MOD_ID, "super_food"));
    }

    @Override
    public ITextComponent getDisplayName(ItemStack stack) {
        return getItemStack(stack).getDisplayName();
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.EAT;
    }

    @Override
    public String getTranslationKey() {
        return RegistryHandler.SUPER_FOOD_STAND.getTranslationKey();
    }

    @Override
    public String getTranslationKey(ItemStack stack) {
        return getItemStack(stack).getTranslationKey();
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        getItemStack(stack).getItem().addInformation(stack, worldIn, tooltip, flagIn);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        if (!worldIn.isRemote) {
            if (handIn == Hand.MAIN_HAND) {
                if (!playerIn.getHeldItemOffhand().isEmpty()) {
                    setItemStack(playerIn.getHeldItemMainhand(), playerIn.getHeldItemOffhand().copy());
                    playerIn.setHeldItem(Hand.OFF_HAND, ItemStack.EMPTY);
                }
            }
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    @Override
    public void onUse(World worldIn, LivingEntity livingEntityIn, ItemStack stack, int count) {
        if (!worldIn.isRemote) {
            Vector3d vector3d = new Vector3d((worldIn.getRandom().nextFloat() - 0.5D) * 0.1D, Math.random() * 0.1D + 0.1D, 0.0D);
            vector3d = vector3d.rotatePitch(-livingEntityIn.rotationPitch * ((float) Math.PI / 180F));
            vector3d = vector3d.rotateYaw(-livingEntityIn.rotationYaw * ((float) Math.PI / 180F));
            double d0 = (double) (-worldIn.getRandom().nextFloat()) * 0.6D - 0.3D;
            Vector3d vector3d1 = new Vector3d((worldIn.getRandom().nextFloat() - 0.5D) * 0.3D, d0, 0.6D);
            vector3d1 = vector3d1.rotatePitch(-livingEntityIn.rotationPitch * ((float) Math.PI / 180F));
            vector3d1 = vector3d1.rotateYaw(-livingEntityIn.rotationYaw * ((float) Math.PI / 180F));
            vector3d1 = vector3d1.add(livingEntityIn.getPosX(), livingEntityIn.getPosYEye(), livingEntityIn.getPosZ());

            ((ServerWorld) worldIn).spawnParticle(new ItemParticleData(ParticleTypes.ITEM, getItemStack(stack)),
                    vector3d1.x, vector3d1.y, vector3d1.z, 1, vector3d.x, vector3d.y + 0.05D, vector3d.z, 0.0D);
        }
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity entityLiving) {
        return super.onItemUseFinish(stack, worldIn, entityLiving);
    }

    public static void setItemStack(ItemStack superFood, ItemStack itemStack) {
        CompoundNBT itemNbt = new CompoundNBT();
        itemStack.write(itemNbt);
        superFood.getOrCreateTag().put("c_itemstack", itemNbt);
    }

    public static ItemStack getItemStack(ItemStack superFood) {
        ItemStack result = ItemStack.read(superFood.getOrCreateTag().getCompound("c_itemstack"));
        if (result.isEmpty()) {
            return new ItemStack(RegistryHandler.SUPER_FOOD_STAND);
        }
        else {
            result.shrink(result.getCount() - 1);
            return result;
        }
    }
}

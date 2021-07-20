package com.nmmoc7.theelixir.item;

import com.nmmoc7.theelixir.RegistryHandler;
import com.nmmoc7.theelixir.TheElixir;
import com.nmmoc7.theelixir.capability.CapabilityRegistryHandler;
import com.nmmoc7.theelixir.capability.TheElixirCapability;
import com.nmmoc7.theelixir.damagesource.ModDamageSources;
import com.nmmoc7.theelixir.utils.EntityUtils;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

/**
 * @author DustW
 */
public class DustySword extends SwordItem {
    public DustySword() {
        super(new IItemTier() {
            @Override
            public int getMaxUses() {
                return 0xF_F_F;
            }

            @Override
            public float getEfficiency() {
                return 30;
            }

            @Override
            public float getAttackDamage() {
                return 6;
            }

            @Override
            public int getHarvestLevel() {
                return 0;
            }

            @Override
            public int getEnchantability() {
                return 30;
            }

            @Override
            public Ingredient getRepairMaterial() {
                return Ingredient.fromStacks(new ItemStack(RegistryHandler.FLOWER_ITEM));
            }
        }, 1, -2.4F, new Properties().group(ModItemGroup.INSTANCE).maxStackSize(1));

        this.setRegistryName(new ResourceLocation(TheElixir.MOD_ID, "dusty_sword"));
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(new TranslationTextComponent("tooltip.dusty_sword.1"));
        tooltip.add(new StringTextComponent(" "));
        tooltip.add(new TranslationTextComponent("tooltip.dusty_sword.2"));
    }

    @Override
    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker instanceof ServerPlayerEntity) {
            TheElixirCapability cap = attacker.getCapability(CapabilityRegistryHandler.THE_ELIXIR_CAPABILITY).orElse(null);

            if (cap.isHasFlower()) {
                target.hurtResistantTime = 0;
                target.attackEntityFrom(ModDamageSources.DIRT_BALL, 2);
                target.hurtResistantTime = 0;
                target.attackEntityFrom(ModDamageSources.DIRT_BALL_KING, 2);
                target.hurtResistantTime = 0;
                target.attackEntityFrom(ModDamageSources.DIRT_BALL_QUEEN, 2);

                target.hurtResistantTime = 0;
                EntityUtils.defaultDirtBallEntityShoot(attacker.world, (PlayerEntity) attacker);
            }
        }
        return super.hitEntity(stack, target, attacker);
    }
}

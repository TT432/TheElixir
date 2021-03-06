package com.nmmoc7.theelixir.item;

import com.nmmoc7.theelixir.TheElixir;
import com.nmmoc7.theelixir.capability.CapabilityRegistryHandler;
import com.nmmoc7.theelixir.damagesource.ModDamageSources;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SwordItem;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

/**
 * @author DustW
 */
public class Restrainer extends SwordItem {
    public Restrainer() {
        super(new IItemTier() {
            @Override
            public int getMaxUses() {
                return 1000000;
            }

            @Override
            public float getEfficiency() {
                return 12;
            }

            @Override
            public float getAttackDamage() {
                return -1;
            }

            @Override
            public int getHarvestLevel() {
                return 4;
            }

            @Override
            public int getEnchantability() {
                return 22;
            }

            @Override
            public Ingredient getRepairMaterial() {
                return Ingredient.fromItems(Items.PUMPKIN);
            }
        }, 0, TheElixir.THE_ULTIMATE_ANSWER_TO_THE_UNIVERSE, new Properties().group(ModItemGroup.INSTANCE));

        setRegistryName(new ResourceLocation(TheElixir.MOD_ID, "restrainer"));
    }

    @Override
    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (target instanceof ServerPlayerEntity && target.getCapability(CapabilityRegistryHandler.THE_ELIXIR_CAPABILITY).orElse(null).isUsedElixir()) {
            target.attackEntityFrom(ModDamageSources.withAttacker(ModDamageSources.RESTRAINER, attacker), 20000);
        }

        return super.hitEntity(stack, target, attacker);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(new StringTextComponent("???????????????????????????????????????????????????"));
        tooltip.add(new StringTextComponent(" "));
        tooltip.add(new StringTextComponent("??7??o??l???????????????????????????????????????"));
    }
}

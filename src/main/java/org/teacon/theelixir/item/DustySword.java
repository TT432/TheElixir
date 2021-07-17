package org.teacon.theelixir.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import org.teacon.theelixir.RegistryHandler;
import org.teacon.theelixir.TheElixir;
import org.teacon.theelixir.capability.CapabilityRegistryHandler;
import org.teacon.theelixir.capability.TheElixirCapability;
import org.teacon.theelixir.damagesource.ModDamageSources;
import org.teacon.theelixir.utils.EntityUtils;

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

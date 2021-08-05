package com.nmmoc7.theelixir.support.curios;

import com.nmmoc7.theelixir.capability.CapabilityRegistryHandler;
import com.nmmoc7.theelixir.capability.TheElixirCapability;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import top.theillusivec4.curios.api.type.capability.ICurio;

/**
 * @author DustW
 */
public class FlowerItemCapability implements ICurio {
    @Override
    public void onUnequip(String identifier, int index, LivingEntity livingEntity) {
        if (livingEntity instanceof PlayerEntity) {
            TheElixirCapability cap = livingEntity.getCapability(CapabilityRegistryHandler.THE_ELIXIR_CAPABILITY).orElse(null);
            cap.setHasFlower(false);
            cap.curioFlower = false;
        }
    }

    @Override
    public void onEquip(String identifier, int index, LivingEntity livingEntity) {
        if (livingEntity instanceof PlayerEntity) {
            TheElixirCapability cap = livingEntity.getCapability(CapabilityRegistryHandler.THE_ELIXIR_CAPABILITY).orElse(null);
            cap.setHasFlower(true);
            cap.curioFlower = true;
        }
    }

    @Override
    public boolean canRightClickEquip() {
        return false;
    }
}

package com.nmmoc7.theelixir.support.curios;

import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import top.theillusivec4.curios.api.CuriosCapability;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author DustW
 */
public class CuriosCapProvider implements ICapabilityProvider {
    private final LazyOptional<FlowerItemCapability> flowerItemCapability;

    public CuriosCapProvider() {
        flowerItemCapability = LazyOptional.of(FlowerItemCapability::new);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return cap == CuriosCapability.ITEM ? flowerItemCapability.cast() : LazyOptional.empty();
    }
}

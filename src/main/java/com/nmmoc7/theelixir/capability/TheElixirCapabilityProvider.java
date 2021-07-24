package com.nmmoc7.theelixir.capability;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author DustW
 */
public class TheElixirCapabilityProvider implements ICapabilityProvider, INBTSerializable<CompoundNBT> {
    LazyOptional<TheElixirCapability> capability = LazyOptional.of(TheElixirCapability::new);

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return cap == CapabilityRegistryHandler.THE_ELIXIR_CAPABILITY ?
                capability.cast() : LazyOptional.empty();
    }

    public TheElixirCapabilityProvider init(ServerPlayerEntity owner) {
        capability.orElse(null).init(owner);
        return this;
    }

    @Override
    public CompoundNBT serializeNBT() {
        return capability.orElse(null).serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        capability.orElse(null).deserializeNBT(nbt);
    }
}

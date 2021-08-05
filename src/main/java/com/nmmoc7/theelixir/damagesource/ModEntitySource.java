package com.nmmoc7.theelixir.damagesource;

import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;

import javax.annotation.Nullable;

public class ModEntitySource extends DamageSource {
    Entity trueSource;

    public ModEntitySource(DamageSource damageSource, @Nullable Entity player) {
        super(damageSource.damageType);
        trueSource = player;
    }

    @Nullable
    @Override
    public Entity getTrueSource() {
        return trueSource;
    }
}

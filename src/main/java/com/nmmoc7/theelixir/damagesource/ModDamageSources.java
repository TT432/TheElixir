package com.nmmoc7.theelixir.damagesource;

import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;

import javax.annotation.Nullable;

/**
 * @author DustW
 */
public class ModDamageSources {
    public static final DamageSource BOOTS_KICKS = new DamageSource("boots_kicks");
    public static final DamageSource DIRT_BALL = new DamageSource("dirt_ball");
    public static final DamageSource DIRT_BALL_KING = new DamageSource("dirt_ball_king");
    public static final DamageSource DIRT_BALL_QUEEN = new DamageSource("dirt_ball_queen");
    public static final DamageSource BITE = new DamageSource("bite").setMagicDamage();
    public static final DamageSource RESTRAINER = new DamageSource("restrainer").setDamageAllowedInCreativeMode();

    public static DamageSource withAttacker(DamageSource damageSource, @Nullable Entity entity) {
        return new ModEntitySource(damageSource, entity);
    }
}

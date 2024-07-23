package io.github.tt432.theelixir.common;

import io.github.tt432.theelixir.TheElixir;
import io.github.tt432.theelixir.utils.ResourceLocations;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;

/**
 * @author DustW
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TheElixirDamageTypes {
    public static final ResourceKey<DamageType> DIRT_BALL = key("dirt_ball");
    public static final ResourceKey<DamageType> RESTRAINER = key("restrainer");

    private static ResourceKey<DamageType> key(String name) {
        return ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocations.of(TheElixir.MOD_ID, name));
    }
}

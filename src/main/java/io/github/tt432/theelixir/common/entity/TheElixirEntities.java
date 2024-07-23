package io.github.tt432.theelixir.common.entity;

import io.github.tt432.theelixir.TheElixir;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * @author TT432
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TheElixirEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES =
            DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, TheElixir.MOD_ID);

    public static final DeferredHolder<EntityType<?>, EntityType<DirtBallEntity>> DIRT_BALL =
            ENTITIES.register("dirt_ball",
                    () -> EntityType.Builder.<DirtBallEntity>of(DirtBallEntity::new, MobCategory.MISC)
                            .sized(0.25F, 0.25F)
                            .setTrackingRange(4)
                            .updateInterval(10)
                            .build("dirt_ball"));


    public static final DeferredHolder<EntityType<?>, EntityType<GrenadeEntity>> GRENADE =
            ENTITIES.register("grenade",
                    () -> EntityType.Builder.<GrenadeEntity>of(GrenadeEntity::new, MobCategory.MISC)
                            .sized(0.25F, 0.25F)
                            .setTrackingRange(4)
                            .updateInterval(10)
                            .build("grenade"));
}

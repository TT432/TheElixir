package io.github.tt432.theelixir.common.item;

import io.github.tt432.theelixir.TheElixir;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static net.minecraft.world.effect.MobEffectCategory.*;

/**
 * @author TT432
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TheElixirPotions {
    public static final DeferredRegister<Potion> POTIONS =
            DeferredRegister.create(BuiltInRegistries.POTION, TheElixir.MOD_ID);

    public static final DeferredHolder<Potion, Potion> FURIOUS_COCKTAIL_BENEFICIAL =
            POTIONS.register("furious_cocktail_beneficial", () -> new Potion(
                    BuiltInRegistries.MOB_EFFECT.holders()
                            .filter(me -> me.value().getCategory() == BENEFICIAL)
                            .map(me -> new MobEffectInstance(me, 20 * 15, 5))
                            .toArray(MobEffectInstance[]::new)
            ));

    public static final DeferredHolder<Potion, Potion> FURIOUS_COCKTAIL_HARMFUL =
            POTIONS.register("furious_cocktail_harmful", () -> new Potion(
                    BuiltInRegistries.MOB_EFFECT.holders()
                            .filter(me -> me.value().getCategory() == HARMFUL)
                            .map(me -> new MobEffectInstance(me, 20 * 15, 5))
                            .toArray(MobEffectInstance[]::new)
            ));

    public static final DeferredHolder<Potion, Potion> FURIOUS_COCKTAIL_NEUTRAL =
            POTIONS.register("furious_cocktail_neutral", () -> new Potion(
                    BuiltInRegistries.MOB_EFFECT.holders()
                            .filter(me -> me.value().getCategory() == NEUTRAL)
                            .map(me -> new MobEffectInstance(me, 20 * 15, 5))
                            .toArray(MobEffectInstance[]::new)
            ));

    public static final DeferredHolder<Potion, Potion> FURIOUS_COCKTAIL_ALL =
            POTIONS.register("furious_cocktail_all", () -> new Potion(
                    BuiltInRegistries.MOB_EFFECT.holders()
                            .map(me -> new MobEffectInstance(me, 20 * 15, 5))
                            .toArray(MobEffectInstance[]::new)
            ));
}

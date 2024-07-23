package io.github.tt432.theelixir.common.item;

import io.github.tt432.theelixir.TheElixir;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.core.component.DataComponentType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * @author TT432
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TheElixirItemDataComponents {
    public static final DeferredRegister.DataComponents DATA_COMPONENTS =
            DeferredRegister.createDataComponents(TheElixir.MOD_ID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<FuriousCocktailItem.Data>> FURIOUS_COCKTAIL =
            DATA_COMPONENTS.registerComponentType("furious_cocktail", builder -> builder
                    .persistent(FuriousCocktailItem.Data.CODEC)
                    .networkSynchronized(FuriousCocktailItem.Data.STREAM_CODEC));
}

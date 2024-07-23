package io.github.tt432.theelixir.data;

import io.github.tt432.theelixir.TheElixir;
import io.github.tt432.theelixir.common.TheElixirDamageTypes;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.world.damagesource.DamageType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

/**
 * @author TT432
 */
@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GatherDataHandler {
    @SubscribeEvent
    public static void onEvent(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> provider = event.getLookupProvider();

        generator.addProvider(event.includeServer(), new DatapackBuiltinEntriesProvider(
                output,
                provider,
                new RegistrySetBuilder()
                        .add(Registries.DAMAGE_TYPE, registerDmageTypes()),
                Set.of(TheElixir.MOD_ID)
        ));
    }

    private static RegistrySetBuilder.@NotNull RegistryBootstrap<DamageType> registerDmageTypes() {
        return bootstrap -> {
            bootstrap.register(TheElixirDamageTypes.DIRT_BALL, new DamageType("dirt_ball", 0));
            bootstrap.register(TheElixirDamageTypes.RESTRAINER, new DamageType("restrainer", 0));
        };
    }
}

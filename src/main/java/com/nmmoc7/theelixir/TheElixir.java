package com.nmmoc7.theelixir;

import com.nmmoc7.theelixir.config.ServerConfigs;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import top.theillusivec4.curios.api.SlotTypeMessage;
import top.theillusivec4.curios.api.SlotTypePreset;

/**
 * The value here should match an entry in the META-INF/mods.toml file
 * @author q2437
 */
@Mod("the_elixir")
public class TheElixir {
    public static final String MOD_ID = "the_elixir";
    public static final Logger LOGGER = LogManager.getLogger();

    public static final int THE_ULTIMATE_ANSWER_TO_THE_UNIVERSE = 42;

    public static boolean curiosLoaded;
    public static boolean botaniaLoaded;

    public TheElixir() {
        curiosLoaded = ModList.get().isLoaded("curios");
        botaniaLoaded = ModList.get().isLoaded("botania");

        FMLJavaModLoadingContext.get().getModEventBus().addListener(ServerConfigs.INSTANCE::onConfigLoadOrReload);
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, ServerConfigs.INSTANCE.getSpec());

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onInterModEnqueue);
    }

    private void onInterModEnqueue(final InterModEnqueueEvent event) {
        if (curiosLoaded) {
            InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE,
                    () -> SlotTypePreset.CHARM.getMessageBuilder().build());
        }
    }
}

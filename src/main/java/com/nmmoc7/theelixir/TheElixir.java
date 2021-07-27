package com.nmmoc7.theelixir;

import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import top.theillusivec4.curios.api.SlotTypeMessage;
import top.theillusivec4.curios.api.SlotTypePreset;

/**
 * The value here should match an entry in the META-INF/mods.toml file
 * @author q2437
 */
@Mod("the_elixir")
public class TheElixir {
    public static final String MOD_ID = "the_elixir";

    public static final int THE_ULTIMATE_ANSWER_TO_THE_UNIVERSE = 42;

    public static boolean curiosLoaded;

    public TheElixir() {
        curiosLoaded = ModList.get().isLoaded("curios");

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onInterModEnqueue);
    }

    private void onInterModEnqueue(final InterModEnqueueEvent event) {
        if (curiosLoaded) {
            InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE,
                    () -> SlotTypePreset.CHARM.getMessageBuilder().build());
        }
    }
}

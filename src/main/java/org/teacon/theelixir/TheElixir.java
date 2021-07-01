package org.teacon.theelixir;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The value here should match an entry in the META-INF/mods.toml file
 * @author q2437
 */
@Mod("the_elixir")
public class TheElixir {
    public static final String MOD_ID = "the_elixir";

    private static final Logger LOGGER = LogManager.getLogger();

    public TheElixir() {
        MinecraftForge.EVENT_BUS.register(this);
    }
}

package io.github.tt432.theelixir;

import io.github.tt432.theelixir.capability.TheElixirAttachmentData;
import io.github.tt432.theelixir.common.entity.TheElixirEntities;
import io.github.tt432.theelixir.common.item.TheElixirCreativeTabs;
import io.github.tt432.theelixir.common.item.TheElixirItemDataComponents;
import io.github.tt432.theelixir.common.item.TheElixirItems;
import io.github.tt432.theelixir.common.item.TheElixirPotions;
import io.github.tt432.theelixir.common.particle.TheElixirParticles;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

/**
 * The value here should match an entry in the META-INF/mods.toml file
 *
 * @author q2437
 */
@Mod("the_elixir")
public class TheElixir {
    public static final String MOD_ID = "the_elixir";

    public static final int THE_ULTIMATE_ANSWER_TO_THE_UNIVERSE = 42;

    public TheElixir(IEventBus bus) {
        TheElixirItems.ITEMS.register(bus);
        TheElixirCreativeTabs.CREATIVE_TABS.register(bus);
        TheElixirItemDataComponents.DATA_COMPONENTS.register(bus);
        TheElixirPotions.POTIONS.register(bus);
        TheElixirEntities.ENTITIES.register(bus);
        TheElixirParticles.PARTICLE_TYPES.register(bus);
        TheElixirAttachmentData.ATTACHMENTS.register(bus);
    }
}

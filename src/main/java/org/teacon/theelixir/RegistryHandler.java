package org.teacon.theelixir;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.teacon.theelixir.entity.GrenadeEntity;
import org.teacon.theelixir.item.*;

/**
 * @author DustW
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class RegistryHandler {
    public static final ElixirItem ELIXIR_ITEM = new ElixirItem();
    public static final TextItem TEXT_ITEM = new TextItem();
    public static final Restrainer RESTRAINER = new Restrainer();
    public static final SunroVientianeParaquatItem SUNRO_VIENTIANE_PARAQUAT_ITEM = new SunroVientianeParaquatItem();
    public static final FlowerItem FLOWER_ITEM = new FlowerItem();
    public static final SignInCard SIGN_IN_CARD = new SignInCard();
    public static final RefreshmentGrenade SMELLY_GRENADE = new RefreshmentGrenade();
    public static final BobBoots BOB_BOOTS = new BobBoots();
    public static final IAmFurryItem I_AM_FURRY_ITEM = new IAmFurryItem();

    @SubscribeEvent
    public static void onItemRegistry(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(
                ELIXIR_ITEM,
                TEXT_ITEM,
                RESTRAINER,
                SUNRO_VIENTIANE_PARAQUAT_ITEM,
                FLOWER_ITEM,
                SIGN_IN_CARD,
                SMELLY_GRENADE,
                BOB_BOOTS,
                I_AM_FURRY_ITEM
        );
    }

    public static final EntityType<GrenadeEntity> GRENADE_ENTITY =
            EntityType.Builder.<GrenadeEntity>create(GrenadeEntity::new, EntityClassification.MISC)
                    .size(0.25F, 0.25F)
                    .trackingRange(4)
                    .updateInterval(10).build("grenade_entity");

    @SubscribeEvent
    public static void onEntityTypeRegistry(RegistryEvent.Register<EntityType<?>> event) {
        event.getRegistry().registerAll(
                GRENADE_ENTITY.setRegistryName(new ResourceLocation(TheElixir.MOD_ID, "grenade_entity"))
        );
    }
}

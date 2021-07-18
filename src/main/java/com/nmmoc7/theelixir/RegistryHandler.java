package com.nmmoc7.theelixir;

import com.nmmoc7.theelixir.entity.DirtBallEntity;
import com.nmmoc7.theelixir.entity.GrenadeEntity;
import com.nmmoc7.theelixir.item.*;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

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
    public static final AssRevealGoggles ASS_REVEAL_GOGGLES = new AssRevealGoggles();
    public static final DustySword DUSTY_SWORD = new DustySword();
    public static final DirtBallItem DIRT_BALL_ITEM = new DirtBallItem();
    public static final HumanGlass HUMAN_GLASS = new HumanGlass();
    public static final SoulBomb SOUL_BOMB = new SoulBomb();

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
                I_AM_FURRY_ITEM,
                ASS_REVEAL_GOGGLES,
                DUSTY_SWORD,
                DIRT_BALL_ITEM,
                HUMAN_GLASS,
                SOUL_BOMB
        );
    }

    public static final EntityType<GrenadeEntity> GRENADE_ENTITY =
            EntityType.Builder.<GrenadeEntity>create(GrenadeEntity::new, EntityClassification.MISC)
                    .size(0.25F, 0.25F)
                    .trackingRange(4)
                    .updateInterval(10).build("grenade_entity");

    public static final EntityType<DirtBallEntity> DIRT_BALL_ENTITY =
            EntityType.Builder.<DirtBallEntity>create(DirtBallEntity::new, EntityClassification.MISC)
                    .size(0.25F, 0.25F)
                    .trackingRange(4)
                    .updateInterval(10).build("dirt_ball_entity");

    @SubscribeEvent
    public static void onEntityTypeRegistry(RegistryEvent.Register<EntityType<?>> event) {
        event.getRegistry().registerAll(
                GRENADE_ENTITY.setRegistryName(new ResourceLocation(TheElixir.MOD_ID, "grenade_entity")),
                DIRT_BALL_ENTITY.setRegistryName(new ResourceLocation(TheElixir.MOD_ID, "dirt_ball_entity"))
        );
    }
}

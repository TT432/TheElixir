package com.nmmoc7.theelixir;

import com.nmmoc7.theelixir.entity.DirtBallEntity;
import com.nmmoc7.theelixir.entity.GrenadeEntity;
import com.nmmoc7.theelixir.item.*;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.item.*;
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
    public static final MangoBox MANGO_BOX = new MangoBox();
    public static final MangoFood MANGO_FOOD = new MangoFood();
    public static final CtrlCItem CTRL_C_ITEM = new CtrlCItem();
    public static final SkirtItem SKIRT_ITEM = new SkirtItem();
    public static final SkirtChestItem SKIRT_CHEST_ITEM = new SkirtChestItem();
    public static final TeleportStone TELEPORT_STONE = new TeleportStone();
    public static final PhilosopherStone PHILOSOPHER_STONE = new PhilosopherStone();
    public static final SwallowTheWorld SWALLOW_THE_WORLD = new SwallowTheWorld();
    public static final SuperFood SUPER_FOOD = new SuperFood();
    public static final Item SUPER_FOOD_STAND = new Item(new Item.Properties()).setRegistryName("super_food_stand");

    public static final ModItemBase RHINE_METALS = new ModItemBase("rhine_metals", 64);
    public static final ItemTierBase RHINE_TIER = new ItemTierBase(4, 5472, 7, 3, 17, RHINE_METALS);
    public static final SwordItem RHINE_SWORD = new SwordItem(RHINE_TIER, 5, -2.4F, new Item.Properties().group(ModItemGroup.INSTANCE));
    public static final ShovelItem RHINE_SHOVEL = new ShovelItem(RHINE_TIER, 1.5F, -3.0F, new Item.Properties().group(ModItemGroup.INSTANCE));
    public static final PickaxeItem RHINE_PICKAXE = new PickaxeItem(RHINE_TIER, 1, -2.8F, new Item.Properties().group(ModItemGroup.INSTANCE));
    public static final AxeItem RHINE_AXE = new AxeItem(RHINE_TIER, 6.0F, -3.2F, new Item.Properties().group(ModItemGroup.INSTANCE));
    public static final HoeItem RHINE_HOE = new HoeItem(RHINE_TIER, 0, -3.0F, new Item.Properties().group(ModItemGroup.INSTANCE));

    /*
     * walnut核桃
     * peanut花生
     * peanut butter花生酱
     * reactor反应堆
     */


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
                SOUL_BOMB,
                RHINE_METALS,
                MANGO_BOX,
                MANGO_FOOD,
                CTRL_C_ITEM,
                SKIRT_ITEM,
                SKIRT_CHEST_ITEM,
                TELEPORT_STONE,
                PHILOSOPHER_STONE,
                SWALLOW_THE_WORLD,
                SUPER_FOOD,
                SUPER_FOOD_STAND,
                RHINE_PICKAXE.setRegistryName(new ResourceLocation(TheElixir.MOD_ID, "rhine_pickaxe")),
                RHINE_AXE.setRegistryName(new ResourceLocation(TheElixir.MOD_ID, "rhine_axe")),
                RHINE_HOE.setRegistryName(new ResourceLocation(TheElixir.MOD_ID, "rhine_hoe")),
                RHINE_SWORD.setRegistryName(new ResourceLocation(TheElixir.MOD_ID, "rhine_sword")),
                RHINE_SHOVEL.setRegistryName(new ResourceLocation(TheElixir.MOD_ID, "rhine_shovel"))
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

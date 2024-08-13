package io.github.tt432.theelixir.common.item;


import io.github.tt432.eyelib.capability.item.EyelibDataComponents;
import io.github.tt432.eyelib.capability.item.ItemTooltipReplaceData;
import io.github.tt432.eyelib.client.gui.tooltip.ReplaceTooltipData;
import io.github.tt432.theelixir.TheElixir;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * @author DustW
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TheElixirItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(TheElixir.MOD_ID);

    public static final DeferredItem<ElixirItem> ELIXIR_ITEM =
            ITEMS.registerItem("elixir_item", ElixirItem::new, new Item.Properties().stacksTo(1));

    public static final DeferredItem<Restrainer> RESTRAINER =
            ITEMS.registerItem("restrainer", Restrainer::new, new Item.Properties().stacksTo(1));

    public static final DeferredItem<FuriousCocktailItem> FURIOUS_COCKTAIL =
            ITEMS.registerItem("furious_cocktail", FuriousCocktailItem::new,
                    new Item.Properties().stacksTo(1));

    public static final DeferredItem<FlowerItem> FLOWER_ITEM =
            ITEMS.registerItem("flower_item", FlowerItem::new, new Item.Properties().stacksTo(1));

    public static final DeferredItem<RefreshmentGrenade> SMELLY_GRENADE =
            ITEMS.registerItem("refreshment_grenade", RefreshmentGrenade::new);

    public static final DeferredItem<FoxTailItem> FOX_TAIL =
            ITEMS.register("fox_tail", () -> new FoxTailItem(new Item.Properties()
                    .component(EyelibDataComponents.ITEM_TOOLTIP_REPLACE_DATA, new ItemTooltipReplaceData(
                            new ReplaceTooltipData(
                                    ResourceLocation.fromNamespaceAndPath(TheElixir.MOD_ID, "fox_tooltip"),
                                    ReplaceTooltipData.Color.EMPTY
                            )))
                    .stacksTo(1)));

    public static final DeferredItem<HumanGlass> HUMAN_GLASS =
            ITEMS.registerItem("human_glass", HumanGlass::new, new Item.Properties().stacksTo(1));

    public static final DeferredItem<DirtBallItem> DIRT_BALL_ITEM =
            ITEMS.registerItem("dirt_ball", DirtBallItem::new);

    // todo public static final DeferredItem<SkirtItem> SKIRT_ITEM = new SkirtItem();
    // todo public static final DeferredItem<SkirtChestItem> SKIRT_CHEST_ITEM = new SkirtChestItem();
}

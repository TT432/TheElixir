package io.github.tt432.theelixir.common.item;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.context.UseOnContext;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static io.github.tt432.theelixir.common.item.FuriousCocktailItem.Type.BENEFICIAL;

/**
 * @author DustW
 */
public class FuriousCocktailItem extends PotionItem {
    public FuriousCocktailItem(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack pStack, TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pTooltipFlag) {
        super.appendHoverText(pStack, pContext, pTooltipComponents, pTooltipFlag);
        pTooltipComponents.add(Component.literal("右键生物以装满瓶子"));
        pTooltipComponents.add(Component.literal("当前模式: " + getMode(pStack).name()));
    }

    @Getter
    @AllArgsConstructor
    @EqualsAndHashCode
    public static final class Data {
        public static final Codec<Data> CODEC = RecordCodecBuilder.create(ins -> ins.group(
                Type.CODEC.fieldOf("mode").forGetter(o -> o.mode)
        ).apply(ins, Data::new));

        public static final StreamCodec<ByteBuf, Data> STREAM_CODEC = StreamCodec.composite(
                Type.STREAM_CODEC,
                Data::getMode,
                Data::new
        );

        Type mode;
    }

    public Type getMode(ItemStack potion) {
        Data data = getOrCreateData(potion);
        if (data == null) return BENEFICIAL;
        return data.mode;
    }

    private Data getOrCreateData(ItemStack stack) {
        Data data = stack.get(TheElixirItemDataComponents.FURIOUS_COCKTAIL);
        if (data == null) data = stack.set(TheElixirItemDataComponents.FURIOUS_COCKTAIL, new Data(BENEFICIAL));
        return data;
    }

    public void setMode(ItemStack potion, Type mode) {
        getOrCreateData(potion).mode = mode;
    }

    public void switchMode(ItemStack potion) {
        setMode(potion, getMode(potion).next());
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        if (!context.getLevel().isClientSide) {
            ItemStack itemInHand = context.getItemInHand();
            switchMode(itemInHand);

            itemInHand.set(DataComponents.POTION_CONTENTS, new PotionContents(getMode(itemInHand).potion));

            if (!context.getPlayer().isCreative()) {
                itemInHand.shrink(1);
            }
        }

        return super.useOn(context);
    }

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public enum Type implements StringRepresentable {
        BENEFICIAL(TheElixirPotions.FURIOUS_COCKTAIL_BENEFICIAL),
        HARMFUL(TheElixirPotions.FURIOUS_COCKTAIL_HARMFUL),
        NEUTRAL(TheElixirPotions.FURIOUS_COCKTAIL_NEUTRAL),
        ALL(TheElixirPotions.FURIOUS_COCKTAIL_ALL);

        public static final StreamCodec<ByteBuf, Type> STREAM_CODEC =
                ByteBufCodecs.VAR_INT.map(i -> Type.values()[i], Type::ordinal);
        public static final Codec<Type> CODEC = StringRepresentable.fromEnum(Type::values);
        final Holder<Potion> potion;

        public Type next() {
            return switch (this) {
                case BENEFICIAL -> HARMFUL;
                case HARMFUL -> NEUTRAL;
                case NEUTRAL -> ALL;
                case ALL -> BENEFICIAL;
            };
        }

        @Override
        @NotNull
        public String getSerializedName() {
            return name();
        }
    }
}

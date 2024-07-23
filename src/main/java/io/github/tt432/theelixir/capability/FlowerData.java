package io.github.tt432.theelixir.capability;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

/**
 * @author TT432
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FlowerData implements SyncableData {
    public static final Codec<FlowerData> CODEC = RecordCodecBuilder.create(ins -> ins.group(
            Codec.BOOL.fieldOf("hasFlower").forGetter(o -> o.hasFlower),
            Codec.INT.fieldOf("flowerSpeed").forGetter(o -> o.flowerSpeed)
    ).apply(ins, FlowerData::new));

    public static final StreamCodec<ByteBuf, FlowerData> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL,
            FlowerData::isHasFlower,
            ByteBufCodecs.VAR_INT,
            FlowerData::getFlowerSpeed,
            FlowerData::new
    );

    // todo 以 curios 的信息设置该字段
    private boolean hasFlower;
    private int flowerSpeed = 6;
}

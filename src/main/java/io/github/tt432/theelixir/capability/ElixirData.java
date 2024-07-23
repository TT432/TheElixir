package io.github.tt432.theelixir.capability;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import lombok.*;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

/**
 * @author DustW
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class ElixirData implements SyncableData {
    public static final Codec<ElixirData> CODEC = RecordCodecBuilder.create(ins -> ins.group(
            Codec.BOOL.fieldOf("used_elixir").forGetter(o -> o.usedElixir)
    ).apply(ins, ElixirData::new));

    public static final StreamCodec<ByteBuf, ElixirData> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL,
            ElixirData::isUsedElixir,
            ElixirData::new
    );

    @Getter
    @Setter
    private boolean usedElixir;
}

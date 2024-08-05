package io.github.tt432.theelixir.capability;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

/**
 * @author TT432
 */
@NoArgsConstructor
public class FoxTailData implements SyncableData {
    public static final Codec<FoxTailData> CODEC = RecordCodecBuilder.create(ins -> ins.group(
            Codec.BOOL.fieldOf("has_fox").forGetter(o -> o.hasFox)
    ).apply(ins, FoxTailData::new));

    public static final StreamCodec<ByteBuf, FoxTailData> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL,
            FoxTailData::hasFox,
            FoxTailData::new
    );

    private FoxTailData(boolean hasFox) {
        this.hasFox = hasFox;
    }

    @Getter
    @Setter
    private boolean dirty = true;

    private boolean hasFox;

    public boolean hasFox() {
        return hasFox;
    }

    public void setHasFox(boolean hasFox) {
        dirty = true;
        this.hasFox = hasFox;
    }
}

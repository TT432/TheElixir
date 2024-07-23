package io.github.tt432.theelixir.capability;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

/**
 * @author TT432
 */
@AllArgsConstructor
@NoArgsConstructor
public class SkirtData implements SyncableData {
    public static final Codec<SkirtData> CODEC = RecordCodecBuilder.create(ins -> ins.group(
            Codec.BOOL.fieldOf("chest_skirt").forGetter(o -> o.chestSkirt),
            Codec.BOOL.fieldOf("normal_skirt").forGetter(o -> o.normalSkirt)
    ).apply(ins, SkirtData::new));

    public static final StreamCodec<ByteBuf, SkirtData> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL,
            SkirtData::isChestSkirt,
            ByteBufCodecs.BOOL,
            SkirtData::isNormalSkirt,
            SkirtData::new
    );

    private boolean chestSkirt = false;
    private boolean normalSkirt = false;

    // todo call the method need sync
    public void setChestSkirt(boolean chestSkirt) {
        this.chestSkirt = chestSkirt;
        this.normalSkirt = !chestSkirt;
    }

    // todo call the method need sync
    public void closeChestSkirt() {
        this.chestSkirt = false;
        this.normalSkirt = false;
    }

    public boolean isChestSkirt() {
        return chestSkirt;
    }

    public boolean isNormalSkirt() {
        return normalSkirt;
    }
}

package io.github.tt432.theelixir.network;

import io.github.tt432.theelixir.capability.*;
import io.github.tt432.theelixir.utils.ResourceLocations;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.attachment.AttachmentType;

/**
 * @author TT432
 */
public record TheElixirDataSyncPacketS2C<T extends SyncableData>(
        int entityId,
        T data
) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<TheElixirDataSyncPacketS2C<?>> TYPE =
            new Type<>(ResourceLocations.mod("the_elixir_data_sync"));

    public static final StreamCodec<ByteBuf, TheElixirDataSyncPacketS2C<?>> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT,
            TheElixirDataSyncPacketS2C::entityId,
            ByteBufCodecs.VAR_INT.dispatch(TheElixirDataSyncPacketS2C::innerId, TheElixirDataSyncPacketS2C::codec),
            TheElixirDataSyncPacketS2C::data,
            TheElixirDataSyncPacketS2C::new
    );

    private static <T extends SyncableData> int innerId(T data) {
        return switch (data) {
            case ElixirData ignored -> 0;
            case FlowerData ignored -> 1;
            case FoxTailData ignored -> 2;
            case SkirtData ignored -> 3;
            default -> throw new IllegalStateException("Unexpected value: " + data.getClass());
        };
    }

    @SuppressWarnings("unchecked")
    public <R extends AttachmentType<T>> R attachmentType() {
        return switch (data) {
            case ElixirData ignored -> (R) TheElixirAttachmentData.ELIXIR.get();
            case FlowerData ignored -> (R) TheElixirAttachmentData.FLOWER.get();
            case FoxTailData ignored -> (R) TheElixirAttachmentData.FOX_TAIL.get();
            case SkirtData ignored -> (R) TheElixirAttachmentData.SKIRT.get();
            default -> throw new IllegalStateException("Unexpected value: " + data.getClass());
        };
    }

    static StreamCodec<ByteBuf, ? extends SyncableData> codec(int idx) {
        return (switch (idx) {
            case 0 -> ElixirData.STREAM_CODEC;
            case 1 -> FlowerData.STREAM_CODEC;
            case 2 -> FoxTailData.STREAM_CODEC;
            case 3 -> SkirtData.STREAM_CODEC;
            default -> throw new IllegalStateException("Unexpected value: " + idx);
        });
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}

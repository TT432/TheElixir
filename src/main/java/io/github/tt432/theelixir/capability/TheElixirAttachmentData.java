package io.github.tt432.theelixir.capability;

import io.github.tt432.theelixir.TheElixir;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

/**
 * @author DustW
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TheElixirAttachmentData {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENTS =
            DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, TheElixir.MOD_ID);

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<ElixirData>> ELIXIR =
            ATTACHMENTS.register("elixir",
                    () -> AttachmentType.builder(ElixirData::new).serialize(ElixirData.CODEC).build());

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<FlowerData>> FLOWER =
            ATTACHMENTS.register("flower",
                    () -> AttachmentType.builder(FlowerData::new).serialize(FlowerData.CODEC).build());

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<FoxTailData>> FOX_TAIL =
            ATTACHMENTS.register("fox_tail",
                    () -> AttachmentType.builder(FoxTailData::new).serialize(FoxTailData.CODEC).build());

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<SkirtData>> SKIRT =
            ATTACHMENTS.register("skirt",
                    () -> AttachmentType.builder(SkirtData::new).serialize(SkirtData.CODEC).build());
}

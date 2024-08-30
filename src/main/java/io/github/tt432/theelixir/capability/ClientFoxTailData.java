package io.github.tt432.theelixir.capability;

import com.mojang.serialization.Codec;
import com.mojang.serialization.Decoder;
import com.mojang.serialization.Encoder;
import com.mojang.serialization.MapCodec;
import io.github.tt432.eyelib.capability.component.AnimationComponent;
import lombok.Getter;

/**
 * @author TT432
 */
@Getter
public class ClientFoxTailData {
    public static final Codec<ClientFoxTailData> CODEC =
            MapCodec.of(Encoder.empty(), Decoder.unit(ClientFoxTailData::new)).codec();

    AnimationComponent animationComponent = new AnimationComponent();
}

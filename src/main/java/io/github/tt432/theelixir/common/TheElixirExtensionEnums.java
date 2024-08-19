package io.github.tt432.theelixir.common;

import io.github.tt432.theelixir.client.AnimationArmPoseTransformer;
import net.minecraft.client.model.HumanoidModel;
import net.neoforged.fml.common.asm.enumextension.EnumProxy;

/**
 * @author TT432
 */
public class TheElixirExtensionEnums {
    public static final EnumProxy<HumanoidModel.ArmPose> APPLAUD_POSE = new EnumProxy<>(
            HumanoidModel.ArmPose.class, true, AnimationArmPoseTransformer.INSTANCE
    );
}

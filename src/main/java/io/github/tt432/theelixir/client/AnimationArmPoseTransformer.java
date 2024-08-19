package io.github.tt432.theelixir.client;

import io.github.tt432.eyelib.capability.RenderData;
import io.github.tt432.eyelib.capability.component.AnimationComponent;
import io.github.tt432.eyelib.client.ClientTickHandler;
import io.github.tt432.eyelib.client.animation.BrAnimator;
import io.github.tt432.eyelib.client.render.bone.BoneRenderInfoEntry;
import io.github.tt432.theelixir.TheElixir;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.client.IArmPoseTransformer;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author TT432
 */
public class AnimationArmPoseTransformer implements IArmPoseTransformer {
    public static final AnimationArmPoseTransformer INSTANCE = new AnimationArmPoseTransformer();

    private static final Map<UUID, AnimationComponent> components = new HashMap<>();

    @Override
    public void applyTransform(@NotNull HumanoidModel<?> model, @NotNull LivingEntity entity, @NotNull HumanoidArm arm) {
        ResourceLocation animLoc = ResourceLocation.fromNamespaceAndPath(TheElixir.MOD_ID, "applaud");
        AnimationComponent component = components.computeIfAbsent(entity.getUUID(), k -> new AnimationComponent());
        component.setup(animLoc, animLoc);
        var infos = BrAnimator.tickAnimation(component, RenderData.getComponent(entity).getScope(),
                ClientTickHandler.getTick() + Minecraft.getInstance().getTimer().getGameTimeDeltaPartialTick(false));

        BoneRenderInfoEntry entry = infos.get("left_arm");
        if (entry != null) setupArmAnim(model.leftArm, entry);

        entry = infos.get("right_arm");
        if (entry != null) setupArmAnim(model.rightArm, entry);
    }

    private static void setupArmAnim(ModelPart modelPart, BoneRenderInfoEntry entry) {
        PartPose initialPose = modelPart.getInitialPose();
        Vector3f position = entry.getRenderPosition().mul(16).add(initialPose.x, initialPose.y, initialPose.z);
        modelPart.setPos(position.x, position.y, position.z);
        Vector3f rotation = entry.getRenderRotation().mul(-1, -1, 1).add(initialPose.xRot, initialPose.yRot, initialPose.zRot);
        modelPart.setRotation(rotation.x, rotation.y, rotation.z);
    }
}

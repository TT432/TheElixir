package io.github.tt432.theelixir.client;

import com.mojang.blaze3d.vertex.PoseStack;
import io.github.tt432.eyelib.capability.RenderData;
import io.github.tt432.eyelib.capability.component.AnimationComponent;
import io.github.tt432.eyelib.client.ClientTickHandler;
import io.github.tt432.eyelib.client.animation.BrAnimator;
import io.github.tt432.eyelib.client.loader.BrModelLoader;
import io.github.tt432.eyelib.client.render.BrModelTextures;
import io.github.tt432.eyelib.client.render.RenderParams;
import io.github.tt432.eyelib.client.render.renderer.BrModelRenderer;
import io.github.tt432.eyelib.client.render.visitor.BuiltInBrModelRenderVisitors;
import io.github.tt432.eyelib.client.render.visitor.ModelRenderVisitorList;
import io.github.tt432.eyelib.util.math.EyeMath;
import io.github.tt432.theelixir.capability.TheElixirAttachmentData;
import io.github.tt432.theelixir.utils.ResourceLocations;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix4f;

import java.util.HashMap;
import java.util.List;

/**
 * @author TT432
 */
public class FoxTailRenderLayer extends RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {
    private static final ResourceLocation FOX_TAIL_TEXTURE = ResourceLocations.mod("textures/entity/fox_tail.png");
    private static final ResourceLocation FOX_TAIL_MODEL = ResourceLocations.mod("fox_tail");

    public FoxTailRenderLayer(RenderLayerParent<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> pRenderer) {
        super(pRenderer);
    }

    @Override
    public void render(PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, AbstractClientPlayer pLivingEntity,
                       float pLimbSwing, float pLimbSwingAmount, float pPartialTick, float pAgeInTicks,
                       float pNetHeadYaw, float pHeadPitch) {
        if (!pLivingEntity.getData(TheElixirAttachmentData.FOX_TAIL).hasFox()) return;

        pPoseStack.pushPose();
        getParentModel().body.translateAndRotate(pPoseStack);

        Matrix4f pose = pPoseStack.last().pose();
        float ang = 180 * EyeMath.DEGREES_TO_RADIANS;
        pose.rotateX(ang);
        pPoseStack.last().normal().rotateX(ang);

        pose.translate(0, -12 / 16F, 0);

        AnimationComponent animationComponent = RenderData.getComponent(pLivingEntity).getAnimationComponent();
        animationComponent.setup(FOX_TAIL_MODEL, FOX_TAIL_MODEL);
        var infos = BrAnimator.tickAnimation(animationComponent,
                RenderData.getComponent(pLivingEntity).getScope(), ClientTickHandler.getTick() + pPartialTick);

        RenderType renderType = RenderType.entitySolid(FOX_TAIL_TEXTURE);
        BrModelRenderer.render(new RenderParams(
                        pLivingEntity,
                        pPoseStack.last().copy(),
                        pPoseStack,
                        renderType,
                        pBuffer.getBuffer(renderType),
                        pPackedLight,
                        LivingEntityRenderer.getOverlayCoords(pLivingEntity, 0)
                ), BrModelLoader.getModel(FOX_TAIL_MODEL), infos,
                new BrModelTextures.TwoSideInfoMap(new HashMap<>()),
                new ModelRenderVisitorList(List.of(BuiltInBrModelRenderVisitors.BLANK.get())));
        pPoseStack.popPose();
    }

    @Override
    protected ResourceLocation getTextureLocation(AbstractClientPlayer pEntity) {
        return FOX_TAIL_TEXTURE;
    }
}

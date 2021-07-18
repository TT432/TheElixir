package com.nmmoc7.theelixir.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.nmmoc7.theelixir.capability.CapabilityRegistryHandler;
import com.nmmoc7.theelixir.capability.TheElixirCapability;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.FoxRenderer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.FoxModel;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.vector.Vector3f;

/**
 * @author DustW
 */
public class FoxLayerRender extends LayerRenderer<PlayerEntity, PlayerModel<PlayerEntity>> {
    FoxModel<FoxEntity> foxModel = new FoxModel<>();

    public FoxLayerRender(IEntityRenderer<PlayerEntity, PlayerModel<PlayerEntity>> entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, PlayerEntity player, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        TheElixirCapability cap = player.getCapability(CapabilityRegistryHandler.THE_ELIXIR_CAPABILITY).orElse(null);

        if (cap.isHasFoxTail()) {
            ModelRenderer tail = foxModel.tail;
            ModelRenderer leftEar = foxModel.leftEar;
            ModelRenderer rightEar = foxModel.rightEar;

            Minecraft mc = Minecraft.getInstance();
            FoxRenderer foxRender = new FoxRenderer(mc.getRenderManager());
            FoxEntity foxEntity = new FoxEntity(EntityType.FOX, mc.world);
            RenderType type = new FoxModel<>().getRenderType(foxRender.getEntityTexture(foxEntity));
            IVertexBuilder buffer = mc.getRenderTypeBuffers().getBufferSource().getBuffer(type);

            if (tail.showModel) {
                matrixStackIn.push();
                if (player.isCrouching()) {
                    matrixStackIn.rotate(Vector3f.XP.rotation(getEntityModel().bipedBody.rotateAngleX));
                    matrixStackIn.rotate(Vector3f.XP.rotationDegrees(55));
                    matrixStackIn.translate(0, -8 / 16F, -13 / 16F);
                }
                else {
                    matrixStackIn.rotate(Vector3f.XN.rotationDegrees(-55));
                    matrixStackIn.translate(0, -9 / 16F, -9 / 16F);
                }
                tail.render(matrixStackIn, buffer, packedLightIn, OverlayTexture.NO_OVERLAY);
                matrixStackIn.pop();
            }

            if (leftEar.showModel && rightEar.showModel) {
                matrixStackIn.push();
                if (player.isCrouching()) {
                    matrixStackIn.translate(0, 4 / 16F, 0);
                }
                matrixStackIn.rotate(Vector3f.YP.rotationDegrees(netHeadYaw));
                matrixStackIn.rotate(Vector3f.XP.rotationDegrees(headPitch));
                matrixStackIn.translate(-1 / 16F, -6 / 16F, 1 / 16F);
                leftEar.render(matrixStackIn, buffer, packedLightIn, OverlayTexture.NO_OVERLAY);
                rightEar.render(matrixStackIn, buffer, packedLightIn, OverlayTexture.NO_OVERLAY);
                matrixStackIn.pop();
            }
        }
    }
}

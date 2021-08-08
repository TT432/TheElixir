package com.nmmoc7.theelixir.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.nmmoc7.theelixir.TheElixir;
import com.nmmoc7.theelixir.capability.CapabilityRegistryHandler;
import com.nmmoc7.theelixir.capability.TheElixirCapability;
import com.nmmoc7.theelixir.model.BlueJK;
import com.nmmoc7.theelixir.model.ClothesModel;
import com.nmmoc7.theelixir.model.ClothesModelChest;
import com.nmmoc7.theelixir.model.Skirt;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeBuffers;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;

/**
 * @author DustW
 */
public class ClothesLayerRender extends LayerRenderer<PlayerEntity, PlayerModel<PlayerEntity>> {
    public static final ResourceLocation CLOTHES = new ResourceLocation(TheElixir.MOD_ID, "textures/entity/clothes.png");
    public static final ResourceLocation CLOTHES_2 = new ResourceLocation(TheElixir.MOD_ID, "textures/entity/clothes2.png");
    public static final ResourceLocation SKIRT = new ResourceLocation(TheElixir.MOD_ID, "textures/entity/skirt.png");
    public static final ResourceLocation BLUE_TEXTURE = new ResourceLocation(TheElixir.MOD_ID, "textures/entity/jk.png");

    public static final ClothesModel CLOTHES_MODEL = new ClothesModel();
    public static final ClothesModelChest CLOTHES_MODEL_CHEST = new ClothesModelChest();
    public static final Skirt SKIRT_MODEL = new Skirt();
    public static final BlueJK BLUE_JK = new BlueJK();

    public ClothesLayerRender(IEntityRenderer<PlayerEntity, PlayerModel<PlayerEntity>> entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, PlayerEntity player, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        TheElixirCapability cap = player.getCapability(CapabilityRegistryHandler.THE_ELIXIR_CAPABILITY).orElse(null);

        if (cap == null) { return; }

        if (cap.isChestSkirt()) {
            renderChest(matrixStackIn, packedLightIn);
            renderSkirt(matrixStackIn, packedLightIn);
        }
        else if (cap.isNormalSkirt()) {
            if ((getEntityModel()).smallArms) {
                renderJK(matrixStackIn, packedLightIn);
            }
            else {
                renderNormal(matrixStackIn, packedLightIn);
                renderSkirt(matrixStackIn, packedLightIn);
            }
        }
    }

    public void renderJK(MatrixStack matrixStackIn, int packedLightIn) {
        RenderType renderType = BLUE_JK.getRenderType(BLUE_TEXTURE);
        Minecraft mc = Minecraft.getInstance();
        RenderTypeBuffers renderBuffers = mc.getRenderTypeBuffers();
        IVertexBuilder vertexBuilder = renderBuffers.getBufferSource().getBuffer(renderType);

        PlayerModel<PlayerEntity> playerModel = getEntityModel();
        ModelRenderer leftArmModel = playerModel.bipedLeftArm;
        ModelRenderer rightArmModel = playerModel.bipedRightArm;
        ModelRenderer bodyModel = playerModel.bipedBody;
        ModelRenderer leftLegModel = playerModel.bipedLeftLeg;
        ModelRenderer rightLegModel = playerModel.bipedRightLeg;
        ModelRenderer headModel = playerModel.bipedHead;

        BLUE_JK.bipedBody.copyModelAngles(bodyModel);
        BLUE_JK.bipedLeftArm.copyModelAngles(rightArmModel);
        BLUE_JK.bipedRightArm.copyModelAngles(leftArmModel);
        BLUE_JK.bipedLeftLeg.copyModelAngles(leftLegModel);
        BLUE_JK.bipedRightLeg.copyModelAngles(rightLegModel);
        BLUE_JK.bipedHead.copyModelAngles(headModel);

        BLUE_JK.render(matrixStackIn, vertexBuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
    }

    public void renderSkirt(MatrixStack matrixStackIn, int packedLightIn) {
        RenderType renderType = SKIRT_MODEL.getRenderType(SKIRT);
        Minecraft mc = Minecraft.getInstance();
        RenderTypeBuffers renderBuffers = mc.getRenderTypeBuffers();
        IVertexBuilder vertexBuilder = renderBuffers.getBufferSource().getBuffer(renderType);

        PlayerModel<PlayerEntity> playerModel = getEntityModel();
        ModelRenderer bodyModel = playerModel.bipedBody;

        ModelUtils.copyRotate(SKIRT_MODEL.bipedBody, bodyModel);

        matrixStackIn.push();
        matrixStackIn.translate(0, 1 / 16f, 0);

        SKIRT_MODEL.render(matrixStackIn, vertexBuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
        matrixStackIn.pop();
    }

    public void renderNormal(MatrixStack matrixStackIn, int packedLightIn) {
        RenderType renderType = CLOTHES_MODEL.getRenderType(CLOTHES);
        Minecraft mc = Minecraft.getInstance();
        RenderTypeBuffers renderBuffers = mc.getRenderTypeBuffers();
        IVertexBuilder vertexBuilder = renderBuffers.getBufferSource().getBuffer(renderType);

        PlayerModel<PlayerEntity> playerModel = getEntityModel();
        ModelRenderer leftArmModel = playerModel.bipedLeftArm;
        ModelRenderer rightArmModel = playerModel.bipedRightArm;
        ModelRenderer bodyModel = playerModel.bipedBody;

        CLOTHES_MODEL.bipedBody.copyModelAngles(bodyModel);
        CLOTHES_MODEL.bipedLeftArm.copyModelAngles(rightArmModel);
        CLOTHES_MODEL.bipedRightArm.copyModelAngles(leftArmModel);

        CLOTHES_MODEL.render(matrixStackIn, vertexBuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
    }

    public void renderChest(MatrixStack matrixStackIn, int packedLightIn) {
        RenderType renderType = CLOTHES_MODEL_CHEST.getRenderType(CLOTHES_2);
        Minecraft mc = Minecraft.getInstance();
        RenderTypeBuffers renderBuffers = mc.getRenderTypeBuffers();
        IVertexBuilder vertexBuilder = renderBuffers.getBufferSource().getBuffer(renderType);

        PlayerModel<PlayerEntity> playerModel = getEntityModel();
        ModelRenderer leftArmModel = playerModel.bipedLeftArm;
        ModelRenderer rightArmModel = playerModel.bipedRightArm;
        ModelRenderer bodyModel = playerModel.bipedBody;

        CLOTHES_MODEL_CHEST.bipedBody.copyModelAngles(bodyModel);
        CLOTHES_MODEL_CHEST.bipedLeftArm.copyModelAngles(rightArmModel);
        CLOTHES_MODEL_CHEST.bipedRightArm.copyModelAngles(leftArmModel);
        CLOTHES_MODEL_CHEST.render(matrixStackIn, vertexBuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
    }
}

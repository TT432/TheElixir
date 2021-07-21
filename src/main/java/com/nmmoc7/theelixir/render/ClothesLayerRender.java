package com.nmmoc7.theelixir.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.nmmoc7.theelixir.TheElixir;
import com.nmmoc7.theelixir.capability.CapabilityRegistryHandler;
import com.nmmoc7.theelixir.capability.TheElixirCapability;
import com.nmmoc7.theelixir.model.ClothesModel;
import com.nmmoc7.theelixir.model.ClothesModelChest;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeBuffers;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;

/**
 * @author DustW
 */
public class ClothesLayerRender extends LayerRenderer<PlayerEntity, PlayerModel<PlayerEntity>> {
    public static final ResourceLocation CLOTHES = new ResourceLocation(TheElixir.MOD_ID, "textures/entity/clothes.png");
    public static final ResourceLocation CLOTHES_2 = new ResourceLocation(TheElixir.MOD_ID, "textures/entity/clothes2.png");
    public static final ResourceLocation SKIRT = new ResourceLocation(TheElixir.MOD_ID, "textures/entity/skirt");

    public static final ClothesModel CLOTHES_MODEL = new ClothesModel();
    public static final ClothesModelChest CLOTHES_MODEL_CHEST = new ClothesModelChest();

    public ClothesLayerRender(IEntityRenderer<PlayerEntity, PlayerModel<PlayerEntity>> entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, PlayerEntity player, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        TheElixirCapability cap = player.getCapability(CapabilityRegistryHandler.THE_ELIXIR_CAPABILITY).orElse(null);

        if (cap != null && cap.isHasFlower()) {
            RenderType renderType = CLOTHES_MODEL.getRenderType(CLOTHES);
            Minecraft mc = Minecraft.getInstance();
            RenderTypeBuffers renderBuffers = mc.getRenderTypeBuffers();
            IVertexBuilder vertexBuilder = renderBuffers.getBufferSource().getBuffer(renderType);

            PlayerModel<PlayerEntity> playerModel = getEntityModel();
            ModelRenderer leftArmModel = playerModel.bipedLeftArm;
            ModelRenderer rightArmModel = playerModel.bipedRightArm;
            ModelRenderer bodyModel = playerModel.bipedBody;

            CLOTHES_MODEL.body.copyModelAngles(bodyModel);
            CLOTHES_MODEL.leftArmIn.copyModelAngles(leftArmModel);
            CLOTHES_MODEL.rightArmIn.copyModelAngles(rightArmModel);

            CLOTHES_MODEL.render(matrixStackIn, vertexBuilder, packedLightIn, packedLightIn, 1, 1, 1, 1);
        }
    }
}

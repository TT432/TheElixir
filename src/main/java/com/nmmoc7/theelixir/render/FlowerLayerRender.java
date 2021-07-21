package com.nmmoc7.theelixir.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.nmmoc7.theelixir.TheElixir;
import com.nmmoc7.theelixir.capability.CapabilityRegistryHandler;
import com.nmmoc7.theelixir.capability.TheElixirCapability;
import com.nmmoc7.theelixir.model.ZZZZFlower;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeBuffers;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;

/**
 * @author DustW
 */
public class FlowerLayerRender extends LayerRenderer<PlayerEntity, PlayerModel<PlayerEntity>>  {
    public static final ResourceLocation ZZZZ_FLOWER_TEXTURE = new ResourceLocation(TheElixir.MOD_ID, "textures/entity/zzzzflower.png");
    ZZZZFlower zzzzFlower = new ZZZZFlower();

    public FlowerLayerRender(IEntityRenderer<PlayerEntity, PlayerModel<PlayerEntity>> entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, PlayerEntity player, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        TheElixirCapability cap = player.getCapability(CapabilityRegistryHandler.THE_ELIXIR_CAPABILITY).orElse(null);

        if (cap != null && cap.isHasFlower()) {
            RenderType renderType = zzzzFlower.getRenderType(ZZZZ_FLOWER_TEXTURE);
            Minecraft mc = Minecraft.getInstance();
            RenderTypeBuffers renderBuffers = mc.getRenderTypeBuffers();
            IVertexBuilder vertexBuilder = renderBuffers.getBufferSource().getBuffer(renderType);
            matrixStackIn.push();
            matrixStackIn.translate(0, -1, 0.5);
            zzzzFlower.setLivingAnimations(player, limbSwing, limbSwingAmount, partialTicks);
            zzzzFlower.render(matrixStackIn, vertexBuilder, packedLightIn, packedLightIn, 1, 1, 1, 1);
            matrixStackIn.pop();
        }
    }
}

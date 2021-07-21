package com.nmmoc7.theelixir.model;// Made with Blockbench 3.9.2
// Exported for Minecraft version 1.15 - 1.16 with MCP mappings
// Paste this class into your mod and generate all required imports

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.nmmoc7.theelixir.render.ModelUtils;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.player.PlayerEntity;
public class ClothesModel extends BipedModel<PlayerEntity> {
	private final ModelRenderer body;
	private final ModelRenderer bowtie;
	private final ModelRenderer bone3;
	private final ModelRenderer bone4;
	private final ModelRenderer bone5;
	private final ModelRenderer rightArm;
	private final ModelRenderer rightArmIn;
	private final ModelRenderer leftArm;
	private final ModelRenderer leftArmIn;

	public ClothesModel() {
		super(RenderType::getEntityTranslucent, 0, 0.0F, 32, 32);
		 // 清理Biped骨架确保后续加载时骨架为空。
        this.bipedBody = new ModelRenderer(this);
        this.bipedLeftArm = new ModelRenderer(this);
        this.bipedRightArm = new ModelRenderer(this);

		body = new ModelRenderer(this);
		body.setRotationPoint(0.0F, 9.0F, -0.1F);
		body.setTextureOffset(0, 0).addBox(-4.0F, -9.0F, -2.0F, 8.0F, 9.0F, 4.0F, 0.2F, false);

		bowtie = new ModelRenderer(this);
		bowtie.setRotationPoint(-0.2F, 15.0F, 0.0F);
		body.addChild(bowtie);

		bone3 = new ModelRenderer(this);
		bone3.setRotationPoint(-1.0F, -20.0F, 8.0F);
		bowtie.addChild(bone3);
		setRotationAngle(bone3, 0.0F, 0.0F, 0.7854F);
		bone3.setTextureOffset(20, 0).addBox(-4.0F, -2.0F, -10.5F, 4.0F, 2.0F, 0.0F, 0.2F, false);
		bone3.setTextureOffset(0, 2).addBox(1.6569F, -2.0F, -10.5F, 2.0F, 2.0F, 0.0F, 0.2F, false);

		bone4 = new ModelRenderer(this);
		bone4.setRotationPoint(2.0F, -21.0F, 2.0F);
		bowtie.addChild(bone4);
		setRotationAngle(bone4, 0.0F, 0.0F, -0.7854F);
		bone4.setTextureOffset(12, 15).addBox(-1.2929F, -1.7071F, -4.4F, 4.0F, 2.0F, 0.0F, 0.2F, false);
		bone4.setTextureOffset(0, 0).addBox(-5.0F, -1.7071F, -4.4F, 2.0F, 2.0F, 0.0F, 0.2F, false);

		bone5 = new ModelRenderer(this);
		bone5.setRotationPoint(0.0F, 0.0F, 0.0F);
		bowtie.addChild(bone5);
		bone5.setTextureOffset(12, 13).addBox(-2.0F, -21.0F, -2.51F, 4.0F, 2.0F, 0.0F, 0.2F, false);

		rightArm = new ModelRenderer(this);
		rightArm.setRotationPoint(5.0F, 8.0F, -0.1F);
		
		rightArmIn = new ModelRenderer(this);
		rightArmIn.setRotationPoint(-5.0F, 18.0F, 0.0F);
		rightArm.addChild(rightArmIn);
		rightArmIn.setTextureOffset(16, 16).addBox(4.0F, -25.9F, -2.0F, 4.0F, 11.0F, 4.0F, 0.4F, false);

		leftArm = new ModelRenderer(this);
		leftArm.setRotationPoint(-4.6F, 8.0F, -0.1F);
		

		leftArmIn = new ModelRenderer(this);
		leftArmIn.setRotationPoint(5.0F, 18.0F, 0.0F);
		leftArm.addChild(leftArmIn);
		leftArmIn.setTextureOffset(0, 13).addBox(-8.4F, -25.9F, -2.0F, 4.0F, 11.0F, 4.0F, 0.4F, false);

		ModelUtils.addClothes(this, body, leftArm, rightArm);
	}

	@Override
	public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
		this.bipedBody.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, 1, 1, 1, 1);
		this.bipedLeftArm.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, 1, 1, 1, 1);
		this.bipedRightArm.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, 1, 1, 1, 1);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}
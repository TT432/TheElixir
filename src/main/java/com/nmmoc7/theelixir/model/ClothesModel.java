package com.nmmoc7.theelixir.model;// Made with Blockbench 3.9.2
// Exported for Minecraft version 1.15 - 1.16 with MCP mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ClothesModel extends EntityModel<Entity> {
	public final ModelRenderer bone2;
	public final ModelRenderer leftArm;
	public final ModelRenderer leftArmIn;
	public final ModelRenderer rightArm;
	public final ModelRenderer rightArmIn;
	public final ModelRenderer body;
	public final ModelRenderer bowtie;
	public final ModelRenderer bone3;
	public final ModelRenderer bone4;
	public final ModelRenderer bone5;

	public ClothesModel() {
		textureWidth = 32;
		textureHeight = 32;

		bone2 = new ModelRenderer(this);
		bone2.setRotationPoint(0.0F, 9.0F, -0.1F);
		

		leftArm = new ModelRenderer(this);
		leftArm.setRotationPoint(-5.0F, -1.0F, 0.0F);
		bone2.addChild(leftArm);
		

		leftArmIn = new ModelRenderer(this);
		leftArmIn.setRotationPoint(5.0F, 18.0F, 0.0F);
		leftArm.addChild(leftArmIn);
		leftArmIn.setTextureOffset(0, 13).addBox(-8.4F, -20.0F, -2.0F, 4.0F, 11.0F, 4.0F, 0.2F, false);

		rightArm = new ModelRenderer(this);
		rightArm.setRotationPoint(5.0F, -1.0F, 0.0F);
		bone2.addChild(rightArm);
		

		rightArmIn = new ModelRenderer(this);
		rightArmIn.setRotationPoint(-5.0F, 18.0F, 0.0F);
		rightArm.addChild(rightArmIn);
		rightArmIn.setTextureOffset(16, 16).addBox(4.4F, -20.0F, -2.0F, 4.0F, 11.0F, 4.0F, 0.2F, false);

		body = new ModelRenderer(this);
		body.setRotationPoint(0.0F, 0.0F, 0.0F);
		bone2.addChild(body);
		body.setTextureOffset(0, 0).addBox(-4.0F, -9.0F, -2.0F, 8.0F, 9.0F, 4.0F, 0.2F, false);

		bowtie = new ModelRenderer(this);
		bowtie.setRotationPoint(0.0F, 15.0F, 0.0F);
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
		bone4.setTextureOffset(12, 15).addBox(-1.2929F, -1.7071F, -4.5F, 4.0F, 2.0F, 0.0F, 0.2F, false);
		bone4.setTextureOffset(0, 0).addBox(-5.0F, -1.7071F, -4.5F, 2.0F, 2.0F, 0.0F, 0.2F, false);

		bone5 = new ModelRenderer(this);
		bone5.setRotationPoint(0.0F, 0.0F, 0.0F);
		bowtie.addChild(bone5);
		bone5.setTextureOffset(12, 13).addBox(-2.0F, -21.0F, -2.51F, 4.0F, 2.0F, 0.0F, 0.2F, false);
	}

	@Override
	public void setRotationAngles(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
		//previously the render function, render code was moved to a method below
	}

	@Override
	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		bone2.render(matrixStack, buffer, packedLight, packedOverlay);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}
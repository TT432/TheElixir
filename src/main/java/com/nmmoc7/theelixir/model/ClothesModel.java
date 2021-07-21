package com.nmmoc7.theelixir.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.nmmoc7.theelixir.render.ModelUtils;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.Entity;
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
		rightArmIn.setTextureOffset(16, 16).addBox(4.4F, -26.0F, -2.0F, 4.0F, 11.0F, 4.0F, 0.2F, false);

		leftArm = new ModelRenderer(this);
		leftArm.setRotationPoint(-5.0F, 8.0F, -0.1F);

		leftArmIn = new ModelRenderer(this);
		leftArmIn.setRotationPoint(5.0F, 18.0F, 0.0F);
		leftArm.addChild(leftArmIn);
		leftArmIn.setTextureOffset(0, 13).addBox(-8.4F, -26.0F, -2.0F, 4.0F, 11.0F, 4.0F, 0.2F, false);

		this.bipedBody.addChild(body);
		this.bipedRightArm.addChild(rightArm);
		this.bipedLeftArm.addChild(leftArm);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}
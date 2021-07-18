package com.nmmoc7.theelixir.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * @author DustW, MaleyP
 * 感谢马雷的大力资瓷
 */
public class ZZZZFlower extends EntityModel<Entity> {
	private final ModelRenderer shell;
	private final ModelRenderer stand;
	private final ModelRenderer headmain;
	private final ModelRenderer headrotate;
	private final ModelRenderer head1;
	private final ModelRenderer bone;
	private final ModelRenderer bone2;
	private final ModelRenderer bone3;
	private final ModelRenderer bone9;
	private final ModelRenderer bone10;
	private final ModelRenderer bone11;
	private final ModelRenderer bone12;
	private final ModelRenderer bone13;
	private final ModelRenderer bone14;
	private final ModelRenderer bone15;
	private final ModelRenderer bone16;
	private final ModelRenderer bone17;
	private final ModelRenderer head2;
	private final ModelRenderer bone4;
	private final ModelRenderer bone5;
	private final ModelRenderer bone6;

	public ZZZZFlower() {
		textureWidth = 64;
		textureHeight = 64;

		shell = new ModelRenderer(this);
		shell.setRotationPoint(0.0F, 24.0F, 0.0F);
		

		stand = new ModelRenderer(this);
		stand.setRotationPoint(0.0F, 0.0F, 0.0F);
		shell.addChild(stand);
		stand.setTextureOffset(29, 22).addBox(-1.0F, -16.0F, -1.0F, 2.0F, 16.0F, 2.0F, 0.0F, false);

		headmain = new ModelRenderer(this);
		headmain.setRotationPoint(0.0F, -16.0F, 0.0F);
		shell.addChild(headmain);
		setRotationAngle(headmain, -0.5236F, 0.0F, 0.0F);
		

		headrotate = new ModelRenderer(this);
		headrotate.setRotationPoint(0.0F, 16.0F, 0.0F);
		headmain.addChild(headrotate);
		

		head1 = new ModelRenderer(this);
		head1.setRotationPoint(0.0F, -16.0F, 0.0F);
		headrotate.addChild(head1);
		

		bone = new ModelRenderer(this);
		bone.setRotationPoint(0.0F, 0.0F, -0.5F);
		head1.addChild(bone);
		

		bone2 = new ModelRenderer(this);
		bone2.setRotationPoint(0.0F, 0.0F, 0.0F);
		bone.addChild(bone2);
		setRotationAngle(bone2, -0.0436F, 0.0F, 0.0F);
		bone2.setTextureOffset(0, 46).addBox(-7.0F, -4.0F, 0.0F, 7.0F, 8.0F, 1.0F, 0.0F, false);

		bone3 = new ModelRenderer(this);
		bone3.setRotationPoint(0.0F, 0.0F, -0.5F);
		head1.addChild(bone3);
		setRotationAngle(bone3, 0.0F, 0.0F, 1.0472F);
		

		bone9 = new ModelRenderer(this);
		bone9.setRotationPoint(0.0F, 0.0F, 0.0F);
		bone3.addChild(bone9);
		setRotationAngle(bone9, -0.0436F, 0.0F, 0.0F);
		bone9.setTextureOffset(34, 42).addBox(-7.0F, -4.0F, 0.0F, 7.0F, 8.0F, 1.0F, 0.0F, false);

		bone10 = new ModelRenderer(this);
		bone10.setRotationPoint(0.0F, 0.0F, -0.5F);
		head1.addChild(bone10);
		setRotationAngle(bone10, 0.0F, 0.0F, 2.0944F);
		

		bone11 = new ModelRenderer(this);
		bone11.setRotationPoint(0.0F, 0.0F, 0.0F);
		bone10.addChild(bone11);
		setRotationAngle(bone11, -0.0436F, 0.0F, 0.0F);
		bone11.setTextureOffset(17, 41).addBox(-7.0F, -4.0F, 0.0F, 7.0F, 8.0F, 1.0F, 0.0F, false);

		bone12 = new ModelRenderer(this);
		bone12.setRotationPoint(0.0F, 0.0F, -0.5F);
		head1.addChild(bone12);
		setRotationAngle(bone12, 0.0F, 0.0F, -3.1416F);
		

		bone13 = new ModelRenderer(this);
		bone13.setRotationPoint(0.0F, 0.0F, 0.0F);
		bone12.addChild(bone13);
		setRotationAngle(bone13, -0.0436F, 0.0F, 0.0F);
		bone13.setTextureOffset(38, 32).addBox(-7.0F, -4.0F, 0.0F, 7.0F, 8.0F, 1.0F, 0.0F, false);

		bone14 = new ModelRenderer(this);
		bone14.setRotationPoint(0.0F, 0.0F, -0.5F);
		head1.addChild(bone14);
		setRotationAngle(bone14, 0.0F, 0.0F, -2.0944F);
		

		bone15 = new ModelRenderer(this);
		bone15.setRotationPoint(0.0F, 0.0F, 0.0F);
		bone14.addChild(bone15);
		setRotationAngle(bone15, -0.0436F, 0.0F, 0.0F);
		bone15.setTextureOffset(38, 22).addBox(-7.0F, -4.0F, 0.0F, 7.0F, 8.0F, 1.0F, 0.0F, false);

		bone16 = new ModelRenderer(this);
		bone16.setRotationPoint(0.0F, 0.0F, -0.5F);
		head1.addChild(bone16);
		setRotationAngle(bone16, 0.0F, 0.0F, -1.0472F);
		

		bone17 = new ModelRenderer(this);
		bone17.setRotationPoint(0.0F, 0.0F, 0.0F);
		bone16.addChild(bone17);
		setRotationAngle(bone17, -0.0436F, 0.0F, 0.0F);
		bone17.setTextureOffset(0, 36).addBox(-7.0F, -4.0F, 0.0F, 7.0F, 8.0F, 1.0F, 0.0F, false);

		head2 = new ModelRenderer(this);
		head2.setRotationPoint(0.0F, -15.5F, 0.75F);
		headrotate.addChild(head2);
		

		bone4 = new ModelRenderer(this);
		bone4.setRotationPoint(0.0F, 0.0F, -0.5F);
		head2.addChild(bone4);
		bone4.setTextureOffset(0, 24).addBox(-5.0F, -3.5F, -3.05F, 10.0F, 7.0F, 4.0F, -1.5F, false);

		bone5 = new ModelRenderer(this);
		bone5.setRotationPoint(0.0F, 0.0F, -0.5F);
		head2.addChild(bone5);
		setRotationAngle(bone5, 0.0F, 0.0F, 2.0944F);
		bone5.setTextureOffset(0, 12).addBox(-5.0F, -3.5F, -2.95F, 10.0F, 7.0F, 4.0F, -1.5F, false);

		bone6 = new ModelRenderer(this);
		bone6.setRotationPoint(0.0F, 0.0F, -0.5F);
		head2.addChild(bone6);
		setRotationAngle(bone6, 0.0F, 0.0F, -2.0944F);
		bone6.setTextureOffset(0, 0).addBox(-5.0F, -3.5F, -3.0F, 10.0F, 7.0F, 4.0F, -1.5F, false);
	}

	@Override
	public void setRotationAngles(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
		//previously the render function, render code was moved to a method below
	}

	@Override
	public void setLivingAnimations(Entity entityIn, float limbSwing, float limbSwingAmount, float partialTick) {
		head1.rotateAngleZ -= partialTick * Math.toRadians(6);
		head2.rotateAngleZ -= partialTick * Math.toRadians(6);
	}

	@Override
	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		shell.render(matrixStack, buffer, packedLight, packedOverlay);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}
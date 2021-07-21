package com.nmmoc7.theelixir.render;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.player.PlayerEntity;

/**
 * @author DustW
 */
public class ModelUtils {
    public static void clearModel(BipedModel<?> model) {
        model.bipedHead = new ModelRenderer(model);
        model.bipedHeadwear = new ModelRenderer(model);
        model.bipedBody = new ModelRenderer(model);
        model.bipedRightArm = new ModelRenderer(model);
        model.bipedLeftArm = new ModelRenderer(model);
        model.bipedRightLeg = new ModelRenderer(model);
        model.bipedLeftLeg = new ModelRenderer(model);
    }

    public static void addClothes(BipedModel<?> model, ModelRenderer body, ModelRenderer leftArm, ModelRenderer rightArm) {
        model.bipedBody.addChild(body);
        model.bipedLeftArm.addChild(leftArm);
        model.bipedRightArm.addChild(rightArm);
    }

    public static void copyRotate(ModelRenderer model1, ModelRenderer model2) {
        model1.rotateAngleX = model2.rotateAngleX;
        model1.rotateAngleY = model2.rotateAngleY;
        model1.rotateAngleZ = model2.rotateAngleZ;
    }
}

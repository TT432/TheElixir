package com.nmmoc7.theelixir.ister;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.nmmoc7.theelixir.RegistryHandler;
import com.nmmoc7.theelixir.item.SuperFood;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.vector.Vector3f;

/**
 * @author DustW
 */
public class SuperFoodItemStackRender extends ItemStackTileEntityRenderer {
    @Override
    public void func_239207_a_(ItemStack stack, ItemCameraTransforms.TransformType transformType, MatrixStack matrixStack, IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay) {
        Item item = stack.getItem();

        if (item == RegistryHandler.SUPER_FOOD) {
            ItemStack foodStack = SuperFood.getItemStack(stack);

            if (foodStack.getItem() != RegistryHandler.SUPER_FOOD) {
                ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
                IBakedModel ibakedmodel = itemRenderer.getItemModelWithOverrides(foodStack, null, null);
                matrixStack.push();
                matrixStack.translate(0.5F, 0.5F, 0.5F);
                itemRenderer.renderItem(foodStack, transformType, false, matrixStack, buffer, combinedLight, combinedOverlay, ibakedmodel.getBakedModel());
                matrixStack.pop();
            }
        }
    }
}

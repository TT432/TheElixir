package com.nmmoc7.theelixir.event;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

/**
 * @author DustW
 */
public class RendererModelEvent extends Event {
    @Cancelable
    public static class Pre extends RendererModelEvent {
        ModelRenderer modelRenderer;
        MatrixStack matrixStackIn;
        IVertexBuilder bufferIn;
        int packedLightIn;
        int packedOverlayIn;
        float red;
        float green;
        float blue;
        float alpha;

        public Pre(ModelRenderer modelRenderer, MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
            this.modelRenderer = modelRenderer;
            this.matrixStackIn = matrixStackIn;
            this.bufferIn = bufferIn;
            this.packedLightIn = packedLightIn;
            this.packedOverlayIn = packedOverlayIn;
            this.red = red;
            this.green = green;
            this.blue = blue;
            this.alpha = alpha;
        }

        public float getAlpha() {
            return alpha;
        }

        public float getBlue() {
            return blue;
        }

        public float getGreen() {
            return green;
        }

        public float getRed() {
            return red;
        }

        public int getPackedLightIn() {
            return packedLightIn;
        }

        public int getPackedOverlayIn() {
            return packedOverlayIn;
        }

        public IVertexBuilder getBufferIn() {
            return bufferIn;
        }

        public MatrixStack getMatrixStackIn() {
            return matrixStackIn;
        }

        public ModelRenderer getModelRenderer() {
            return modelRenderer;
        }

        public void setAlpha(float alpha) {
            this.alpha = alpha;
        }

        public void setBlue(float blue) {
            this.blue = blue;
        }

        public void setBufferIn(IVertexBuilder bufferIn) {
            this.bufferIn = bufferIn;
        }

        public void setGreen(float green) {
            this.green = green;
        }

        public void setMatrixStackIn(MatrixStack matrixStackIn) {
            this.matrixStackIn = matrixStackIn;
        }

        public void setModelRenderer(ModelRenderer modelRenderer) {
            this.modelRenderer = modelRenderer;
        }

        public void setPackedLightIn(int packedLightIn) {
            this.packedLightIn = packedLightIn;
        }

        public void setPackedOverlayIn(int packedOverlayIn) {
            this.packedOverlayIn = packedOverlayIn;
        }

        public void setRed(float red) {
            this.red = red;
        }
    }

    public static class Post extends RendererModelEvent {
        ModelRenderer modelRenderer;
        MatrixStack matrixStackIn;
        IVertexBuilder bufferIn;
        int packedLightIn;
        int packedOverlayIn;
        float red;
        float green;
        float blue;
        float alpha;

        public Post(ModelRenderer modelRenderer, MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
            this.modelRenderer = modelRenderer;
            this.matrixStackIn = matrixStackIn;
            this.bufferIn = bufferIn;
            this.packedLightIn = packedLightIn;
            this.packedOverlayIn = packedOverlayIn;
            this.red = red;
            this.green = green;
            this.blue = blue;
            this.alpha = alpha;
        }

        public float getAlpha() {
            return alpha;
        }

        public float getBlue() {
            return blue;
        }

        public float getGreen() {
            return green;
        }

        public float getRed() {
            return red;
        }

        public int getPackedLightIn() {
            return packedLightIn;
        }

        public int getPackedOverlayIn() {
            return packedOverlayIn;
        }

        public IVertexBuilder getBufferIn() {
            return bufferIn;
        }

        public MatrixStack getMatrixStackIn() {
            return matrixStackIn;
        }

        public ModelRenderer getModelRenderer() {
            return modelRenderer;
        }

        public void setAlpha(float alpha) {
            this.alpha = alpha;
        }

        public void setBlue(float blue) {
            this.blue = blue;
        }

        public void setBufferIn(IVertexBuilder bufferIn) {
            this.bufferIn = bufferIn;
        }

        public void setGreen(float green) {
            this.green = green;
        }

        public void setMatrixStackIn(MatrixStack matrixStackIn) {
            this.matrixStackIn = matrixStackIn;
        }

        public void setModelRenderer(ModelRenderer modelRenderer) {
            this.modelRenderer = modelRenderer;
        }

        public void setPackedLightIn(int packedLightIn) {
            this.packedLightIn = packedLightIn;
        }

        public void setPackedOverlayIn(int packedOverlayIn) {
            this.packedOverlayIn = packedOverlayIn;
        }

        public void setRed(float red) {
            this.red = red;
        }
    }
}

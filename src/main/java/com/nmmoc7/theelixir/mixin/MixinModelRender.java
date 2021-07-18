package com.nmmoc7.theelixir.mixin;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.nmmoc7.theelixir.event.RendererModelEvent;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author DustW
 */
@Mixin(value = ModelRenderer.class)
public class MixinModelRender {
    final ModelRenderer THIS = (ModelRenderer)(Object) this;

    @Inject(
            method = "render(Lcom/mojang/blaze3d/matrix/MatrixStack;Lcom/mojang/blaze3d/vertex/IVertexBuilder;IIFFFF)V",
            cancellable = true,
            at = @At(value = "HEAD")
    )
    private void renderTEPre(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha, CallbackInfo ci) {
        RendererModelEvent.Pre renderEvent = new RendererModelEvent.Pre(THIS, matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        MinecraftForge.EVENT_BUS.post(renderEvent);
        if (renderEvent.isCanceled()) {
            ci.cancel();
        }

        matrixStackIn = renderEvent.getMatrixStackIn();
        bufferIn = renderEvent.getBufferIn();
        packedLightIn = renderEvent.getPackedLightIn();
        packedOverlayIn = renderEvent.getPackedOverlayIn();
        red = renderEvent.getRed();
        green = renderEvent.getGreen();
        blue = renderEvent.getBlue();
        alpha = renderEvent.getAlpha();
    }

    @Inject(
            method = "render(Lcom/mojang/blaze3d/matrix/MatrixStack;Lcom/mojang/blaze3d/vertex/IVertexBuilder;IIFFFF)V",
            at = @At(value = "RETURN")
    )
    private void renderTEPost(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha, CallbackInfo ci) {
        RendererModelEvent.Post renderEvent = new RendererModelEvent.Post(THIS, matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        MinecraftForge.EVENT_BUS.post(renderEvent);

        matrixStackIn = renderEvent.getMatrixStackIn();
        bufferIn = renderEvent.getBufferIn();
        packedLightIn = renderEvent.getPackedLightIn();
        packedOverlayIn = renderEvent.getPackedOverlayIn();
        red = renderEvent.getRed();
        green = renderEvent.getGreen();
        blue = renderEvent.getBlue();
        alpha = renderEvent.getAlpha();
    }
}

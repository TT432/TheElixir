package org.teacon.theelixir.event.listener;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeBuffers;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.teacon.theelixir.TheElixir;
import org.teacon.theelixir.capability.CapabilityRegistryHandler;
import org.teacon.theelixir.model.ZZZZFlower;

/**
 * @author DustW
 */
@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class ListenerPlayerRender {
    public static final ResourceLocation ZZZZ_FLOWER_TEXTURE = new ResourceLocation(TheElixir.MOD_ID, "textures/entity/zzzzflower.png");
    public static final ZZZZFlower ZZZZ_FLOWER = new ZZZZFlower();

    @SubscribeEvent
    public static void onPlayerRender(RenderPlayerEvent event) {
        Minecraft mc = Minecraft.getInstance();
        PlayerEntity player = event.getPlayer();

        if (player.getCapability(CapabilityRegistryHandler.THE_ELIXIR_CAPABILITY).orElse(null).isHasFlower()) {
            World world = mc.world;
            Vector3d pos = player.getPositionVec().add(-1, 0, -1);
            RenderTypeBuffers renderBuffers = mc.getRenderTypeBuffers();
            RenderType renderType = ZZZZ_FLOWER.getRenderType(ZZZZ_FLOWER_TEXTURE);
            IVertexBuilder vertexBuilder = renderBuffers.getBufferSource().getBuffer(renderType);

            MatrixStack matrixStack = event.getMatrixStack();

            int overlay = OverlayTexture.getPackedUV(OverlayTexture.getU(0), OverlayTexture.getV(false));
            int light = LightTexture.packLight(world.getLightFor(LightType.SKY, new BlockPos(pos)), world.getLightFor(LightType.BLOCK, new BlockPos(pos)));

            matrixStack.push();
            matrixStack.rotate(Vector3f.YP.rotationDegrees(-player.getYaw(event.getPartialRenderTick())));
            matrixStack.rotate(Vector3f.XP.rotationDegrees(180));
            matrixStack.translate(0, -2.5, 0.5);

            ZZZZ_FLOWER.render(matrixStack, vertexBuilder, light, overlay, 1, 1, 1, 1);
            matrixStack.pop();
        }
    }

    @SubscribeEvent
    public static void onWorldRender(RenderWorldLastEvent event) {
        ZZZZ_FLOWER.setLivingAnimations(Minecraft.getInstance().player, tick, 0, event.getPartialTicks());
    }

    static int tick = 0;

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        tick++;
    }
}

package com.nmmoc7.theelixir.event.listener;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.nmmoc7.theelixir.TheElixir;
import com.nmmoc7.theelixir.capability.CapabilityRegistryHandler;
import com.nmmoc7.theelixir.capability.TheElixirCapability;
import com.nmmoc7.theelixir.event.RendererModelEvent;
import com.nmmoc7.theelixir.model.ZZZZFlower;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeBuffers;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.FoxRenderer;
import net.minecraft.client.renderer.entity.model.FoxModel;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.FoxEntity;
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

import java.util.UUID;

/**
 * @author DustW
 */
@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class ListenerPlayerRender {
    public static final ResourceLocation ZZZZ_FLOWER_TEXTURE = new ResourceLocation(TheElixir.MOD_ID, "textures/entity/zzzzflower.png");
    public static final ZZZZFlower ZZZZ_FLOWER = new ZZZZFlower();

    static UUID renderingPlayer;

    @SubscribeEvent
    public static void onPlayerRender(RenderPlayerEvent.Post event) {
        PlayerEntity player = event.getPlayer();
        renderingPlayer = player.getUniqueID();

        MatrixStack matrixStack = event.getMatrixStack();
        if (Minecraft.getInstance().player == null || player.getDistance(Minecraft.getInstance().player) > 64) return;
        PlayerModel<AbstractClientPlayerEntity> playerModel = event.getRenderer().getEntityModel();
        ModelRenderer bodyModel = playerModel.bipedBody;
        ModelRenderer headModel = playerModel.bipedHead;
        if (bodyModel == null || headModel == null) return;
        player.getCapability(CapabilityRegistryHandler.THE_ELIXIR_CAPABILITY).ifPresent(cap -> {
            if (cap.getTail() == null || !bodyModel.showModel) return;
            cap.getTail().showModel = cap.isHasFoxTail();
            if (cap.getEars()[0] == null || !headModel.showModel) return;
            cap.getEars()[0].showModel = cap.isHasFoxTail();
            if (cap.getEars()[1] == null) return;
            cap.getEars()[1].showModel = cap.isHasFoxTail();

            if (!cap.isBeAdd()) {
                bodyModel.addChild(cap.getTail());
                headModel.addChild(cap.getEars()[0]);
                headModel.addChild(cap.getEars()[1]);
                cap.setBeAdd(true);
            }
        });

        renderFlower(player, matrixStack, event.getPartialRenderTick());
    }

    private static void renderFlower(PlayerEntity player, MatrixStack matrixStack, float partialTick) {
        if (player.getCapability(CapabilityRegistryHandler.THE_ELIXIR_CAPABILITY).orElse(null).isHasFlower()) {
            Minecraft mc = Minecraft.getInstance();
            World world = mc.world;
            Vector3d pos = player.getPositionVec().add(-1, 0, -1);
            RenderTypeBuffers renderBuffers = mc.getRenderTypeBuffers();
            RenderType renderType = ZZZZ_FLOWER.getRenderType(ZZZZ_FLOWER_TEXTURE);
            IVertexBuilder vertexBuilder = renderBuffers.getBufferSource().getBuffer(renderType);

            int overlay = OverlayTexture.getPackedUV(OverlayTexture.getU(0), OverlayTexture.getV(false));
            int light = LightTexture.packLight(world.getLightFor(LightType.SKY, new BlockPos(pos)), world.getLightFor(LightType.BLOCK, new BlockPos(pos)));

            matrixStack.push();
            matrixStack.rotate(Vector3f.YP.rotationDegrees(-player.getYaw(partialTick)));
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

    @SubscribeEvent
    public static void onModelRender(RendererModelEvent.Pre event) {
        if (renderingPlayer != null) {
            ModelRenderer mr = event.getModelRenderer();
            Minecraft mc = Minecraft.getInstance();
            PlayerEntity player = mc.world.getPlayerByUuid(renderingPlayer);
            TheElixirCapability cap = player.getCapability(CapabilityRegistryHandler.THE_ELIXIR_CAPABILITY).orElse(null);
            if (mr == cap.getTail() || mr == cap.getEars()[0] || mr == cap.getEars()[1]) {
                if (mr.showModel) {
                    FoxRenderer foxRender = new FoxRenderer(mc.getRenderManager());
                    FoxEntity foxEntity = new FoxEntity(EntityType.FOX, mc.world);
                    RenderType type = new FoxModel<>().getRenderType(foxRender.getEntityTexture(foxEntity));
                    IVertexBuilder buffer = mc.getRenderTypeBuffers().getBufferSource().getBuffer(type);
                    event.setBufferIn(buffer);

                    event.getMatrixStackIn().push();

                    if (mr == cap.getTail()) {
                        event.getMatrixStackIn().translate(0, 4 / 16F, -12.5 / 16F);
                        event.getMatrixStackIn().rotate(Vector3f.XP.rotationDegrees(60));
                    } else {
                        event.getMatrixStackIn().translate(-1 / 16F, -6 / 16F, 1 / 16F);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onModelRenderPost(RendererModelEvent.Post event) {
        if (renderingPlayer != null) {
            ModelRenderer mr = event.getModelRenderer();
            Minecraft mc = Minecraft.getInstance();
            PlayerEntity player = mc.world.getPlayerByUuid(renderingPlayer);

            if (player != null) {
                TheElixirCapability cap = player.getCapability(CapabilityRegistryHandler.THE_ELIXIR_CAPABILITY).orElse(null);
                if (mr == cap.getTail() || mr == cap.getEars()[0] || mr == cap.getEars()[1]) {
                    if (mr.showModel) {
                        EntityRenderer<? super PlayerEntity> playerRenderer = mc.getRenderManager().getRenderer(player);
                        RenderType type = new PlayerModel<PlayerEntity>(0, false).getRenderType(playerRenderer.getEntityTexture(player));
                        IVertexBuilder buffer = mc.getRenderTypeBuffers().getBufferSource().getBuffer(type);
                        event.setBufferIn(buffer);

                        event.getMatrixStackIn().pop();
                    }
                }
            }
        }
    }
}

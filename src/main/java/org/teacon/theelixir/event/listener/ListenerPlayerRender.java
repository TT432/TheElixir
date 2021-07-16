package org.teacon.theelixir.event.listener;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
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
import org.teacon.theelixir.TheElixir;
import org.teacon.theelixir.capability.CapabilityRegistryHandler;
import org.teacon.theelixir.event.RendererModelEvent;
import org.teacon.theelixir.model.ZZZZFlower;

import java.util.UUID;

/**
 * @author DustW
 */
@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class ListenerPlayerRender {
    public static final ResourceLocation ZZZZ_FLOWER_TEXTURE = new ResourceLocation(TheElixir.MOD_ID, "textures/entity/zzzzflower.png");
    public static final ZZZZFlower ZZZZ_FLOWER = new ZZZZFlower();

    public static ModelRenderer TAIL;
    static boolean beGet;

    public static ModelRenderer getTail() {
        if (!beGet) {
            TAIL = (new FoxModel<>()).tail;
            beGet = true;
        }
        return TAIL;
    }

    static boolean beAdd;

    static UUID renderingPlayer;

    @SubscribeEvent
    public static void onPlayerRender(RenderPlayerEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        PlayerEntity player = event.getPlayer();

        renderingPlayer = player.getUniqueID();

        MatrixStack matrixStack = event.getMatrixStack();

        PlayerModel<AbstractClientPlayerEntity> playerModel = event.getRenderer().getEntityModel();
        ModelRenderer bodyModel = playerModel.bipedBody;

        if (!beAdd && player.getCapability(CapabilityRegistryHandler.THE_ELIXIR_CAPABILITY).orElse(null).isHasFoxTail()) {
            if (getTail() != null) {
                bodyModel.addChild(getTail());
                beAdd = true;
            }
        }

        if (player.getCapability(CapabilityRegistryHandler.THE_ELIXIR_CAPABILITY).orElse(null).isHasFlower()) {
            World world = mc.world;
            Vector3d pos = player.getPositionVec().add(-1, 0, -1);
            RenderTypeBuffers renderBuffers = mc.getRenderTypeBuffers();
            RenderType renderType = ZZZZ_FLOWER.getRenderType(ZZZZ_FLOWER_TEXTURE);
            IVertexBuilder vertexBuilder = renderBuffers.getBufferSource().getBuffer(renderType);

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

    @SubscribeEvent
    public static void onModelRender(RendererModelEvent.Pre event) {
        if (event.getModelRenderer() == getTail()) {
            Minecraft mc = Minecraft.getInstance();
            FoxRenderer foxRender = new FoxRenderer(mc.getRenderManager());
            FoxEntity foxEntity = new FoxEntity(EntityType.FOX, mc.world);
            RenderType type = new FoxModel<>().getRenderType(foxRender.getEntityTexture(foxEntity));
            IVertexBuilder buffer = mc.getRenderTypeBuffers().getBufferSource().getBuffer(type);
            event.setBufferIn(buffer);

            event.getMatrixStackIn().push();

            event.getMatrixStackIn().translate(0, -3 / 16F, -8 / 16F);
            event.getMatrixStackIn().rotate(Vector3f.XP.rotationDegrees(30));
        }
    }

    @SubscribeEvent
    public static void onModelRenderPost(RendererModelEvent.Post event) {
        if (event.getModelRenderer() == getTail()) {
            Minecraft mc = Minecraft.getInstance();
            PlayerEntity player = mc.world.getPlayerByUuid(renderingPlayer);
            EntityRenderer<? super PlayerEntity> playerRenderer = mc.getRenderManager().getRenderer(player);
            RenderType type = new PlayerModel<PlayerEntity>(0, false).getRenderType(playerRenderer.getEntityTexture(player));
            IVertexBuilder buffer = mc.getRenderTypeBuffers().getBufferSource().getBuffer(type);
            event.setBufferIn(buffer);

            event.getMatrixStackIn().pop();
        }
    }
}

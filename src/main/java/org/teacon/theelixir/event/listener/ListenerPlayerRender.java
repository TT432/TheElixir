package org.teacon.theelixir.event.listener;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeBuffers;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.FoxRenderer;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.client.renderer.entity.model.BipedModel;
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
import org.lwjgl.system.CallbackI;
import org.omg.PortableInterceptor.ObjectReferenceFactoryHelper;
import org.teacon.theelixir.TheElixir;
import org.teacon.theelixir.capability.CapabilityRegistryHandler;
import org.teacon.theelixir.event.RendererModelEvent;
import org.teacon.theelixir.model.ZZZZFlower;

import java.lang.reflect.Field;
import java.util.Random;
import java.util.UUID;
import java.util.function.Function;

/**
 * @author DustW
 */
@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class ListenerPlayerRender {
    public static final ResourceLocation ZZZZ_FLOWER_TEXTURE = new ResourceLocation(TheElixir.MOD_ID, "textures/entity/zzzzflower.png");
    public static final ZZZZFlower ZZZZ_FLOWER = new ZZZZFlower();

    public static ModelRenderer TAIL;
    static boolean beGet;
    static ModelRenderer LEFT_EAR;
    static ModelRenderer RIGHT_EAR;
    static boolean earBeGet;

    public static ModelRenderer getTail() {
        if (!beGet) {
            Field tail = null;

            try {
                try {
                    tail = FoxModel.class.getDeclaredField("tail");
                } catch (NoSuchFieldException e) {
                    try {
                        tail = FoxModel.class.getDeclaredField("field_217124_m");
                    } catch (NoSuchFieldException ignored) {
                    }
                }

                if (tail != null) {
                    tail.setAccessible(true);
                    TAIL = (ModelRenderer) tail.get(new FoxModel<>());
                    beGet = true;
                }
            }
            catch (IllegalAccessException ignored) {

            }
        }

        return TAIL;
    }

    public static ModelRenderer[] getEars() {
        if (!earBeGet) {
            Field leftEar = null;
            Field rightEar = null;

            try {
                try {
                    leftEar = FoxModel.class.getDeclaredField("leftEar");
                    rightEar = FoxModel.class.getDeclaredField("rightEar");
                } catch (NoSuchFieldException e) {
                    try {
                        leftEar = FoxModel.class.getDeclaredField("field_217117_f");
                        rightEar = FoxModel.class.getDeclaredField("field_217116_b");
                    } catch (NoSuchFieldException ignored) {
                    }
                }

                if (leftEar != null && rightEar != null) {
                    leftEar.setAccessible(true);
                    rightEar.setAccessible(true);
                    LEFT_EAR = (ModelRenderer) leftEar.get(new FoxModel<>());
                    RIGHT_EAR = (ModelRenderer) rightEar.get(new FoxModel<>());
                    earBeGet = true;
                }
            }
            catch (IllegalAccessException ignored) {

            }
        }

        return new ModelRenderer[] {LEFT_EAR, RIGHT_EAR};
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
        ModelRenderer headModel = playerModel.bipedHead;

        if (getTail() != null && getEars()[0] != null) {
            boolean flag = player.getCapability(CapabilityRegistryHandler.THE_ELIXIR_CAPABILITY).orElse(null).isHasFoxTail();
            getTail().showModel = flag;
            getEars()[0].showModel = flag;
            getEars()[1].showModel = flag;
        }

        if (!beAdd) {
            if (getTail() != null) {
                bodyModel.addChild(getTail());
                headModel.addChild(getEars()[0]);
                headModel.addChild(getEars()[1]);
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
        ModelRenderer mr = event.getModelRenderer();
        if (mr == getTail() || mr == getEars()[0] || mr == getEars()[1]) {
            Minecraft mc = Minecraft.getInstance();
            FoxRenderer foxRender = new FoxRenderer(mc.getRenderManager());
            FoxEntity foxEntity = new FoxEntity(EntityType.FOX, mc.world);
            RenderType type = new FoxModel<>().getRenderType(foxRender.getEntityTexture(foxEntity));
            IVertexBuilder buffer = mc.getRenderTypeBuffers().getBufferSource().getBuffer(type);
            event.setBufferIn(buffer);

            event.getMatrixStackIn().push();

            if (mr == getTail()) {
                event.getMatrixStackIn().translate(0, 4 / 16F, -12.5 / 16F);
                event.getMatrixStackIn().rotate(Vector3f.XP.rotationDegrees(60));
            }
            else {
                event.getMatrixStackIn().translate(-1 / 16F, -6 / 16F, 1 / 16F);
            }
        }
    }

    @SubscribeEvent
    public static void onModelRenderPost(RendererModelEvent.Post event) {
        ModelRenderer mr = event.getModelRenderer();
        if (mr == getTail() || mr == getEars()[0] || mr == getEars()[1]) {
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

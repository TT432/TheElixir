package com.nmmoc7.theelixir.event.listener;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.nmmoc7.theelixir.RegistryHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * @author DustW
 */
@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class ListenerOverlayRenderEvent {
    @SubscribeEvent
    public static void onRenderWorld(RenderGameOverlayEvent.Post event) {
        MatrixStack matrixStack = event.getMatrixStack();
        matrixStack.push();

        Minecraft mc = Minecraft.getInstance();
        ClientPlayerEntity player = mc.player;

        if (player.inventory.armorInventory.stream().anyMatch(itemStack -> itemStack.getItem() == RegistryHandler.ASS_REVEAL_GOGGLES)) {
            if (player.getHeldItemMainhand().getItem() == RegistryHandler.BOB_BOOTS) {
                if (mc.objectMouseOver != null) {
                    if (mc.objectMouseOver.getType() == RayTraceResult.Type.ENTITY) {
                        if (((EntityRayTraceResult) mc.objectMouseOver).getEntity() instanceof LivingEntity) {
                            LivingEntity target = (LivingEntity) ((EntityRayTraceResult) mc.objectMouseOver).getEntity();
                            Vector3d playerLook = player.getLook(1);
                            Vector3d targetLook = target.getLook(1);

                            if (Direction.getFacingFromVector(playerLook.x, playerLook.y, playerLook.z) ==
                                    Direction.getFacingFromVector(targetLook.x, targetLook.y, targetLook.z)) {

                                float playerPitch = (float) Math.toRadians(player.getPitch(1));
                                double distance = player.getPositionVec().distanceTo(target.getPositionVec());
                                double a = MathHelper.sin(playerPitch) / MathHelper.cos(playerPitch) * distance;

                                float targetHeight = target.getHeight();
                                float height = player.getHeight() - player.getEyeHeight();

                                if (a - height > targetHeight * ((12 - 6) / 32F) && a - height < targetHeight * ((12 + 6) / 32F)) {
                                    mc.getRenderManager().getFontRenderer().drawText(
                                            matrixStack,
                                            new StringTextComponent("KICK IT!"),
                                            mc.getMainWindow().getScaledWidth() / 2F + 3,
                                            mc.getMainWindow().getScaledHeight() / 2F + 3,
                                            0xFF_000000);
                                }
                            }
                        }
                    }
                }
            }
        }

        matrixStack.pop();
    }
}

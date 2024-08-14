package io.github.tt432.theelixir.client;

import io.github.tt432.eyelib.client.cursor.Cursor;
import io.github.tt432.eyelib.client.cursor.CursorManager;
import io.github.tt432.theelixir.TheElixir;
import io.github.tt432.theelixir.capability.TheElixirAttachmentData;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderFrameEvent;

/**
 * @author TT432
 */
@EventBusSubscriber(Dist.CLIENT)
public class TheElixirCursorHandler {
    private static Cursor cursor;

    @SubscribeEvent
    public static void onEvent(RenderFrameEvent.Pre event) {
        if (Minecraft.getInstance().player != null
                && Minecraft.getInstance().player.getData(TheElixirAttachmentData.FOX_TAIL).hasFox()) {
            if (cursor == null)
                cursor = CursorManager.load(ResourceLocation.fromNamespaceAndPath(
                        TheElixir.MOD_ID,
                        "textures/cursor/fox_cursor.png"
                ), 0, 0);

            cursor.set();
        } else {
            if (cursor != null) {
                cursor.destroy();
                cursor = null;
            }
        }
    }
}

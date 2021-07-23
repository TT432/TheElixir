package com.nmmoc7.theelixir.teleport_stone;

import com.nmmoc7.theelixir.TheElixir;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TranslationTextComponent;

/**
 * @author DustW
 */
public class OpenGui {
    public OpenGui() {
        Minecraft.getInstance().displayGuiScreen(new TeleportFirstGui(new TranslationTextComponent(TheElixir.MOD_ID + ".teleport_gui")));
    }
}

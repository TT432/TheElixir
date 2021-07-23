package com.nmmoc7.theelixir.teleport_stone;

import com.google.common.collect.Sets;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.network.play.NetworkPlayerInfo;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerModelPart;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.util.Set;
import java.util.UUID;

/**
 * @author DustW
 */
public class PlayerButton {
    private final ResourceLocation id;
    private final PlayerButton parent;
    private final DisplayInfo display;
    public final NetworkPlayerInfo playerInfo;
    private final Set<PlayerButton> children = Sets.newLinkedHashSet();
    public UUID playerUuid = null;

    public PlayerButton(ResourceLocation id, NetworkPlayerInfo playerInfo, PlayerButton parentIn, @Nullable DisplayInfo displayIn) {
        this.id = id;
        this.display = displayIn;
        this.parent = parentIn;
        this.playerInfo = playerInfo;
        if (playerInfo != null) {
            this.playerUuid = playerInfo.getGameProfile().getId();
        }
        if (parentIn != null) {
            parentIn.addChild(this);
        }
    }

    public void render(MatrixStack matrixStack, int x, int y) {
        Minecraft mc = Minecraft.getInstance();
        if (playerInfo != null) {
            matrixStack.push();
            PlayerEntity playerentity = mc.world.getPlayerByUuid(playerUuid);
            boolean flag1 = playerentity != null && playerentity.isWearing(PlayerModelPart.CAPE);
            mc.getTextureManager().bindTexture(playerInfo.getLocationSkin());
            int i3 = 8 + (flag1 ? 8 : 0);
            int j3 = 8 * (flag1 ? -1 : 1);
            AbstractGui.blit(matrixStack, x, y, 8, 8, 8.0F, (float)i3, 8, j3, 64, 64);
            if (playerentity != null && playerentity.isWearing(PlayerModelPart.HAT)) {
                int k3 = 8 + (flag1 ? 8 : 0);
                int l3 = 8 * (flag1 ? -1 : 1);
                AbstractGui.blit(matrixStack, x, y, 8, 8, 40.0F, (float)k3, 8, l3, 64, 64);
            }
            matrixStack.pop();
        }
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Nullable
    public PlayerButton getParent() {
        return this.parent;
    }

    public void addChild(PlayerButton advancementIn) {
        this.children.add(advancementIn);
    }

    @Nullable
    public DisplayInfo getDisplay() {
        return this.display;
    }
}

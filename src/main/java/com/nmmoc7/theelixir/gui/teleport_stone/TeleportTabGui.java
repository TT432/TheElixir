package com.nmmoc7.theelixir.gui.teleport_stone;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.advancements.FrameType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.advancements.AdvancementTabType;
import net.minecraft.client.network.play.ClientPlayNetHandler;
import net.minecraft.client.network.play.NetworkPlayerInfo;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

import java.util.Collection;
import java.util.Map;

/**
 * @author DustW
 */
public class TeleportTabGui extends AbstractGui {
    public final Minecraft minecraft;
    public final AdvancementTabType type;
    public final TeleportFirstGui screen;
    public final int index;
    public final DisplayInfo display;
    public final ItemStack icon;
    public final ITextComponent title;
    public TeleportEntryGui root;
    private final Map<PlayerButton, TeleportEntryGui> guis = Maps.newLinkedHashMap();
    public double scrollX;
    public double scrollY;
    public int minX = Integer.MAX_VALUE;
    public int minY = Integer.MAX_VALUE;
    public int maxX = Integer.MIN_VALUE;
    public int maxY = Integer.MIN_VALUE;
    public float fade;
    public boolean centered;

    public boolean onDrawTooltip = false;
    public TeleportEntryGui drawingEntry = null;

    public TeleportTabGui(Minecraft minecraft, TeleportFirstGui screen, AdvancementTabType type, int index, PlayerButton advancement) {
        this.minecraft = minecraft;
        this.screen = screen;
        this.type = type;
        this.index = index;
        this.display = advancement.getDisplay();
        this.icon = display.getIcon();
        this.title = display.getTitle();
        this.root = new TeleportEntryGui(this, minecraft, advancement, display);
    }

    public ITextComponent getTitle() {
        return this.title;
    }

    public void renderTabSelectorBackground(MatrixStack matrixStack, int offsetX, int offsetY, boolean isSelected) {
        this.type.renderTabSelectorBackground(matrixStack, this, offsetX, offsetY, isSelected, this.index);
    }

    public void drawIcon(int offsetX, int offsetY, ItemRenderer renderer) {
        this.type.drawIcon(offsetX, offsetY, this.index, renderer, this.icon);
    }

    public void drawTabBackground(MatrixStack matrixStack) {
        if (!this.centered) {
            this.scrollX = 117 - (this.maxX + this.minX) / 2F;
            this.scrollY = 56 - (this.maxY + this.minY) / 2F;
            this.centered = true;
        }

        RenderSystem.pushMatrix();
        RenderSystem.enableDepthTest();
        RenderSystem.translatef(0.0F, 0.0F, 950.0F);
        RenderSystem.colorMask(false, false, false, false);
        fill(matrixStack, 4680, 2260, -4680, -2260, -16777216);
        RenderSystem.colorMask(true, true, true, true);
        RenderSystem.translatef(0.0F, 0.0F, -950.0F);
        RenderSystem.depthFunc(518);
        fill(matrixStack, 234, 113, 0, 0, -16777216);
        RenderSystem.depthFunc(515);
        ResourceLocation resourcelocation = this.display.getBackground();
        if (resourcelocation != null) {
            this.minecraft.getTextureManager().bindTexture(resourcelocation);
        } else {
            this.minecraft.getTextureManager().bindTexture(TextureManager.RESOURCE_LOCATION_EMPTY);
        }

        int i = MathHelper.floor(this.scrollX);
        int j = MathHelper.floor(this.scrollY);
        int k = i % 16;
        int l = j % 16;

        for(int i1 = -1; i1 <= 15; ++i1) {
            for(int j1 = -1; j1 <= 8; ++j1) {
                blit(matrixStack, k + 16 * i1, l + 16 * j1, 0.0F, 0.0F, 16, 16, 16, 16);
            }
        }

        this.root.drawConnectionLineToParent(matrixStack, i, j, true);
        this.root.drawConnectionLineToParent(matrixStack, i, j, false);
        this.root.drawAdvancement(matrixStack, i, j);
        RenderSystem.depthFunc(518);
        RenderSystem.translatef(0.0F, 0.0F, -950.0F);
        RenderSystem.colorMask(false, false, false, false);
        fill(matrixStack, 4680, 2260, -4680, -2260, -16777216);
        RenderSystem.colorMask(true, true, true, true);
        RenderSystem.translatef(0.0F, 0.0F, 950.0F);
        RenderSystem.depthFunc(515);
        RenderSystem.popMatrix();
    }

    public void drawTabTooltips(MatrixStack matrixStack, int mouseX, int mouseY, int width, int height) {
        onDrawTooltip = false;

        RenderSystem.pushMatrix();
        RenderSystem.translatef(0.0F, 0.0F, 200.0F);
        fill(matrixStack, 0, 0, 234, 113, MathHelper.floor(this.fade * 255.0F) << 24);
        int i = MathHelper.floor(this.scrollX);
        int j = MathHelper.floor(this.scrollY);
        if (mouseX > 0 && mouseX < 234 && mouseY > 0 && mouseY < 113) {
            for(TeleportEntryGui teleportEntryGui : this.guis.values()) {
                if (teleportEntryGui.isMouseOver(i, j, mouseX, mouseY)) {
                    onDrawTooltip = true;
                    drawingEntry = teleportEntryGui;
                    teleportEntryGui.drawAdvancementHover(matrixStack, i, j, this.fade, width, height);
                    break;
                }
            }
        }
        RenderSystem.popMatrix();
        if (onDrawTooltip) {
            this.fade = MathHelper.clamp(this.fade + 0.02F, 0.0F, 0.3F);
        } else {
            this.fade = MathHelper.clamp(this.fade - 0.04F, 0.0F, 1.0F);
        }
    }

    public boolean isInsideTabSelector(int offsetX, int offsetY, double mouseX, double mouseY) {
        return this.type.inInsideTabSelector(offsetX, offsetY, this.index, mouseX, mouseY);
    }

    public void refresh() {
        guis.clear();
        ClientPlayNetHandler clientplaynethandler = minecraft.player.connection;
        NetworkPlayerInfo[] list = clientplaynethandler.getPlayerInfoMap().toArray(new NetworkPlayerInfo[0]);

        PlayerButton playerButtonRoot = new PlayerButton(new ResourceLocation("button"),
                list[0], null, getDisplay(list[0]));
        this.root = new TeleportEntryGui(this, minecraft, playerButtonRoot, getDisplay(list[0]));
        this.root.parent = null;
        addPlayerButton(this.root, playerButtonRoot);

        for (int i = 1;  i < list.length; i++) {
            DisplayInfo tempDisplay = getDisplay(list[i]);
            tempDisplay.setPosition(i * 2, 0);

            PlayerButton playerButton = new PlayerButton(new ResourceLocation("button" + i),
                    list[i], null, tempDisplay);

            addPlayerButton(new TeleportEntryGui(this, minecraft, playerButton, tempDisplay), playerButton);

            PlayerButton before = guis.values().toArray(new TeleportEntryGui[0])[i - 1].getPlayerButton();

            if (before != null) {
                guis.get(before).parent = guis.get(playerButton);
                guis.get(before).addGuiAdvancement(guis.get(playerButton));
                guis.get(before).attachToParent();
            }
        }
    }

    public DisplayInfo getDisplay(NetworkPlayerInfo info) {
        return new DisplayInfo(
                new ItemStack(Items.AIR),
                new StringTextComponent(info.getGameProfile().getName()),
                new StringTextComponent(info.getGameProfile().getName()),
                null,
                FrameType.TASK,
                false, false, false
        );
    }

    private void addPlayerButton(TeleportEntryGui gui, PlayerButton playerButton) {
        this.guis.put(playerButton, gui);
        int i = gui.getX();
        int j = i + 28;
        int k = gui.getY();
        int l = k + 27;
        this.minX = Math.min(this.minX, i);
        this.maxX = Math.max(this.maxX, j);
        this.minY = Math.min(this.minY, k);
        this.maxY = Math.max(this.maxY, l);
    }

    public void dragSelectedGui(double dragX, double dragY) {
        if (this.maxX - this.minX > 234) {
            this.scrollX = MathHelper.clamp(this.scrollX + dragX, (double)(-(this.maxX - 234)), 0.0D);
        }

        if (this.maxY - this.minY > 113) {
            this.scrollY = MathHelper.clamp(this.scrollY + dragY, (double)(-(this.maxY - 113)), 0.0D);
        }
    }
}

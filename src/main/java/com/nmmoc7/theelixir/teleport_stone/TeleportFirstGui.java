package com.nmmoc7.theelixir.teleport_stone;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.nmmoc7.theelixir.network.ModNetworkManager;
import com.nmmoc7.theelixir.network.client.TeleportClient;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.advancements.FrameType;
import net.minecraft.client.gui.advancements.AdvancementTabType;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import org.lwjgl.glfw.GLFW;

/**
 * @author DustW
 */
public class TeleportFirstGui extends Screen {
    public static final ResourceLocation ADVENTURE = new ResourceLocation("textures/gui/advancements/backgrounds/adventure.png");
    private static final ResourceLocation WINDOW = new ResourceLocation("textures/gui/advancements/window.png");
    private static final ResourceLocation TABS = new ResourceLocation("textures/gui/advancements/tabs.png");
    private static final ITextComponent GUI_LABEL = new StringTextComponent("Teleport GUI");

    private TeleportTabGui selectedTab;

    private boolean isScrolling;

    public TeleportFirstGui(ITextComponent titleIn) {
        super(titleIn);
    }

    @Override
    protected void init() {
        this.selectedTab = new TeleportTabGui(minecraft, this, AdvancementTabType.LEFT, 0,
                new PlayerButton(new ResourceLocation("teleport_gui"), null, null, new DisplayInfo(
                        new ItemStack(Items.BOOK),
                        new StringTextComponent("Teleport GUI"),
                        new StringTextComponent("Teleport GUI"),
                        ADVENTURE,
                        FrameType.TASK,
                        false, false, false
                )));

        super.init();
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        int i = (this.width - 252) / 2;
        int j = (this.height - 140) / 2;
        selectedTab.refresh();
        this.renderBackground(matrixStack);
        this.drawWindowBackground(matrixStack, i, j);
        this.renderWindow(matrixStack, i, j);
        this.drawWindowTooltips(matrixStack, mouseX, mouseY, i, j);
    }

    private void drawWindowBackground(MatrixStack matrixStack, int offsetX, int offsetY) {
        TeleportTabGui teleportTabGui = this.selectedTab;
        RenderSystem.pushMatrix();
        RenderSystem.translatef((float)(offsetX + 9), (float)(offsetY + 18), 0.0F);
        teleportTabGui.drawTabBackground(matrixStack);
        RenderSystem.popMatrix();
        RenderSystem.depthFunc(515);
        RenderSystem.disableDepthTest();
    }

    public void renderWindow(MatrixStack matrixStack, int offsetX, int offsetY) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.enableBlend();
        this.minecraft.getTextureManager().bindTexture(WINDOW);
        this.blit(matrixStack, offsetX, offsetY, 0, 0, 252, 140);
        this.minecraft.getTextureManager().bindTexture(TABS);

        selectedTab.renderTabSelectorBackground(matrixStack, offsetX, offsetY, true);

        RenderSystem.enableRescaleNormal();
        RenderSystem.defaultBlendFunc();

        selectedTab.drawIcon(offsetX, offsetY, this.itemRenderer);

        RenderSystem.disableBlend();

        this.font.drawText(matrixStack, GUI_LABEL, (float)(offsetX + 8), (float)(offsetY + 6), 4210752);
    }

    private void drawWindowTooltips(MatrixStack matrixStack, int mouseX, int mouseY, int offsetX, int offsetY) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.pushMatrix();
        RenderSystem.enableDepthTest();
        RenderSystem.translatef((float)(offsetX + 9), (float)(offsetY + 18), 400.0F);
        this.selectedTab.drawTabTooltips(matrixStack, mouseX - offsetX - 9, mouseY - offsetY - 18, offsetX, offsetY);
        RenderSystem.disableDepthTest();
        RenderSystem.popMatrix();

        if (selectedTab.isInsideTabSelector(offsetX, offsetY, mouseX, mouseY)) {
            this.renderTooltip(matrixStack, selectedTab.getTitle(), mouseX, mouseY);
        }
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        if (button != 0) {
            this.isScrolling = false;
            return false;
        } else {
            if (!this.isScrolling) {
                this.isScrolling = true;
            } else if (this.selectedTab != null) {
                this.selectedTab.dragSelectedGui(dragX, dragY);
            }

            return true;
        }
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (button == GLFW.GLFW_MOUSE_BUTTON_LEFT && this.selectedTab.onDrawTooltip) {
            ModNetworkManager.clientSendToServer(new TeleportClient(this.selectedTab.drawingEntry.getPlayerButton().playerUuid));
            closeScreen();
        }

        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (this.minecraft.gameSettings.keyBindAdvancements.matchesKey(keyCode, scanCode)) {
            this.minecraft.displayGuiScreen((Screen)null);
            this.minecraft.mouseHelper.grabMouse();
            return true;
        } else {
            return super.keyPressed(keyCode, scanCode, modifiers);
        }
    }
}
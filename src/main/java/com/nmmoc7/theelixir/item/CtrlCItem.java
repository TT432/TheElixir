package com.nmmoc7.theelixir.item;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

import javax.annotation.Nullable;
import java.util.List;

/**
 * @author DustW
 */
public class CtrlCItem extends ModItemBase {
    public CtrlCItem() {
        super("ctrl_c", 1);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(new TranslationTextComponent("tooltip.ctrl_c.1"));
        tooltip.add(new StringTextComponent(" "));
        tooltip.add(new TranslationTextComponent("tooltip.ctrl_c.2"));
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        if (!worldIn.isRemote) {
            for (ServerPlayerEntity player : ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayers()) {
                for (ServerPlayerEntity serverPlayerEntity : ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayers()) {
                    player.sendStatusMessage(new StringTextComponent("§e" + serverPlayerEntity.getDisplayName().getString() + "退出了游戏"), false);
                }
            }
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }
}

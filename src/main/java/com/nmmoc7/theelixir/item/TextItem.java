package com.nmmoc7.theelixir.item;

import com.nmmoc7.theelixir.TheElixir;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

/**
 * @author DustW
 */
public class TextItem extends Item {
    public TextItem() {
        super(new Properties());

        this.setRegistryName(new ResourceLocation(TheElixir.MOD_ID, "test_item"));
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(new StringTextComponent("右键自杀"));
        tooltip.add(new StringTextComponent(" "));
        tooltip.add(new StringTextComponent("小朋友们请不要模仿哦"));
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        if (!worldIn.isRemote) {
            playerIn.sendMessage(new StringTextComponent("尝试remove玩家"), Util.DUMMY_UUID);
            playerIn.remove();
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }
}

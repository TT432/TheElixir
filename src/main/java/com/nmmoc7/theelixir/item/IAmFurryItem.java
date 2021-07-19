package com.nmmoc7.theelixir.item;

import com.nmmoc7.theelixir.capability.CapabilityRegistryHandler;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

/**
 * @author DustW
 */
public class IAmFurryItem extends ModItemBase {
    public IAmFurryItem() {
        super("i_am_furry", 1);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        if (!worldIn.isRemote) {
            playerIn.getCapability(CapabilityRegistryHandler.THE_ELIXIR_CAPABILITY).ifPresent(theCap -> {
                theCap.setHasFoxTail(!theCap.isHasFoxTail());
            });

            playerIn.getCooldownTracker().setCooldown(playerIn.getHeldItem(handIn).getItem(), 20);
        }

        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(new StringTextComponent("右键以得到可爱的狐狸尾巴和耳朵"));
        tooltip.add(new StringTextComponent(" "));
        tooltip.add(new StringTextComponent("§7§o§l福瑞控是吧？ "));
        tooltip.add(new StringTextComponent("             ——Black Hand"));
    }
}

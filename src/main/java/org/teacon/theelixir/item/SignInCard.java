package org.teacon.theelixir.item;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
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
public class SignInCard extends ModItemBase {
    public SignInCard() {
        super("sign_in_card", 64);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(new StringTextComponent("所有者: " + (stack.getOrCreateTag().contains("owner") ? stack.getShareTag().getString("owner") : "暂无")));
        tooltip.add(new StringTextComponent("打卡天数: " + stack.getOrCreateTag().getInt("sign_in")));
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        if (!worldIn.isRemote) {
            CompoundNBT shareTag = playerIn.getHeldItem(handIn).getOrCreateTag();
            if (!shareTag.contains("owner") && shareTag.contains("uuid")) {
                if (shareTag.getUniqueId("uuid").equals(playerIn.getUniqueID())) {
                    playerIn.sendMessage(new StringTextComponent("打卡失败！用自己的卡！"), playerIn.getUniqueID());
                    return super.onItemRightClick(worldIn, playerIn, handIn);
                }
            }
            else {
                shareTag.putString("owner", playerIn.getDisplayName().getString());
                shareTag.putUniqueId("uuid", playerIn.getUniqueID());
            }

            int worldDay = Math.toIntExact(worldIn.getDayTime() / 24000L);
            int itemDay = shareTag.getInt("day");

            if (worldDay > itemDay || itemDay == 0) {
                shareTag.putInt("day", worldDay);
                playerIn.sendMessage(new StringTextComponent("打卡成功！请享受今天！"), playerIn.getUniqueID());

                int signInCount = shareTag.getInt("sign_in");
                shareTag.putInt("sign_in", signInCount + 1);
            }
            else {
                playerIn.sendMessage(new StringTextComponent("打卡失败！今天你打过卡了！"), playerIn.getUniqueID());
            }
        }

        return super.onItemRightClick(worldIn, playerIn, handIn);
    }
}

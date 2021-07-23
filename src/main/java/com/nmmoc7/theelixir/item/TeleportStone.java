package com.nmmoc7.theelixir.item;

import com.nmmoc7.theelixir.teleport_stone.OpenGui;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

/**
 * @author DustW
 */
public class TeleportStone extends ModItemBase {
    public TeleportStone() {
        super("teleport_stone", 1);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity p_77659_2_, Hand p_77659_3_) {
        if (world.isRemote) {
            new OpenGui();
        }
        return super.onItemRightClick(world, p_77659_2_, p_77659_3_);
    }
}

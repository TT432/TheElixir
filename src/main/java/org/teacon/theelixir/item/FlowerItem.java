package org.teacon.theelixir.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.teacon.theelixir.capability.CapabilityRegistryHandler;

/**
 * @author DustW
 */
public class FlowerItem extends ModItemBase {
    public FlowerItem() {
        super("flower_item", 1);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        if (!worldIn.isRemote) {
            playerIn.getCapability(CapabilityRegistryHandler.THE_ELIXIR_CAPABILITY).ifPresent(theCap -> {
                theCap.setHasFlower(!theCap.isHasFlower());
            });

            playerIn.getCooldownTracker().setCooldown(playerIn.getHeldItem(handIn).getItem(), 20);
        }

        return super.onItemRightClick(worldIn, playerIn, handIn);
    }
}

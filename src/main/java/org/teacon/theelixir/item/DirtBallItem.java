package org.teacon.theelixir.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import org.teacon.theelixir.capability.CapabilityRegistryHandler;
import org.teacon.theelixir.capability.TheElixirCapability;
import org.teacon.theelixir.entity.DirtBallEntity;
import org.teacon.theelixir.utils.EntityUtils;

import javax.annotation.Nullable;

/**
 * @author DustW
 */
public class DirtBallItem extends ModItemBase {
    public DirtBallItem() {
        super("dirt_ball_item", 64);
    }

    public float getDamageCount(@Nullable PlayerEntity player) {
        if (player != null) {
            TheElixirCapability cap = player.getCapability(CapabilityRegistryHandler.THE_ELIXIR_CAPABILITY).orElse(null);
            return cap.isHasFlower() ? 8 : 4;
        }
        return 4;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        if (!worldIn.isRemote) {
            EntityUtils.defaultDirtBallEntityShoot(worldIn, playerIn);

            if (!playerIn.isCreative()) {
                playerIn.getHeldItem(handIn).shrink(1);
            }

            playerIn.getCooldownTracker().setCooldown(playerIn.getHeldItem(handIn).getItem(), 20);
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }
}

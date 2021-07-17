package org.teacon.theelixir.item;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import org.teacon.theelixir.damagesource.ModDamageSources;

import javax.annotation.Nullable;
import java.util.List;

/**
 * @author DustW
 */
public class BobBoots extends ModItemBase {
    public BobBoots() {
        super("bob_boots", 1);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(new StringTextComponent("对生物的屁股击打会造成20点伤害。"));
        tooltip.add(new StringTextComponent("戴上“屁股揭示护目镜”以揭示“屁股”"));
        tooltip.add(new StringTextComponent(" "));
        tooltip.add(new StringTextComponent("哦, 我的上帝! 我发誓, 我一定要用我的靴子狠狠地踢你的屁股!"));
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, PlayerEntity player, Entity entity) {
        if (!player.world.isRemote && entity instanceof LivingEntity) {
            Vector3d playerLook = player.getLook(1);
            Vector3d targetLook = entity.getLook(1);

            if (Direction.getFacingFromVector(playerLook.x, playerLook.y, playerLook.z) ==
                    Direction.getFacingFromVector(targetLook.x, targetLook.y, targetLook.z)) {

                float playerPitch = (float) Math.toRadians(player.getPitch(1));
                double distance = player.getPositionVec().distanceTo(entity.getPositionVec());
                double a = MathHelper.sin(playerPitch) / MathHelper.cos(playerPitch) * distance;

                float targetHeight = entity.getHeight();
                float height = player.getHeight() - player.getEyeHeight();

                if (a - height > targetHeight * ((12 - 6) / 32F) && a - height < targetHeight * ((12 + 6) / 32F)) {
                    entity.attackEntityFrom(ModDamageSources.BOOTS_KICKS, 20);
                }
            }
        }
        return super.onLeftClickEntity(stack, player, entity);
    }
}

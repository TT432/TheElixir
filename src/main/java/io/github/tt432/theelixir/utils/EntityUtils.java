package io.github.tt432.theelixir.utils;

import io.github.tt432.theelixir.common.entity.DirtBallEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

/**
 * @author DustW
 */
public class EntityUtils {
    public static void defaultDirtBallEntityShoot(Level worldIn, Player playerIn) {
        DirtBallEntity dirtBallEntity = new DirtBallEntity(worldIn, playerIn);
        var pos = playerIn.getLookAngle().scale(2);
        dirtBallEntity.shoot(pos.x, pos.y, pos.z, 1, 1);
        worldIn.addFreshEntity(dirtBallEntity);
    }

    public static void randomDirtBallEntityShoot(Level worldIn, Player playerIn) {
        DirtBallEntity dirtBallEntity = new DirtBallEntity(worldIn, playerIn);
        var random = worldIn.getRandom();
        var randomPos = new Vec3(random.nextInt(), random.nextInt(), random.nextInt()).normalize().scale(2);
        dirtBallEntity.shoot(randomPos.x, randomPos.y, randomPos.z, 1, 1);
        worldIn.addFreshEntity(dirtBallEntity);
    }
}

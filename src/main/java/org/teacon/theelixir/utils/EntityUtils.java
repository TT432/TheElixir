package org.teacon.theelixir.utils;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import org.teacon.theelixir.entity.DirtBallEntity;

/**
 * @author DustW
 */
public class EntityUtils {
    public static void defaultDirtBallEntityShoot(World worldIn, PlayerEntity playerIn) {
        DirtBallEntity dirtBallEntity = new DirtBallEntity(worldIn, playerIn);
        Vector3d pos = playerIn.getLook(1).scale(2);
        dirtBallEntity.shoot(pos.x, pos.y, pos.z, 1, 1);
        worldIn.addEntity(dirtBallEntity);
    }
}

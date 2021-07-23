package com.nmmoc7.theelixir.network.server;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;

/**
 * @author DustW
 */
public interface IServerMessage {
    /**
     * 该说不说的，虽然idea提示你这个是没用的，但他还就真得这么写才不会爆
     * @return 安全的获取玩家
     */
    static PlayerEntity getPlayer() {
        return (PlayerEntity) (Object) Minecraft.getInstance().player;
    }
}

package com.nmmoc7.theelixir.capability;

import com.nmmoc7.theelixir.network.ModNetworkManager;
import com.nmmoc7.theelixir.network.server.FlowerSyncServer;
import com.nmmoc7.theelixir.network.server.FoxTailSyncServer;
import com.nmmoc7.theelixir.network.server.SkirtSyncServer;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

/**
 * @author DustW
 */
public class TheElixirCapability implements INBTSerializable<CompoundNBT> {
    private boolean usedElixir = false;
    /** 不要在客户端调用 */
    private ServerPlayerEntity owner;

    public int difficultyPoint;

    private boolean hasFlower;
    private int flowerSpeed = 6;

    private boolean hasFoxTail;
    boolean beAdd = false;

    public void init(ServerPlayerEntity owner) {
        this.owner = owner;
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT result = new CompoundNBT();
        result.putBoolean("used_elixir", usedElixir);
        result.putInt("difficultyPoint", difficultyPoint);
        return result;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        usedElixir = nbt.getBoolean("used_elixir");
        difficultyPoint = nbt.getInt("difficultyPoint");
    }

    public boolean isUsedElixir() {
        return usedElixir;
    }

    public void setUsedElixir(boolean usedElixir) {
        this.usedElixir = usedElixir;
    }

    public boolean isHasFlower() {
        return hasFlower;
    }

    public boolean isHasFoxTail() {
        return hasFoxTail;
    }

    public boolean isBeAdd() {
        return beAdd;
    }

    public void setBeAdd(boolean beAdd) {
        this.beAdd = beAdd;
    }

    public int getFlowerSpeed() {
        return flowerSpeed;
    }

    public void setFlowerSpeed(int flowerSpeed) {
        this.flowerSpeed = flowerSpeed;
    }

    public void setHasFoxTail(boolean hasFoxTail) {
        this.hasFoxTail = hasFoxTail;

        String threadGroupName = Thread.currentThread().getThreadGroup().getName();
        if ("SERVER".equals(threadGroupName)) {
            owner.world.getPlayers().forEach(player -> {
                ModNetworkManager.serverSendToPlayer(new FoxTailSyncServer(owner.getUniqueID(), hasFoxTail), (ServerPlayerEntity) player);
            });
        }
    }

    public void setHasFlower(boolean hasFlower) {
        this.hasFlower = hasFlower;

        String threadGroupName = Thread.currentThread().getThreadGroup().getName();
        if ("SERVER".equals(threadGroupName)) {
            owner.world.getPlayers().forEach(player -> {
                ModNetworkManager.serverSendToPlayer(new FlowerSyncServer(owner.getUniqueID(), hasFlower), (ServerPlayerEntity) player);
            });
        }
    }

    private boolean chestSkirt = false;
    private boolean normalSkirt = false;

    public void setChestSkirt(boolean chestSkirt) {
        this.chestSkirt = chestSkirt;
        this.normalSkirt = !chestSkirt;
        skirtSync();
    }

    public void closeChestSkirt() {
        this.chestSkirt = false;
        this.normalSkirt = false;
        skirtSync();
    }

    public boolean isChestSkirt() {
        return chestSkirt;
    }

    public boolean isNormalSkirt() {
        return normalSkirt;
    }

    public void skirtSync() {
        String threadGroupName = Thread.currentThread().getThreadGroup().getName();
        if ("SERVER".equals(threadGroupName)) {
            owner.world.getPlayers().forEach(player -> {
                ModNetworkManager.serverSendToPlayer(new SkirtSyncServer(owner.getUniqueID(), isChestSkirt(), isNormalSkirt()), (ServerPlayerEntity) player);
            });
        }
    }
}

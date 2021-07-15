package org.teacon.theelixir.capability;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.INBTSerializable;
import org.teacon.theelixir.network.ModNetworkManager;
import org.teacon.theelixir.network.server.FlowerSyncServer;
import org.teacon.theelixir.network.server.FoxTailSyncServer;

/**
 * @author DustW
 */
public class TheElixirCapability implements INBTSerializable<CompoundNBT> {
    private boolean usedElixir = false;
    /** 不要在客户端调用 */
    private ServerPlayerEntity owner;

    private BlockPos lastPos;
    private ServerWorld lastWorld;

    public int difficultyPoint;

    private boolean hasFlower;

    private boolean hasFoxTail;

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

    public void tick() {
        if (isUsedElixir()) {
            owner.clearActivePotions();
            owner.extinguish();

            owner.getFoodStats().setFoodLevel(1);

            if (lastWorld == null) {
                lastWorld = owner.getServerWorld();
            }

            if (lastPos == null) {
                lastPos = owner.getPosition();
            }

            if (owner.getPosition().distanceSq(lastPos) > 1000 && lastWorld == owner.getServerWorld()) {
                owner.setPosition(lastPos.getX(), lastPos.getY(), lastPos.getZ());
            }
            else {
                lastPos = owner.getPosition();
                lastWorld = owner.getServerWorld();
            }
        }
    }
}

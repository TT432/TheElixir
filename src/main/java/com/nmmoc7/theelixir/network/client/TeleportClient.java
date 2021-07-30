package com.nmmoc7.theelixir.network.client;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.server.SPlayerPositionLookPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.server.TicketType;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.function.Supplier;

/**
 * @author DustW
 */
public class TeleportClient implements IClientMessage {
    UUID playerUuid;

    public TeleportClient(UUID playerUuid) {
        this.playerUuid = playerUuid;
    }

    public static void encode(TeleportClient packet, PacketBuffer pb) {
        pb.writeUniqueId(packet.playerUuid);
    }

    public static TeleportClient decode(PacketBuffer pb) {
        return new TeleportClient(pb.readUniqueId());
    }

    public static void handle(TeleportClient packet, Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection().equals(NetworkDirection.PLAY_TO_SERVER)) {
            ctx.get().enqueueWork(() -> {
                ServerPlayerEntity player = ctx.get().getSender();
                PlayerEntity target = player.world.getPlayerByUuid(packet.playerUuid);
                teleport(player, (ServerWorld) player.world, target.getPosX(), target.getPosY(), target.getPosZ(), new HashSet<>(), player.getYaw(1), player.getPitch(1));
            });
            ctx.get().setPacketHandled(true);
        }
    }

    private static void teleport(Entity entityIn, ServerWorld worldIn, double x, double y, double z, Set<SPlayerPositionLookPacket.Flags> relativeList, float yaw, float pitch) {
        net.minecraftforge.event.entity.living.EntityTeleportEvent.TeleportCommand event = net.minecraftforge.event.ForgeEventFactory.onEntityTeleportCommand(entityIn, x, y, z);
        if (event.isCanceled()) {
            return;
        }
        x = event.getTargetX(); y = event.getTargetY(); z = event.getTargetZ();
        BlockPos blockpos = new BlockPos(x, y, z);
        if (World.isInvalidPosition(blockpos)) {
            if (entityIn instanceof ServerPlayerEntity) {
                ChunkPos chunkpos = new ChunkPos(new BlockPos(x, y, z));
                worldIn.getChunkProvider().registerTicket(TicketType.POST_TELEPORT, chunkpos, 1, entityIn.getEntityId());
                entityIn.stopRiding();
                if (((ServerPlayerEntity)entityIn).isSleeping()) {
                    ((ServerPlayerEntity)entityIn).stopSleepInBed(true, true);
                }

                if (worldIn == entityIn.world) {
                    ((ServerPlayerEntity)entityIn).connection.setPlayerLocation(x, y, z, yaw, pitch, relativeList);
                } else {
                    ((ServerPlayerEntity)entityIn).teleport(worldIn, x, y, z, yaw, pitch);
                }

                entityIn.setRotationYawHead(yaw);
            } else {
                float f1 = MathHelper.wrapDegrees(yaw);
                float f = MathHelper.wrapDegrees(pitch);
                f = MathHelper.clamp(f, -90.0F, 90.0F);
                if (worldIn == entityIn.world) {
                    entityIn.setLocationAndAngles(x, y, z, f1, f);
                    entityIn.setRotationYawHead(f1);
                } else {
                    entityIn.detach();
                    Entity entity = entityIn;
                    entityIn = entityIn.getType().create(worldIn);
                    if (entityIn == null) {
                        return;
                    }

                    entityIn.copyDataFromOld(entity);
                    entityIn.setLocationAndAngles(x, y, z, f1, f);
                    entityIn.setRotationYawHead(f1);
                    worldIn.addFromAnotherDimension(entityIn);
                }
            }

            if (!(entityIn instanceof LivingEntity) || !((LivingEntity)entityIn).isElytraFlying()) {
                entityIn.setMotion(entityIn.getMotion().mul(1.0D, 0.0D, 1.0D));
                entityIn.setOnGround(true);
            }

            if (entityIn instanceof CreatureEntity) {
                ((CreatureEntity)entityIn).getNavigator().clearPath();
            }
        }
    }
}

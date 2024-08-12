package io.github.tt432.theelixir.mixin;

import io.github.tt432.theelixir.capability.TheElixirAttachmentData;
import io.github.tt432.theelixir.network.TheElixirDataSyncPacketS2C;
import net.minecraft.server.level.ServerEntity;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.network.PacketDistributor;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author TT432
 */
@Mixin(ServerEntity.class)
public class ServerEntityMixin {
    @Shadow
    @Final
    private Entity entity;

    @Inject(method = "sendDirtyEntityData()V", at = @At("RETURN"))
    private void theElixir$sendDirtyEntityData(CallbackInfo ci) {
        if (entity.hasData(TheElixirAttachmentData.FOX_TAIL) && entity.getData(TheElixirAttachmentData.FOX_TAIL).isDirty()) {
            PacketDistributor.sendToPlayersTrackingEntityAndSelf(entity,
                    new TheElixirDataSyncPacketS2C<>(entity.getId(), entity.getData(TheElixirAttachmentData.FOX_TAIL)));
            entity.getData(TheElixirAttachmentData.FOX_TAIL).setDirty(false);
        }
    }
}

package com.nmmoc7.theelixir.support.uusi_aurinko;

import com.nmmoc7.theelixir.TheElixir;
import com.nmmoc7.theelixir.support.botania.BotaniaSupportRegistryHandler;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import vazkii.botania.api.mana.IManaItem;

/**
 * @author DustW
 */
@Mod.EventBusSubscriber
public class OnPlayerHurt {
    @SubscribeEvent
    public static void onPlayerHurt(LivingHurtEvent event) {
        if (TheElixir.uusiLoaded && TheElixir.botaniaLoaded) {
            LivingEntity entity = event.getEntityLiving();

            if (entity instanceof ServerPlayerEntity) {
                PlayerEntity player = (PlayerEntity) entity;

                if (event.getSource().damageType.equals(UusiAurinkoDamageSource.ELECTRICITY_TYPE)) {
                    ItemStack held = player.getHeldItemMainhand();

                    if (held.getItem() == BotaniaSupportRegistryHandler.ITEM_MANA_LEMON) {
                        ((IManaItem) held.getItem()).addMana(held, MathHelper.floor(event.getAmount()));
                    }
                }
            }
        }
    }
}

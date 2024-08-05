package io.github.tt432.theelixir.common;

import io.github.tt432.theelixir.capability.ElixirData;
import io.github.tt432.theelixir.capability.TheElixirAttachmentData;
import io.github.tt432.theelixir.common.item.TheElixirItems;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.scores.ScoreAccess;
import net.minecraft.world.scores.Team;
import net.minecraft.world.scores.criteria.ObjectiveCriteria;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

/**
 * @author DustW
 */
@EventBusSubscriber
public class OnPlayerDeath {
    @SubscribeEvent
    public static void onPlayerDeath(LivingDeathEvent event) {
        LivingEntity livingEntity = event.getEntity();

        if (!(livingEntity instanceof ServerPlayer serverPlayerEntity)) return;

        ElixirData capability = serverPlayerEntity.getData(TheElixirAttachmentData.ELIXIR);

        if (event.getSource().getEntity() instanceof LivingEntity attacker
                && attacker.getItemInHand(InteractionHand.MAIN_HAND).getItem() == TheElixirItems.RESTRAINER.get()) {
            if (capability.isUsedElixir()) {
                capability.setUsedElixir(false);

                livingEntity.sendSystemMessage(Component.literal("你被变回了人类"));
            }

            return;
        }

        if (!capability.isUsedElixir()) return;

        //死亡信息广播
        boolean flag = serverPlayerEntity.level().getGameRules().getBoolean(GameRules.RULE_SHOWDEATHMESSAGES);
        if (flag) {
            var itextcomponent = serverPlayerEntity.getCombatTracker().getDeathMessage();
            Team team = serverPlayerEntity.getTeam();
            if (team == null || team.getDeathMessageVisibility() == Team.Visibility.ALWAYS) {
                serverPlayerEntity.server.getPlayerList().broadcastSystemMessage(itextcomponent, false);
            } else if (team.getDeathMessageVisibility() == Team.Visibility.HIDE_FOR_OTHER_TEAMS) {
                serverPlayerEntity.server.getPlayerList().broadcastSystemToTeam(serverPlayerEntity, itextcomponent);
            } else if (team.getDeathMessageVisibility() == Team.Visibility.HIDE_FOR_OWN_TEAM) {
                serverPlayerEntity.server.getPlayerList().broadcastSystemToAllExceptTeam(serverPlayerEntity, itextcomponent);
            }
        }

        event.setCanceled(true);

        //计分板数据更新
        serverPlayerEntity.getScoreboard().forAllObjectives(ObjectiveCriteria.DEATH_COUNT,
                serverPlayerEntity, ScoreAccess::increment);

        //统计数据（被生物杀死）更新
        LivingEntity livingentity = serverPlayerEntity.getKillCredit();
        if (livingentity != null) {
            serverPlayerEntity.awardStat(Stats.ENTITY_KILLED_BY.get(livingentity.getType()));
        }

        //死亡相关统计数据更新
        serverPlayerEntity.awardStat(Stats.DEATHS);
        serverPlayerEntity.resetStat(Stats.CUSTOM.get(Stats.TIME_SINCE_DEATH));
        serverPlayerEntity.resetStat(Stats.CUSTOM.get(Stats.TIME_SINCE_REST));

        //生命回复，饱食度回复，所有效果（药水&火焰&窒息）清除，无敌5s（考虑调用玩家重生的方法，避免可能的mod自定义玩家数值无法回复）
        livingEntity.setHealth(serverPlayerEntity.getMaxHealth());

        //重置战斗纪录
        serverPlayerEntity.getCombatTracker().recheckStatus();
    }
}

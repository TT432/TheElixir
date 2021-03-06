package com.nmmoc7.theelixir.event.listener;

import com.nmmoc7.theelixir.capability.CapabilityRegistryHandler;
import com.nmmoc7.theelixir.capability.TheElixirCapability;
import com.nmmoc7.theelixir.item.Restrainer;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.IAngerable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreCriteria;
import net.minecraft.scoreboard.Team;
import net.minecraft.stats.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.Util;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.GameRules;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * @author DustW
 */
@Mod.EventBusSubscriber
public class OnPlayerDeath {
    @SubscribeEvent
    public static void onPlayerDeath(LivingDeathEvent event) {
        LivingEntity livingEntity = event.getEntityLiving();

        if (livingEntity instanceof ServerPlayerEntity) {
            ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity) livingEntity;

            TheElixirCapability capability = serverPlayerEntity.getCapability(CapabilityRegistryHandler.THE_ELIXIR_CAPABILITY).orElse(null);

            if (!livingEntity.world.isRemote && event.getSource().getTrueSource() instanceof LivingEntity) {
                LivingEntity attacker = (LivingEntity) event.getSource().getTrueSource();

                if (attacker.getHeldItem(Hand.MAIN_HAND).getItem() instanceof Restrainer) {
                    if (capability.isUsedElixir()) {
                        capability.setUsedElixir(false);

                        livingEntity.sendMessage(new StringTextComponent("?????????????????????"), event.getSource().getTrueSource().getUniqueID());

                        if (event.getSource().getTrueSource() instanceof PlayerEntity) {
                            event.getSource().getTrueSource().sendMessage(new StringTextComponent(String.format("?????? %s ???????????????", livingEntity.getDisplayName().getString())), livingEntity.getUniqueID());
                        }
                    }
                    return;
                }
            }

            if (capability.isUsedElixir()) {
                //??????????????????
                boolean flag = serverPlayerEntity.world.getGameRules().getBoolean(GameRules.SHOW_DEATH_MESSAGES);
                if (flag) {
                    ITextComponent itextcomponent = serverPlayerEntity.getCombatTracker().getDeathMessage();
                    Team team = serverPlayerEntity.getTeam();
                    if (team != null && team.getDeathMessageVisibility() != Team.Visible.ALWAYS) {
                        if (team.getDeathMessageVisibility() == Team.Visible.HIDE_FOR_OTHER_TEAMS) {
                            serverPlayerEntity.server.getPlayerList().sendMessageToAllTeamMembers(serverPlayerEntity, itextcomponent);
                        } else if (team.getDeathMessageVisibility() == Team.Visible.HIDE_FOR_OWN_TEAM) {
                            serverPlayerEntity.server.getPlayerList().sendMessageToTeamOrAllPlayers(serverPlayerEntity, itextcomponent);
                        }
                    } else {
                        serverPlayerEntity.server.getPlayerList().func_232641_a_(itextcomponent, ChatType.SYSTEM, Util.DUMMY_UUID);
                    }
                }

                capability.difficultyPoint += 1;

                //???????????????????????????
                event.setCanceled(true);

                //???????????????????????????func_241157_eT_()???
                if (serverPlayerEntity.world.getGameRules().getBoolean(GameRules.FORGIVE_DEAD_PLAYERS)) {
                    forgivePlayer(serverPlayerEntity);
                }

                //?????????????????????
                serverPlayerEntity.getWorldScoreboard().forAllObjectives(ScoreCriteria.DEATH_COUNT, serverPlayerEntity.getScoreboardName(), Score::incrementScore);

                //???????????????????????????????????????
                LivingEntity damageSource = serverPlayerEntity.getAttackingEntity();
                if (damageSource != null) {
                    serverPlayerEntity.addStat(Stats.ENTITY_KILLED_BY.get(damageSource.getType()));
                    CriteriaTriggers.ENTITY_KILLED_PLAYER.trigger(serverPlayerEntity, damageSource, event.getSource());
                }

                //??????????????????????????????
                serverPlayerEntity.addStat(Stats.DEATHS);
                serverPlayerEntity.takeStat(Stats.CUSTOM.get(Stats.TIME_SINCE_DEATH));
                serverPlayerEntity.takeStat(Stats.CUSTOM.get(Stats.TIME_SINCE_REST));

                net.minecraftforge.fml.hooks.BasicEventHooks.firePlayerRespawnEvent(serverPlayerEntity, false);

                //??????????????????????????????????????????????????????&??????&????????????????????????5s??????????????????????????????????????????????????????mod????????????????????????????????????
                livingEntity.setHealth(serverPlayerEntity.getMaxHealth());

                //??????????????????
                serverPlayerEntity.getCombatTracker().reset();
            }
        }
    }

    public static void forgivePlayer(ServerPlayerEntity player)
    {
        AxisAlignedBB axisalignedbb = (new AxisAlignedBB(player.getPosition())).grow(32.0D, 10.0D, 32.0D);
        player.world
                .getLoadedEntitiesWithinAABB(MobEntity.class, axisalignedbb)
                .stream()
                .filter((entity) -> entity instanceof IAngerable)
                .forEach((mobEntity) -> ((IAngerable) mobEntity).func_233681_b_(player));
    }
}

package com.nmmoc7.theelixir.config;

import com.google.common.collect.Lists;
import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

/**
 * @author DustW
 */
public class ServerConfigs extends LoadListeningConfigManagerAbstract {
    private static final Pair<ServerConfigs, ForgeConfigSpec> CONFIGURE = new ForgeConfigSpec.Builder().configure(ServerConfigs::new);
    private static final ForgeConfigSpec CONFIG_SPEC = CONFIGURE.getRight();
    public static final ServerConfigs INSTANCE = CONFIGURE.getLeft();

    public final ForgeConfigSpec.ConfigValue<Boolean> SWALLOW_NOT_LIVING;
    public final ForgeConfigSpec.ConfigValue<Boolean> SWALLOW_PLAYER;
    public final EntityTypeTaggedListConfigValue SWALLOW_BLACK_LIST;

    public ServerConfigs(ForgeConfigSpec.Builder builder) {
        SWALLOW_NOT_LIVING = ConfigUtils.defineBoolean(builder,
                "吞食天地是否可以攻击非Living实体 (即，没有血条)",
                "swallow_can_attack_nl", true);
        SWALLOW_PLAYER = ConfigUtils.defineBoolean(builder,
                "吞食天地是否可以攻击玩家",
                "swallow_can_attack_p", false);
        SWALLOW_BLACK_LIST = new EntityTypeTaggedListConfigValue(ConfigUtils.defineList(builder,
                "吞食天地伤害黑名单，在这里面的不会被伤害\n" +
                        "允许使用tag和生物注册名",
                "swallow_attack_black",
                Lists.newArrayList("item_frame", "painting")
        ), this);
    }

    @Override
    public ForgeConfigSpec getSpec() {
        return CONFIG_SPEC;
    }
}

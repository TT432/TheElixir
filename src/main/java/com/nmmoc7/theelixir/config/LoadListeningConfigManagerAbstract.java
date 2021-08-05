package com.nmmoc7.theelixir.config;

import com.google.common.collect.Lists;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.config.ModConfig;

import java.util.List;
import java.util.function.Predicate;

/**
 * @author DustW
 */
public abstract class LoadListeningConfigManagerAbstract {
    private final List<ILoadListeningConfigValue> listeningConfigs = Lists.newArrayList();

    final <T extends ILoadListeningConfigValue> T register(T value) {
        listeningConfigs.add(value);
        return value;
    }

    protected abstract ForgeConfigSpec getSpec();

    public final void onConfigLoadOrReload(ModConfig.ModConfigEvent configEvent) {
        if (configEvent.getConfig().getSpec() == this.getSpec()) {
            if (configEvent instanceof ModConfig.Loading) {
                listeningConfigs.forEach(ILoadListeningConfigValue::onLoad);
            }
            else if (configEvent instanceof ModConfig.Reloading) {
                listeningConfigs.forEach(ILoadListeningConfigValue::onReload);
            }
        }
    }
}

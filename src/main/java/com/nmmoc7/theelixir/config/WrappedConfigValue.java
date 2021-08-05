package com.nmmoc7.theelixir.config;

import net.minecraftforge.common.ForgeConfigSpec;

/**
 * @author DustW
 */
public abstract class WrappedConfigValue<T> implements ILoadListeningConfigValue {
    private final ForgeConfigSpec.ConfigValue<T> configValue;

    protected WrappedConfigValue(ForgeConfigSpec.ConfigValue<T> configValue, LoadListeningConfigManagerAbstract manager) {
        this.configValue = configValue;
        manager.register(this);
    }

    public final T get() {
        return configValue.get();
    }
}

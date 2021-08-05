package com.nmmoc7.theelixir.config;

/**
 * @author DustW
 */
public interface ILoadListeningConfigValue {
    /**
     * 加载时调用
     */
    void onLoad();
    /**
     * 重载时调用
     */
    void onReload();
}

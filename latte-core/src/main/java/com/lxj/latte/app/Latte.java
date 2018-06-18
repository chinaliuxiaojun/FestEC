package com.lxj.latte.app;

import android.content.Context;
import android.os.Handler;

import java.util.HashMap;
import java.util.WeakHashMap;

/**
 * Created by lxj on 2018/6/10.
 * 对外工具类
 */

public final class Latte {
    public static Configurator init(Context context) {
        Configurator.getInstance().getLatteConfigs()
                .put(ConfigKeys.APPLICATION_CONTEXT, context.getApplicationContext());
        /**getConfigurations().put(ConfigType.APPLICATION_CONTEXT.name()
         ,context.getApplicationContext());**/
        return Configurator.getInstance();
    }

    public static Configurator getConfigurator() {
        return Configurator.getInstance();
    }

    public static <T> T getConfiguration(Object key) {
        return getConfigurator().getConfiguration(key);
    }


    public static Context getApplicationContext() {
        return getConfiguration(ConfigKeys.APPLICATION_CONTEXT);
    }

    public static Handler getHandler() {
        return getConfiguration(ConfigKeys.HANDLER);
    }
}

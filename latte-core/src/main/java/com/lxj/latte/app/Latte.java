package com.lxj.latte.app;

import android.content.Context;

import java.util.HashMap;
import java.util.WeakHashMap;

/**
 * Created by lxj on 2018/6/10.
 * 对外工具类
 */

public final class Latte {
    public  static Configurator init(Context context){
        getConfigurations().put(ConfigType.APPLICATION_CONTEXT.name()
                ,context.getApplicationContext());
        return Configurator.getInstance();
    }
    public  static HashMap<String,Object> getConfigurations(){
        return Configurator.getInstance().getLatteConfigs();
    }
    public static Context getApplication(){
        return (Context) getConfigurations().get(ConfigType.APPLICATION_CONTEXT.name());
    }
}

package com.lxj.latte.app;

/**
 * Created by lxj on 2018/6/13.
 * 枚举类是唯一单例，且只会初始化一次
 * 我们要进行多线程操作的时候，用枚举类来进行安全的惰性的单例的初始化，线程安全的懒汉模式
 */

public enum  ConfigKeys {
    API_HOST,//网络请求域名
    APPLICATION_CONTEXT,//全局上下文
    CONFIG_READY,//控制初始化、配置是否完成
    ICON,//存储字体的初始化
    LOADER_DELAYED,
    INTERCEPTOR,
    WE_CHAT_APP_ID,
    WE_CHAT_APP_SECRET,
    ACTIVITY,
    HANDLER,
    JAVASCRIPT_INTERFACE
}

package com.lxj.latte.app;

import android.graphics.drawable.Icon;
import android.os.Handler;
import android.text.Layout;

import com.joanzapata.iconify.IconFontDescriptor;
import com.joanzapata.iconify.Iconify;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.WeakHashMap;

import okhttp3.Interceptor;

/**
 * 管理配置/配置文件存储、获取
 * Created by lxj on 2018/6/10.
 */

public class Configurator {
    //保存ConfigKeys的数据
    private static final HashMap<Object,Object> LATTE_CONFIGS=new HashMap<>();
    private static final ArrayList<IconFontDescriptor> ICONS=new ArrayList<>();
    private static final Handler HANDLER = new Handler();
    //拦截器
    private static final ArrayList<Interceptor> INTERCEPTORS=new ArrayList<>();

    //构造函数，初始化时，向ConfigKeys加入数据
    private Configurator(){
        LATTE_CONFIGS.put(ConfigKeys.CONFIG_READY.name(),false);
        LATTE_CONFIGS.put(ConfigKeys.HANDLER, HANDLER);
    }
    public static Configurator getInstance(){
        return Holder.INSTANCE;
    }

    //获取实例
    final HashMap<Object,Object>getLatteConfigs(){
        return LATTE_CONFIGS;
    }
    //持有者，内部静态类
    private  static class Holder{
        //实例
        private  static final Configurator INSTANCE=new Configurator();
    }
    //最后调用，配置完成
    public  final  void configure(){
        initIcons();
        LATTE_CONFIGS.put(ConfigKeys.CONFIG_READY,true);
    }
    public  final Configurator withApiHost(String host){
        LATTE_CONFIGS.put(ConfigKeys.API_HOST,host);
        return  this;
    }
    private  void initIcons(){
        if(ICONS.size()>0){
            final Iconify.IconifyInitializer initializer=Iconify.with(ICONS
                    .get(0));
            for(int i=1;i<ICONS.size();i++){
                initializer.with(ICONS.get(i));
            }
        }
    }

    public final Configurator withIcon(IconFontDescriptor descriptor){
        ICONS.add(descriptor);
        return this;
    }
    //
    public final Configurator withLoaderDelayed(long delayed) {
        LATTE_CONFIGS.put(ConfigKeys.LOADER_DELAYED, delayed);
        return this;
    }
    //拦截器的调用函数
    public final Configurator withInterceptor(Interceptor interceptor){
        INTERCEPTORS.add(interceptor);
        LATTE_CONFIGS.put(ConfigKeys.INTERCEPTOR,INTERCEPTORS);
        return this;
    }
    public final Configurator withInterceptors(ArrayList<Interceptor>interceptors){
        INTERCEPTORS.addAll(interceptors);
        LATTE_CONFIGS.put(ConfigKeys.INTERCEPTOR,INTERCEPTORS);
        return this;
    }
    private void checkConfiguration(){
        final  boolean isReady= (boolean) LATTE_CONFIGS.get(ConfigKeys.CONFIG_READY);
        if(!isReady){
            throw  new RuntimeException("配置未完成");
        }
    }
    @SuppressWarnings("unchecked")
    final <T> T getConfiguration(Object key){
        checkConfiguration();
        final Object value = LATTE_CONFIGS.get(key);
        if (value == null) {
            throw new NullPointerException(key.toString() + " IS NULL");
        }
        return (T) LATTE_CONFIGS.get(key);
    }
}

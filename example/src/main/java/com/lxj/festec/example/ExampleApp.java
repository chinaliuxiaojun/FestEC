package com.lxj.festec.example;

import android.app.Application;

import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.lxj.latte.app.Latte;
import com.lxj.latte.ec.icon.FontEcModule;
import com.lxj.latte.net.interceptors.DebugInterceptor;

/**
 * Created by lxj on 2018/6/10.
 */

public class ExampleApp extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        Latte.init(this)
                .withIcon(new FontAwesomeModule())
                .withIcon(new FontEcModule())
                .withLoaderDelayed(1000)
                .withApiHost("http://127.0.0.1")
                .withInterceptor(new DebugInterceptor("index",R.raw.text))
                .configure();
    }
}

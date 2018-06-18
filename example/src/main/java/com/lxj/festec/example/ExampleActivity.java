package com.lxj.festec.example;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.lxj.latte.activity.ProxyActivity;
import com.lxj.latte.app.Latte;
import com.lxj.latte.delegates.LatteDelegate;
import com.lxj.latte.ec.launcher.LauncherDelehate;

public class ExampleActivity extends ProxyActivity {


    @Override
    public LatteDelegate setRootDelegare() {
        //return new ExampleDelegate()
        return new LauncherDelehate();
    }
}

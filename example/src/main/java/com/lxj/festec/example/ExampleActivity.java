package com.lxj.festec.example;

import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.lxj.latte.activity.ProxyActivity;
import com.lxj.latte.app.Latte;
import com.lxj.latte.delegates.LatteDelegate;
import com.lxj.latte.ec.launcher.LauncherDelehate;
import com.lxj.latte.ec.launcher.LauncherScrollDelegate;
import com.lxj.latte.ec.sign.ISignListener;
import com.lxj.latte.ec.sign.SignInDelegate;
import com.lxj.latte.ec.sign.SignUpDelegate;
import com.lxj.latte.ui.launcher.ILauncherListener;
import com.lxj.latte.ui.launcher.OnLauncherFinshTag;

/**
 * 实现ISignListener的接口
 **/
public class ExampleActivity extends ProxyActivity implements
        ISignListener,
        ILauncherListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }

    @Override
    public LatteDelegate setRootDelegare() {
        //return new ExampleDelegate()；//第一个界面
        return new LauncherDelehate();//广告图
        // return new LauncherScrollDelegate();//轮播图
        //return new SignUpDelegate();//注册界面
        //return new SignInDelegate();//登录界面
    }

    @Override
    public void onSignInSuccess() {
        Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSignUpSuccess() {
        Toast.makeText(this, "账号注册成功", Toast.LENGTH_SHORT).show();
    }

    /**实现接口**/
    @Override
    public void onLauncherFinsh(OnLauncherFinshTag tag) {

        switch (tag) {
            case SIGEND:
                Toast.makeText(this,"启动结束，用户登录了",Toast.LENGTH_SHORT).show();
                startWithPop(new ExampleDelegate());
                break;
            case NOT_SIGNED:
                Toast.makeText(this,"启动结束，用户没登录",Toast.LENGTH_SHORT).show();
                startWithPop(new SignInDelegate());
                break;
            default:
                break;
        }
    }
}

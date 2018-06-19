package com.lxj.latte.ec.launcher;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;


import com.lxj.latte.app.AccountManager;
import com.lxj.latte.app.IUserChecker;
import com.lxj.latte.delegates.LatteDelegate;
import com.lxj.latte.ec.R;
import com.lxj.latte.ec.R2;
import com.lxj.latte.ui.launcher.ILauncherListener;
import com.lxj.latte.ui.launcher.OnLauncherFinshTag;
import com.lxj.latte.ui.launcher.ScrollLanucherTag;
import com.lxj.latte.util.storage.LattePreference;
import com.lxj.latte.util.timer.BaseTimerTask;
import com.lxj.latte.util.timer.ITimerListener;

import java.text.MessageFormat;
import java.util.Timer;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 滑动启动页
 * Created by lxj on 2018/6/18.
 */

public class LauncherDelehate extends LatteDelegate implements ITimerListener{

    @BindView(R2.id.tv_launcher_timer)
    AppCompatTextView mTvTimer=null;

    private Timer mTimer=null;
    private int mCount=3;

    @OnClick(R2.id.tv_launcher_timer)
    void onClickTimerView(){
        if(mTimer!=null){
            mTimer.cancel();
            mTimer=null;
            checkIsShowScroll();
        }
    }
    private void initTimer(){
    mTimer=new Timer();
    final BaseTimerTask task=new BaseTimerTask(this);
    mTimer.schedule(task,0,1000);
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_launcher;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        initTimer();
    }
    //判断是否显示滑动启动页
    private void checkIsShowScroll(){
        if(!LattePreference.getAppFlag(ScrollLanucherTag.HAS_FIRST_LAUNCHER_APP.name())){
            start(new LauncherScrollDelegate(),SINGLETASK);//SINGLETASK启动代理模式
        }else {
            //检查用户是否登录APP
            AccountManager.checkAccount(new IUserChecker() {
                @Override
                public void onSignIn() {
                if(mILauncherListener!=null){
                    mILauncherListener.onLauncherFinsh(OnLauncherFinshTag.SIGEND);
                }
                }

                @Override
                public void onNoSignIn() {
                if(mILauncherListener!=null){
                    mILauncherListener.onLauncherFinsh(OnLauncherFinshTag.NOT_SIGNED);
                }
                }
            });
        }
    }

private ILauncherListener mILauncherListener=null;
    /**广告界面完成监听回调，把mILauncherListener传给完成该接口的类，既ExampActivity**/
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity instanceof ILauncherListener){
            mILauncherListener= (ILauncherListener) activity;
        }
    }

    @Override
    public void onTimer() {
        //取得getProxyActivity
        getProxyActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(mTvTimer!=null){
                    mTvTimer.setText(MessageFormat.format("跳过\n{0}s",mCount));
                    mCount--;
                    if(mCount<0){
                        if(mTimer!=null){
                            mTimer.cancel();
                            mTimer=null;
                            checkIsShowScroll();
                        }
                    }
                }
            }
        });
    }
}

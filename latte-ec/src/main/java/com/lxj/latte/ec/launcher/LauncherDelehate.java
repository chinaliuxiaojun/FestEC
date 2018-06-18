package com.lxj.latte.ec.launcher;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;


import com.lxj.latte.delegates.LatteDelegate;
import com.lxj.latte.ec.R;
import com.lxj.latte.ec.R2;
import com.lxj.latte.util.timer.BaseTimerTask;
import com.lxj.latte.util.timer.ITimerListener;

import java.text.MessageFormat;
import java.util.Timer;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;


/**
 * Created by lxj on 2018/6/18.
 */

public class LauncherDelehate extends LatteDelegate implements ITimerListener{

    @BindView(R2.id.tv_launcher_timer)
    AppCompatTextView mTvTimer=null;

    private Timer mTimer=null;
    private int mCount=3;

    @OnClick(R2.id.tv_launcher_timer)
    void onClickTimerView(){

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
                        }
                    }
                }
            }
        });
    }
}

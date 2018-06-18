package com.lxj.latte.util.timer;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by lxj on 2018/6/18.
 */

public class BaseTimerTask extends TimerTask{
    private ITimerListener mITimerListener=null;

    public BaseTimerTask(ITimerListener timerListener) {
        this.mITimerListener = timerListener;
    }

    @Override
    public void run() {
        if (mITimerListener!=null){
            mITimerListener.onTimer();
        }
    }
}

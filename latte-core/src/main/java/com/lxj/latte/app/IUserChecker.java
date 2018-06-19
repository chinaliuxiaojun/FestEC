package com.lxj.latte.app;

/**
 * Created by lxj on 2018/6/19.
 * 登录的时候回调接口，判断完成后执行，如果已登录过，执行登录操作，如果没有，执行没有的操作
 */

public interface IUserChecker {
    void onSignIn();
    void  onNoSignIn();
}


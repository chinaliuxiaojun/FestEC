package com.lxj.latte.ec.sign;

/**
 * Created by lxj on 2018/6/19.
 */

public interface ISignListener {
    /**
     * 登录成功的回调
     **/
    void onSignInSuccess();

    /**
     * 注册成功的回调
     **/
    void onSignUpSuccess();
}

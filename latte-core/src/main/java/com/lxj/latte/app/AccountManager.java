package com.lxj.latte.app;

import com.lxj.latte.util.storage.LattePreference;

/**
 * Created by lxj on 2018/6/19.
 * 用来管理用户信息
 * 查看登录状态
 */

public class AccountManager {
    private enum SignTag {
        SIGN_TAG

    }

    /**
     * 设置用户登录状态，登录后调用
     **/
    public static void setSignState(boolean state) {
        LattePreference.setAppFlag(SignTag.SIGN_TAG.name(), state);
    }

    /**
     * 判断用户是否已经登录
     **/
    private static boolean isSignIn() {
        return LattePreference.getAppFlag(SignTag.SIGN_TAG.name());
    }

    /**
     * 传入之前写的接口
     **/
    public static void checkAccount(IUserChecker checker) {
        if (isSignIn()) {
            checker.onSignIn();
        } else {
            checker.onNoSignIn();
        }
    }
}

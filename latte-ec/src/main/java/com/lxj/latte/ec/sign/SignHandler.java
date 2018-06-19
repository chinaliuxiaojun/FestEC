package com.lxj.latte.ec.sign;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lxj.latte.app.AccountManager;
import com.lxj.latte.ec.database.DataBaseManager;
import com.lxj.latte.ec.database.UserProfile;


/**
 * Created by lxj on 2018/6/19.
 */

public class SignHandler {

    /**
     * 点击注册后，在此将数据存入数据库
     **/
    public static void onSignUp(String response, ISignListener signListener) {
        final JSONObject profileJson = JSON.parseObject(response).getJSONObject("data");
        final long userId = profileJson.getLong("userId");
        final String name = profileJson.getString("name");
        final String avatar = profileJson.getString("avatar");
        final String gender = profileJson.getString("gender");
        final String address = profileJson.getString("address");
        //初始化
        final UserProfile profile = new UserProfile(userId, name, avatar, gender, address);
        DataBaseManager.getInstance().getDao().insert(profile);
        /**已经注册并登录成功**/
        AccountManager.setSignState(true);
        signListener.onSignUpSuccess();
    }
    /****/
    public static void onSignIn(String response, ISignListener signListener) {
        final JSONObject profileJson = JSON.parseObject(response).getJSONObject("data");
        final long userId = profileJson.getLong("userId");
        final String name = profileJson.getString("name");
        final String avatar = profileJson.getString("avatar");
        final String gender = profileJson.getString("gender");
        final String address = profileJson.getString("address");
        //初始化
        final UserProfile profile = new UserProfile(userId, name, avatar, gender, address);
        DataBaseManager.getInstance().getDao().insert(profile);
        /**已经注册并登录成功**/
        AccountManager.setSignState(true);
        signListener.onSignInSuccess();
    }
}

package com.lxj.latte.ec.sign;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.lxj.latte.delegates.LatteDelegate;
import com.lxj.latte.ec.R;
import com.lxj.latte.ec.R2;
import com.lxj.latte.net.RestClient;
import com.lxj.latte.net.callback.IFailure;
import com.lxj.latte.net.callback.ISuccess;
import com.lxj.latte.util.log.LatteLogger;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lxj on 2018/6/19.
 */

public class SignInDelegate extends LatteDelegate {
   @BindView(R2.id.edit_sign_in_email)
   TextInputEditText mEmail=null;
    @BindView(R2.id.edit_sign_in_password)
    TextInputEditText mPassword=null;

    private ISignListener mISignListener = null;
    /**
     * 将全局的Activity传入到接口里，把mISignListener传给完成该接口的类，既ExampActivity
     **/
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ISignListener) {
            mISignListener = (ISignListener) activity;
        }
    }
/**点击登录按钮产生的数据获取**/
    @OnClick(R2.id.btn_sign_in)
    void onClickSignIn(){
        if (checkForm()) {
            //可以向服务器提交信息了
            RestClient.builder()
                    .url("http://192.168.0.102:8090/RestServer/api/user_profile.php ")
                    .params("email", mEmail.getText().toString())
                    .params("password", mPassword.getText().toString())
                    .success(new ISuccess() {
                        @Override
                        public void onSuccess(String response) {
                            LatteLogger.json("USER_PROFILE", response);
                            SignHandler.onSignIn(response, mISignListener);
                        }
                    })
                    .failure(new IFailure(){
                        @Override
                        public void onFailure() {
                            Toast.makeText(getContext(),"接连不上服务器",Toast.LENGTH_SHORT).show();
                        }
                    })
                    .build()
                    .post();
            // Toast.makeText(getContext(),"验证通过",Toast.LENGTH_SHORT).show();
        }
    }
    @OnClick(R2.id.icon_sign_in_wechat)
    void onClickWeChat(){

    }
    //点击还未进行注册？
    @OnClick(R2.id.tv_link_sign_up)
    void onClickLink(){
      start(new SignUpDelegate());
    }
    private boolean checkForm(){
        final String email=mEmail.getText().toString();
        final String password=mPassword.getText().toString();
        boolean isPass=true;
        //Patterns.EMAIL_ADDRESS.matcher(email).matches()校验邮箱
        if(email.isEmpty()||!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            mEmail.setError("错误的邮箱格式");
            isPass=false;
        }else {
            mEmail.setError(null);
        }
        if(password.isEmpty()||password.length()<6){
            mPassword.setError("请输入至少6位数的密码");
            isPass=false;
        }else {
            mPassword.setError(null);
        }
        return isPass;
    }
    @Override
    public Object setLayout() {
        return R.layout.delegate_sign_in;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }
}

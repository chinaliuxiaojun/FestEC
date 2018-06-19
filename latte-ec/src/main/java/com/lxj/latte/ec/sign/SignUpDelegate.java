package com.lxj.latte.ec.sign;

import android.app.Activity;
import android.content.Context;
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
 * Created by lxj on 2018/6/18.
 */

public class SignUpDelegate extends LatteDelegate {
    @BindView(R2.id.edit_sign_up_name)
    TextInputEditText mName = null;
    @BindView(R2.id.edit_sign_up_email)
    TextInputEditText mEmail = null;
    @BindView(R2.id.edit_sign_up_phone)
    TextInputEditText mPhone = null;
    @BindView(R2.id.edit_sign_up_password)
    TextInputEditText mPassword = null;
    @BindView(R2.id.edit_sign_up_re_password)
    TextInputEditText mRePassword = null;


    private ISignListener mISignListener = null;
    /**
     * 将全局的Activity传入到接口里，把mISignListener传给完成该接口的类既ExampActivity
     **/
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ISignListener) {
            mISignListener = (ISignListener) activity;
        }
    }

    @OnClick(R2.id.btn_sign_up)
    void onClickSignUp() {
        if (checkForm()) {
            //可以向服务器提交信息了
            RestClient.builder()
                    .url("http://192.168.0.102:8090/RestServer/api/user_profile.php ")
                    .params("name", mName.getText().toString())// 传入提交表单的信息
                    .params("email", mEmail.getText().toString())
                    .params("phone", mPhone.getText().toString())
                    .params("password", mPassword.getText().toString())
                    .success(new ISuccess() {
                        @Override
                        public void onSuccess(String response) {
                            LatteLogger.json("USER_PROFILE", response);
                            SignHandler.onSignUp(response, mISignListener);
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

    //点击已有账户？产生的数据获取
    @OnClick(R2.id.tv_link_sign_in)
    void onClickLink() {
        start(new SignInDelegate());
    }

    private boolean checkForm() {
        final String name = mName.getText().toString();
        final String email = mEmail.getText().toString();
        final String phone = mPhone.getText().toString();
        final String password = mPassword.getText().toString();
        final String rePasswore = mRePassword.getText().toString();
        boolean isPass = true;
        if (name.isEmpty()) {
            mName.setError("请输入姓名");
            isPass = false;
        } else {
            mName.setError(null);
        }
        //Patterns.EMAIL_ADDRESS.matcher(email).matches()校验邮箱
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mEmail.setError("错误的邮箱格式");
            isPass = false;
        } else {
            mEmail.setError(null);
        }

        if (phone.isEmpty() || phone.length() != 11) {
            mPhone.setError("手机号码错误");
            isPass = false;
        } else {
            mPhone.setError(null);
        }
        if (password.isEmpty() || password.length() < 6) {
            mPassword.setError("请输入至少6位数的密码");
            isPass = false;
        } else {
            mPassword.setError(null);
        }
        if (rePasswore.isEmpty() || rePasswore.length() < 6 || !(rePasswore.equals(password))) {
            mRePassword.setError("两次密码需一致");
            isPass = false;
        } else {
            mRePassword.setError(null);
        }
        return isPass;
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_sign_up;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }
}

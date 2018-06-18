package com.lxj.festec.example;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.lxj.latte.delegates.LatteDelegate;
import com.lxj.latte.net.RestClient;
import com.lxj.latte.net.callback.IError;
import com.lxj.latte.net.callback.IFailure;
import com.lxj.latte.net.callback.ISuccess;

import static retrofit2.Response.success;

/**
 * Created by lxj on 2018/6/11.
 */

public class ExampleDelegate extends LatteDelegate {
    @Override
    public Object setLayout() {
        return R.layout.delegate_example;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        testRestClient();
    }

    private void testRestClient() {
        RestClient.builder()
                .url("http://127.0.0.1/index/")//http://192.168.0.102/index  http://news.baidu.com/
                .loader(getContext())//进度圈
                //  .params("", "")
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        Toast.makeText(getContext(),response,Toast.LENGTH_LONG).show();
                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {

                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String msg) {
                    }
                })
                .build()
                .get();
    }
}

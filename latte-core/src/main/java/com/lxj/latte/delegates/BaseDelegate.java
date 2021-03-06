package com.lxj.latte.delegates;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lxj.latte.activity.ProxyActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation_swipeback.SwipeBackFragment;

/**
 * Created by lxj on 2018/6/11.
 * abstract 抽象类，不可new出实例
 */

public abstract class BaseDelegate extends SwipeBackFragment {

    @SuppressWarnings("SpellCheckingInspection")
    public Unbinder mUnbinder=null;
    public  abstract Object setLayout();
    public  abstract void onBindView(@Nullable Bundle savedInstanceState,View rootView);
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView=null;
        if(setLayout() instanceof  Integer){
            rootView=inflater.inflate((Integer) setLayout(),container,false);
        }else if(setLayout() instanceof View){
            rootView= (View) setLayout();
        }else {
            throw new ClassCastException("setLayout() type must be int or View!");
        }
        if(rootView!=null){
            mUnbinder= ButterKnife.bind(this,rootView);
            onBindView(savedInstanceState,rootView);
        }
        return rootView;
    }

    public final ProxyActivity getProxyActivity(){
        return (ProxyActivity) _mActivity;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(mUnbinder !=null){
            mUnbinder.unbind();
        }
    }
}

package com.lxj.latte.ec.launcher;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.lxj.latte.app.AccountManager;
import com.lxj.latte.app.IUserChecker;
import com.lxj.latte.delegates.LatteDelegate;
import com.lxj.latte.ec.R;
import com.lxj.latte.ui.launcher.ILauncherListener;
import com.lxj.latte.ui.launcher.LauncherHolderCreator;
import com.lxj.latte.ui.launcher.OnLauncherFinshTag;
import com.lxj.latte.ui.launcher.ScrollLanucherTag;
import com.lxj.latte.util.storage.LattePreference;

import java.util.ArrayList;

/**
 * 轮播启动页
 * Created by lxj on 2018/6/18.
 */

public class LauncherScrollDelegate extends LatteDelegate implements OnItemClickListener {
    /**
     * ConvenientBanner
     * 页面翻转控件，极方便的广告栏
     * 支持无限循环，自动翻页，翻页特效
     * <p>
     * 支持自动翻页
     **/
    private ConvenientBanner<Integer> mConvenientBanner = null;
    private static final ArrayList<Integer> INTEGERS = new ArrayList<>();

    private void initBanner() {
        INTEGERS.add(R.mipmap.launcher_01);
        INTEGERS.add(R.mipmap.launcher_02);
        INTEGERS.add(R.mipmap.launcher_03);
        INTEGERS.add(R.mipmap.launcher_04);
        INTEGERS.add(R.mipmap.launcher_05);
        mConvenientBanner.setPages(new LauncherHolderCreator(), INTEGERS)
                .setPageIndicator(new int[]{R.drawable.dot_normal, R.drawable.dot_focus})//下部旋转点
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .setOnItemClickListener(this)
                .setCanLoop(false);
    }

    private ILauncherListener mILauncherListener=null;
    /**轮播完成监听回调，把mILauncherListener传给完成该接口的类，既ExampActivity**/
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity instanceof ILauncherListener){
            mILauncherListener= (ILauncherListener) activity;
        }
    }
    @Override
    public Object setLayout() {
        mConvenientBanner=new ConvenientBanner<>(getContext());
        return mConvenientBanner;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        initBanner();
    }

    //按下做的操作//判断是否是第一次启动增加的事件
    @Override
    public void onItemClick(int position) {
        //减1 ，如果点击的是最后一个
        if(position==INTEGERS.size()-1){
            LattePreference.setAppFlag(ScrollLanucherTag.HAS_FIRST_LAUNCHER_APP.name(),true);
            //检查用户是否已经登录
            //检查用户是否登录APP
            AccountManager.checkAccount(new IUserChecker() {
                @Override
                public void onSignIn() {
                    if(mILauncherListener!=null){
                        mILauncherListener.onLauncherFinsh(OnLauncherFinshTag.SIGEND);
                    }
                }

                @Override
                public void onNoSignIn() {
                    if(mILauncherListener!=null){
                        mILauncherListener.onLauncherFinsh(OnLauncherFinshTag.NOT_SIGNED);
                    }
                }
            });
        }
    }
}

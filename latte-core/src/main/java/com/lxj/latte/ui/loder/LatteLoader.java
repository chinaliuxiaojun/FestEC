package com.lxj.latte.ui.loder;

import android.content.Context;
import android.support.v7.app.AppCompatDialog;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.lxj.latte.util.dimen.DimenUtil;
import com.lxj.latte.R;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;

/**
 * Created by lxj on 2018/6/13.
 */

public class LatteLoader {
    private  static final int LOADER_SIZE_SCALE=8;
    private static final int LOADER_OFFSET_SCALE=10;

    private static final ArrayList<AppCompatDialog>LOADERS=new ArrayList<>();

    public static void showLoading(Context context,Enum<LoaderStyle> type){
        showLoading(context,type.name());
    }
    private static final String DEFAULT_LOADER1 =LoaderStyle.BallPulseIndicator.name();


    public  static void showLoading(Context context,String type){
        final AppCompatDialog dialog=new AppCompatDialog(context, R.style.dialog);
        final AVLoadingIndicatorView avLoadingIndicatorView=
                new LoaderCreator().create(type,context);
        dialog.setContentView(avLoadingIndicatorView);
        int deviceWidth= DimenUtil.getScreenWith();
        int deviceHeight= DimenUtil.getScreenHeight();
        final Window dialogWindow=dialog.getWindow();
        if(dialogWindow!=null){
            WindowManager.LayoutParams lp=dialogWindow.getAttributes();
            lp.width=deviceWidth/LOADER_SIZE_SCALE;
            lp.height=deviceHeight/LOADER_SIZE_SCALE;
            lp.height=lp.height+deviceHeight/LOADER_OFFSET_SCALE;
            lp.gravity= Gravity.CENTER;
        }
        LOADERS.add(dialog);
        dialog.show();
    }
    public  static void showLoading(Context context) {
        showLoading(context, DEFAULT_LOADER1);
    }
    public static void stopLoading(){
        for (AppCompatDialog dialog:LOADERS) {
            if(dialog!=null){
                if(dialog.isShowing()){
                    dialog.cancel();
                }
            }
        }
    }
}

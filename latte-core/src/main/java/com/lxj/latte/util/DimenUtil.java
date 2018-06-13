package com.lxj.latte.util;

import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.lxj.latte.app.Latte;

import java.util.ResourceBundle;

/**
 * Created by lxj on 2018/6/13.
 */

public class DimenUtil {
    public static int getScreenWith(){
        final Resources resources= Latte.getApplication().getResources();
        final DisplayMetrics dm =resources.getDisplayMetrics();
        return dm.widthPixels;
    }
    public static int getScreenHeight(){
        final Resources resources= Latte.getApplication().getResources();
        final DisplayMetrics dm =resources.getDisplayMetrics();
        return dm.heightPixels;
    }
}

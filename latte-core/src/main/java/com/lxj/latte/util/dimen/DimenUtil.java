package com.lxj.latte.util.dimen;

import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.lxj.latte.app.Latte;

/**
 * Created by lxj on 2018/6/13.
 */

public class DimenUtil {
    public static int getScreenWith(){
        final Resources resources= Latte.getApplicationContext().getResources();
        final DisplayMetrics dm =resources.getDisplayMetrics();
        return dm.widthPixels;
    }
    public static int getScreenHeight(){
        final Resources resources= Latte.getApplicationContext().getResources();
        final DisplayMetrics dm =resources.getDisplayMetrics();
        return dm.heightPixels;
    }
}

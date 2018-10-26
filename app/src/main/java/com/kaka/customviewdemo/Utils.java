package com.kaka.customviewdemo;

import android.content.res.Resources;
import android.util.TypedValue;

public class Utils {

    /**
     * 把px转成像素
     * */
    public static float px2dp(float dp){
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp
                , Resources.getSystem().getDisplayMetrics());
    }
}

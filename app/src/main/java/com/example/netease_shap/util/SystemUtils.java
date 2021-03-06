package com.example.netease_shap.util;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.DisplayMetrics;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.example.netease_shap.app.Myapp;


public class SystemUtils {

    /**
     * 设置键盘
     * @param activity
     */
    public static void setKeyBroad(Activity activity, boolean open, EditText txt){
        InputMethodManager im = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if(open){
            im.showSoftInput(txt,InputMethodManager.SHOW_FORCED);
        }else{
            im.hideSoftInputFromWindow(txt.getWindowToken(),0);
        }
    }


    /**
     * 检查是否有网络
     * @return
     */
    public static boolean checkNetWork(){
        ConnectivityManager manager = (ConnectivityManager) Myapp.app.getSystemService(Context.CONNECTIVITY_SERVICE);
        return manager.getActiveNetworkInfo() != null;
    }

    /**
     * 当前是否是wifi链接
     * @return
     */
    public static boolean isWifiConnected(){
        ConnectivityManager manager = (ConnectivityManager) Myapp.app.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        return info != null;
    }

    /**
     * 检查手机（4,3,2）G是否链接
     */
    public static boolean isMobileNetworkConnected(){
        ConnectivityManager manager = (ConnectivityManager) Myapp.app.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        return info != null;
    }

    public static long getSystemTime(){
        return System.currentTimeMillis();
    }

    /**
     * 获取屏幕的dpi
     * @param at
     * @return
     */
    public static int getDpi(Activity at){
        DisplayMetrics dm = new DisplayMetrics();
        at.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.densityDpi;
    }


    /**
     * 获取包名
     * @param context
     * @return
     */
    public static String getPgName(Context context){
        return context.getPackageName();
    }

    /**
     * 获取版本号
     * @param context
     * @return
     */

    /**
     * 将px值转换为dip或dp值
     */
    public static int px2Dp(Context context, float px) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(px / scale + 0.5);
    }

    /**
     * 将dp转换成px值
     */
    public static int dp2px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dp * scale + 0.5);
    }


}

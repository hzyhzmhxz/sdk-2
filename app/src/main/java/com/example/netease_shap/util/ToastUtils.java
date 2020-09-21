package com.example.netease_shap.util;

import android.widget.Toast;

import com.example.netease_shap.app.Myapp;



public class ToastUtils {

    public static void onShortToast(String msg) {
        Toast.makeText(Myapp.app, msg, Toast.LENGTH_SHORT).show();
    }

    public static void onLongToast(String msg) {
        Toast.makeText(Myapp.app, msg, Toast.LENGTH_LONG).show();
    }
}

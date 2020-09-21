package com.example.netease_shap.net;

import android.content.Context;
import android.widget.Toast;

public class MyDebug {
    public static void print(Context context, String msg){
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
    }
}

package com.example.image;

import android.content.Context;
import android.widget.Toast;

public class ToastUtils {
    public static void onShort(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
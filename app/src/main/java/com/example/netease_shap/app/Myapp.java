package com.example.netease_shap.app;

import android.app.Application;
import android.content.Context;

import java.util.ArrayList;

public class Myapp extends Application {
    public static Context app;
    public static ArrayList<Integer> vip;
    @Override
    public void onCreate() {
        super.onCreate();
        app=this;




    }
}

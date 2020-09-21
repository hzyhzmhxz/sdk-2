package com.example.netease_shap.net;







import com.example.netease_shap.app.Myapp;

import java.io.File;

public class Constants {

    public static final String Base_NeteaseUrl = "https://cdwan.cn/api/";



    //网络缓存的地址
    public static final String PATH_DATA = Myapp.app.getCacheDir().getAbsolutePath() + File.separator + "data";

    public static final String PATH_CACHE = PATH_DATA + "/netease";


}
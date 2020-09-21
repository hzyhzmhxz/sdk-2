package com.example.netease_shap.util;

import android.util.Log;

import com.example.netease_shap.net.Apiservice;
import com.example.netease_shap.net.Constants;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpUtil {
    private static HttpUtil instance;
    private static Apiservice apiservice;
    public static HttpUtil getInstance(){
        if(instance==null){
            synchronized (HttpUtil.class){
                if (instance==null){
                    instance=new HttpUtil();
                }
            }
        }
        return instance;
    }

    public static Retrofit getretrofit(String uri){
        Retrofit builder = new Retrofit.Builder()
                .client(getOkhttpclient())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(uri)
                .build()
                ;
        return builder;
    }

    public static OkHttpClient getOkhttpclient(){
        File file = new File(Constants.PATH_CACHE);
        Cache cache = new Cache(file, 1024 * 1024 *100);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new LoggingInterceptor())
                .addNetworkInterceptor(new NetInterceptor())
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .cache(cache)
                .build();
        return okHttpClient;
    }

    /**
     * 日志的拦截器打印报文信息
     */
    static class LoggingInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            long t1 = System.nanoTime();
            Log.i("interceptor",String.format("Sending request %s on %s%n%s",request.url(),chain.connection(),request.headers()));

            Response response = chain.proceed(request);
            long t2 = System.nanoTime();
            Log.i("Received:",String.format("Received response for %s in %.1fms%n%s",response.request().url(),(t2-t1)/1e6d,response.headers()));
            if(response.header("session_id") != null){
                //Constant.session_id = response.header("session_id");
            }
            return response;
        }
    }

    static class NetInterceptor implements Interceptor{

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if(!SystemUtils.checkNetWork()){
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
            }
            Response response = chain.proceed(request);
            //通过判断网络连接是否存在获取本地或者服务器的数据
            if(!SystemUtils.checkNetWork()){
                int maxAge = 0;
                return response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control","public ,max-age="+maxAge).build();
            }else{
                int maxStale = 60*60*24*28; //设置缓存数据的保存时间
                return response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control","public, onlyif-cached, max-stale="+maxStale).build();
            }
        }
    }
    public Apiservice getApiservice(){
        if (apiservice==null){
            synchronized (Apiservice.class){
                if (apiservice==null){
                     apiservice = getretrofit(Constants.Base_NeteaseUrl).create(Apiservice.class);
                }
            }
        }
        return apiservice;
    }

}

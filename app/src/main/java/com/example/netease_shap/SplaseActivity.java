package com.example.netease_shap;

import android.content.Intent;
import android.util.Log;

import com.example.netease_shap.base.BasePermissionActivity;
import com.example.netease_shap.insertfaces.IBasePresenter;
import com.example.netease_shap.net.MyDebug;
import com.example.netease_shap.util.SpUtils;


public class SplaseActivity extends BasePermissionActivity {


    @Override
    protected void initView() {
        SpUtils.getInstance().setValue("token","eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoiN2I5NjU2NTYtNzBlYi00NzI2LWI0YTctYzUyMzY2ODYxNDg1IiwiaWF0IjoxNjAwMzMyOTM4fQ.9AFcOzuodY7IU2qtR2FVMwX0Aa9W42gRh8MezeS0abk");
        Log.i("tag","init");
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);

        MyDebug.print(this,"test");
    }

    @Override
    protected int getlayout() {
        return R.layout.activity_splase;
    }



    @Override
    protected void initData() {

    }

    @Override
    protected IBasePresenter initPresenter() {
        return null;
    }
}

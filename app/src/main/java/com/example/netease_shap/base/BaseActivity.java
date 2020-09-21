package com.example.netease_shap.base;

import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

;

import com.example.netease_shap.insertfaces.IBasePresenter;
import com.example.netease_shap.insertfaces.IBaseView;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity<P extends IBasePresenter> extends AppCompatActivity implements IBaseView {

    private Unbinder unbinder;
    protected P mpresenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getlayout());
        unbinder = ButterKnife.bind(this);
        initView();

        mpresenter=initPresenter();
        if (mpresenter!=null){
            mpresenter.onAttach(this);
            initData();
        }

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        }

    protected abstract void initData();

    protected abstract P initPresenter();

    protected abstract void initView();

    protected abstract int getlayout();

    private static final String TAG = "BaseActivity";
    @Override
    public void showTips(String tips) {
        Log.d(TAG, "showTips: "+tips);
    }

    @Override
    public void showLoading(int visible) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}

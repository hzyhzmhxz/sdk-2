package com.example.netease_shap;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.netease_shap.base.BaseActivity;
import com.example.netease_shap.insertfaces.IBasePresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PaymentActivity extends BaseActivity {


    @BindView(R.id.payment_name)
    TextView paymentName;
    @BindView(R.id.payment_phone)
    TextView paymentPhone;
    @BindView(R.id.payment_site)
    TextView paymentSite;
    @BindView(R.id.payment_rv)
    RelativeLayout paymentRv;

    @Override
    protected void initData() {

    }

    @Override
    protected IBasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected int getlayout() {
        return R.layout.activity_payment;
    }



    @OnClick(R.id.payment_rv)
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.payment_rv:
                Intent intent = new Intent(this,SiteActivity.class);
                startActivity(intent);
                break;
        }
    }
}
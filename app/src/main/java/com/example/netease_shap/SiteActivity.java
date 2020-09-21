package com.example.netease_shap;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.netease_shap.base.BaseActivity;
import com.example.netease_shap.insertfaces.IBasePresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SiteActivity extends BaseActivity {


    @BindView(R.id.site_newsite)
    Button siteNewsite;

    @Override
    protected void initData() {

    }

    @Override
    protected IBasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        siteNewsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SiteActivity.this, ManageActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected int getlayout() {
        return R.layout.activity_site;
    }


}
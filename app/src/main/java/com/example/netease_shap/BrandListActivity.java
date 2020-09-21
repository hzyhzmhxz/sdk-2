package com.example.netease_shap;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.netease_shap.base.BaseActivity;
import com.example.netease_shap.bean.BrandinfoBean;
import com.example.netease_shap.bean.GoodDetailBean;
import com.example.netease_shap.bean.MUJIbean;
import com.example.netease_shap.insertfaces.Ibrand;
import com.example.netease_shap.mvp.presenter.BrandPersenter;
import com.example.netease_shap.ui.adapter.MUJIAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BrandListActivity extends BaseActivity<Ibrand.IPersenter> implements Ibrand.IView {


    @BindView(R.id.brand_list_img)
    ImageView brandListImg;
    @BindView(R.id.brandlist_name)
    TextView brandlistName;
    @BindView(R.id.brandlist_simple_desc)
    TextView brandlistSimpleDesc;
    @BindView(R.id.brandlist_rv)
    RecyclerView brandlistRv;
    private List<MUJIbean.DataBeanX.GoodsListBean> mujilist;
    private MUJIAdapter mujiAdapter;

    @Override
    protected void initData() {
        int id = getIntent().getIntExtra("id", 0);
        mpresenter.getbrand(id);
        mpresenter.getgoodslist(id);

    }

    @Override
    protected BrandPersenter initPresenter() {
        return new BrandPersenter();
    }

    @Override
    protected void initView() {
        brandlistRv.setLayoutManager(new GridLayoutManager(this, 2));
        mujilist = new ArrayList<>();
        mujiAdapter = new MUJIAdapter(this, mujilist);
        brandlistRv.setAdapter(mujiAdapter);

    }

    @Override
    protected int getlayout() {
        return R.layout.activity_brand_list;
    }

    @Override
    public void getbrandinfo(BrandinfoBean result) {
        Glide.with(this).load(result.getData().getBrand().getList_pic_url()).into(brandListImg);
        brandlistName.setText(result.getData().getBrand().getName());
        brandlistSimpleDesc.setText(result.getData().getBrand().getSimple_desc());

    }

    @Override
    public void getgoodslist(MUJIbean mujIbean) {
        Log.d(TAG, "getgoodslist: "+mujIbean.getData().getGoodsList().size());
        mujilist.addAll(mujIbean.getData().getGoodsList());
        mujiAdapter.notifyDataSetChanged();
    }

    private static final String TAG = "BrandListActivity";



}
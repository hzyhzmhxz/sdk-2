package com.example.netease_shap.mvp.presenter;


import android.os.IBinder;

import com.example.netease_shap.base.BasePresenter;
import com.example.netease_shap.bean.BrandinfoBean;
import com.example.netease_shap.bean.GoodDetailBean;
import com.example.netease_shap.bean.MUJIbean;
import com.example.netease_shap.insertfaces.ICart;
import com.example.netease_shap.insertfaces.Ibrand;
import com.example.netease_shap.net.CommonSubscriber;
import com.example.netease_shap.util.HttpUtil;
import com.example.netease_shap.util.RxUtils;

public class BrandPersenter extends BasePresenter<Ibrand.IView> implements Ibrand.IPersenter {


    @Override
    public void getbrand(int id) {
        addSubscribe(HttpUtil.getInstance().getApiservice().getbrandinfo(id)
                .compose(RxUtils.<BrandinfoBean>rxScheduler())
                .subscribeWith(new CommonSubscriber<BrandinfoBean>(mView) {
                    @Override
                    public void onNext(BrandinfoBean result) {
                        mView.getbrandinfo(result);
                    }
                }));
    }

    @Override
    public void getgoodslist(int id) {
        addSubscribe(HttpUtil.getInstance().getApiservice().getgoodlist(id)
                .compose(RxUtils.<MUJIbean>rxScheduler())
                .subscribeWith(new CommonSubscriber<MUJIbean>(mView) {
                    @Override
                    public void onNext(MUJIbean result) {
                        mView.getgoodslist(result);
                    }
                }));
    }


}

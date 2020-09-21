package com.example.netease_shap.mvp.presenter;


import com.example.netease_shap.base.BasePresenter;
import com.example.netease_shap.bean.AdressBean;
import com.example.netease_shap.insertfaces.ICart;
import com.example.netease_shap.net.CommonSubscriber;
import com.example.netease_shap.util.HttpUtil;
import com.example.netease_shap.util.RxUtils;

public class AdressPersenter extends BasePresenter<ICart.IAdressView> implements ICart.IAdressPersenter {
    @Override
    public void getAdressById(int parentId) {
        addSubscribe(HttpUtil.getInstance().getApiservice().getAdressById(parentId)
                .compose(RxUtils.<AdressBean>rxScheduler())
                .subscribeWith(new CommonSubscriber<AdressBean>(mView) {
                    @Override
                    public void onNext(AdressBean result) {
                        mView.getAdressByIdReturn(result);
                    }
                }));
    }
}

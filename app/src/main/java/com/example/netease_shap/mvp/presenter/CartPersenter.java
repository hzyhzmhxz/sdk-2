package com.example.netease_shap.mvp.presenter;


import com.example.netease_shap.base.BasePresenter;
import com.example.netease_shap.bean.AddCartInfoBean;
import com.example.netease_shap.bean.GoodDetailBean;
import com.example.netease_shap.insertfaces.ICart;
import com.example.netease_shap.net.CommonSubscriber;
import com.example.netease_shap.util.HttpUtil;
import com.example.netease_shap.util.RxUtils;

public class CartPersenter extends BasePresenter<ICart.IView> implements ICart.IPersenter {
    @Override
    public void getGoodDetail(int id) {
        addSubscribe(HttpUtil.getInstance().getApiservice().getGoodDetail(id)
                .compose(RxUtils.<GoodDetailBean>rxScheduler())
                .subscribeWith(new CommonSubscriber<GoodDetailBean>(mView) {
                    @Override
                    public void onNext(GoodDetailBean result) {
                        mView.getGoodDetailReturn(result);
                    }
                }));
    }

    @Override
    public void addCart(int goodsId, int number, int productId) {
        addSubscribe(HttpUtil.getInstance().getApiservice().addCart(goodsId,number,productId)
                .compose(RxUtils.<AddCartInfoBean>rxScheduler())
                .subscribeWith(new CommonSubscriber<AddCartInfoBean>(mView) {
                    @Override
                    public void onNext(AddCartInfoBean result) {
                        mView.addCartInfoReturn(result);
                    }
                }));
    }

}

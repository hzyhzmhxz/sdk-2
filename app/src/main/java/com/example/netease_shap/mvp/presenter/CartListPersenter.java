package com.example.netease_shap.mvp.presenter;


import com.example.netease_shap.base.BasePresenter;
import com.example.netease_shap.bean.DeleteCartBean;
import com.example.netease_shap.bean.ShoppingCartBean;
import com.example.netease_shap.insertfaces.ICart;
import com.example.netease_shap.net.CommonSubscriber;
import com.example.netease_shap.util.HttpUtil;
import com.example.netease_shap.util.RxUtils;

public class CartListPersenter extends BasePresenter<ICart.ICartView> implements ICart.ICartPersenter {
    @Override
    public void getCartList() {
        addSubscribe(HttpUtil.getInstance().getApiservice().getCartList()
                .compose(RxUtils.<ShoppingCartBean>rxScheduler())
                .subscribeWith(new CommonSubscriber<ShoppingCartBean>(mView) {
                    @Override
                    public void onNext(ShoppingCartBean result) {
                        mView.getCartListReturn(result);
                    }
                }));
    }

    @Override
    public void deleteCartList(String productIds) {
        addSubscribe(HttpUtil.getInstance().getApiservice().cartDelete(productIds)
                .compose(RxUtils.<DeleteCartBean>rxScheduler())
                .subscribeWith(new CommonSubscriber<DeleteCartBean>(mView) {
                    @Override
                    public void onNext(DeleteCartBean result) {
                        mView.deleteCartListReturn(result);
                    }
                }));
    }
}

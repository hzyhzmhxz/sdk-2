package com.example.netease_shap.insertfaces;


import com.example.netease_shap.bean.AddCartInfoBean;
import com.example.netease_shap.bean.AdressBean;
import com.example.netease_shap.bean.DeleteCartBean;
import com.example.netease_shap.bean.GoodDetailBean;
import com.example.netease_shap.bean.ShoppingCartBean;

public interface ICart {
    interface IView extends IBaseView{

        void getGoodDetailReturn(GoodDetailBean result);
        //添加商品信息返回
        void addCartInfoReturn(AddCartInfoBean result);
    }

    interface IPersenter extends IBasePresenter<IView>{
        void getGoodDetail(int id);
        //添加到购物车
        void addCart(int goodsId,int number,int productId);
    }
    interface ICartView extends IBaseView{
        void getCartListReturn(ShoppingCartBean result);

        void deleteCartListReturn(DeleteCartBean result);
    }

    interface ICartPersenter extends IBasePresenter<ICartView>{


        //获取购物车的数据
        void getCartList();

        //删除购物车数据
        void deleteCartList(String productIds);

    }
    interface IAdressView extends IBaseView{
        void getAdressByIdReturn(AdressBean result);
    }

    interface IAdressPersenter extends IBasePresenter<IAdressView>{
        void getAdressById(int parentId);
    }


}

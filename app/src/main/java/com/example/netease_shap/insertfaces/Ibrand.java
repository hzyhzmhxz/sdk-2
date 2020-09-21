package com.example.netease_shap.insertfaces;

import com.example.netease_shap.bean.BrandinfoBean;
import com.example.netease_shap.bean.GoodDetailBean;
import com.example.netease_shap.bean.HomeBean;
import com.example.netease_shap.bean.MUJIbean;

public interface Ibrand {
    interface IView extends IBaseView{

        void getbrandinfo(BrandinfoBean result);
        void getgoodslist(MUJIbean mujIbean);
    }

    interface IPersenter extends IBasePresenter<Ibrand.IView>{
        void getbrand(int id);
        void getgoodslist(int id);
    }
}

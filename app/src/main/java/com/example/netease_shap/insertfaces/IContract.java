package com.example.netease_shap.insertfaces;


import com.example.netease_shap.bean.HomeBean;

import java.util.List;

public interface IContract {
    interface Iview extends IBaseView{
        void onhome(List<HomeBean.HomeListBean> homeBean);

    }
    interface Ipresenter extends IBasePresenter<Iview>{
        void gethome();
    }

}

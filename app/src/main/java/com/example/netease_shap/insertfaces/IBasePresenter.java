package com.example.netease_shap.insertfaces;

public interface IBasePresenter<V extends IBaseView> {

    void  onAttach(V v);
    void  onDttach();
}

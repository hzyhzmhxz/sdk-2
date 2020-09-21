package com.example.netease_shap.base;







import com.example.netease_shap.insertfaces.IBasePresenter;
import com.example.netease_shap.insertfaces.IBaseView;

import java.lang.ref.WeakReference;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BasePresenter<V extends IBaseView> implements IBasePresenter<V> {
    protected   V mView;
    private WeakReference<V> weakReference;
    protected CompositeDisposable compositeDisposable;
    @Override
    public void onAttach(V v) {
        weakReference=new WeakReference<>(v);
        mView=weakReference.get();
    }

    @Override
    public void onDttach() {
        mView=null;
        unSubscribe();
    }

    public void addSubscribe(Disposable disposable){
        if (compositeDisposable==null) compositeDisposable=new CompositeDisposable();
        compositeDisposable.add(disposable);
    }
    public void unSubscribe(){
        if (compositeDisposable!=null){
            compositeDisposable.clear();
        }
    }

}

package com.example.netease_shap.mvp.presenter;


import com.example.netease_shap.base.BasePresenter;
import com.example.netease_shap.bean.BrandinfoBean;
import com.example.netease_shap.bean.MUJIbean;
import com.example.netease_shap.bean.TopicBean;
import com.example.netease_shap.bean.TopicListBean;
import com.example.netease_shap.insertfaces.IContract;
import com.example.netease_shap.insertfaces.Ibrand;
import com.example.netease_shap.insertfaces.Itopic;
import com.example.netease_shap.net.CommonSubscriber;
import com.example.netease_shap.util.HttpUtil;
import com.example.netease_shap.util.RxUtils;

public class TopicPersenter extends BasePresenter<Itopic.Iview> implements Itopic.Ipresenter {


    @Override
    public void gettopic(int id) {
        addSubscribe(HttpUtil.getInstance().getApiservice().gettopic(id)
                .compose(RxUtils.<TopicBean>rxScheduler())
                .subscribeWith(new CommonSubscriber<TopicBean>(mView) {
                    @Override
                    public void onNext(TopicBean topicBean) {
                        mView.gettopic(topicBean);
                    }
                }));
    }

    @Override
    public void gettopiclist(int id) {
        addSubscribe(HttpUtil.getInstance().getApiservice().gettopiclist(id)
                .compose(RxUtils.<TopicListBean>rxScheduler())
                .subscribeWith(new CommonSubscriber<TopicListBean>(mView) {
                    @Override
                    public void onNext(TopicListBean topicListBean) {
                        mView.gettopiclist(topicListBean);
                    }
                }));
    }


}

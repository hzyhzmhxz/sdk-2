package com.example.netease_shap.insertfaces;

import com.example.netease_shap.bean.HomeBean;
import com.example.netease_shap.bean.TopicBean;
import com.example.netease_shap.bean.TopicListBean;

import java.util.List;

public interface Itopic {
    interface Iview extends IBaseView{
        void gettopic(TopicBean topicBean);
        void gettopiclist(TopicListBean topicListBean);
    }
    interface Ipresenter extends IBasePresenter<Itopic.Iview>{
        void gettopic(int id);
        void gettopiclist( int id);
    }
}

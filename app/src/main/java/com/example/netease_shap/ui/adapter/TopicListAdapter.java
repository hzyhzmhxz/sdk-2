package com.example.netease_shap.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.netease_shap.R;
import com.example.netease_shap.base.BaseAdapter;
import com.example.netease_shap.bean.HomeBean;
import com.example.netease_shap.bean.TopicListBean;

import java.util.List;

public class TopicListAdapter extends BaseAdapter {
    public TopicListAdapter(Context context, List data) {
        super(context, data);
    }

    @Override
    protected int getlayout() {
        return R.layout.topic_list_layout;
    }

    @Override
    protected void bindData(BaseViewHolder baseViewHolder, Object o, int position) {
       TopicListBean.DataBean topicListBean = (TopicListBean.DataBean) o;
        if(!TextUtils.isEmpty(topicListBean.getScene_pic_url())){
            Glide.with(context).load(topicListBean.getScene_pic_url()).into((ImageView) baseViewHolder.getviewbyid(R.id.topic_iv));
        }
        ((TextView)baseViewHolder.getviewbyid(R.id.topic_list_name)).setText(topicListBean.getTitle());

}
}

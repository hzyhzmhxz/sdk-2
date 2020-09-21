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

import java.util.List;

public class TopicAdapter extends BaseAdapter {
    public TopicAdapter(Context context, List data) {
        super(context, data);
    }

    @Override
    protected int getlayout() {
        return R.layout.layout_home_topic;
    }

    @Override
    protected void bindData(BaseViewHolder baseViewHolder, Object o, int position) {
        HomeBean.DataBean.TopicListBean topicListBean = (HomeBean.DataBean.TopicListBean) o;
        if(!TextUtils.isEmpty(topicListBean.getItem_pic_url())){
            Glide.with(context).load(topicListBean.getItem_pic_url()).into((ImageView) baseViewHolder.getviewbyid(R.id.img_topic));
        }
        ((TextView)baseViewHolder.getviewbyid(R.id.txt_topic_name)).setText(topicListBean.getTitle());
        ((TextView)baseViewHolder.getviewbyid(R.id.txt_topic_subtitle)).setText(topicListBean.getSubtitle());
        ((TextView)baseViewHolder.getviewbyid(R.id.txt_topic_price)).setText("¥"+topicListBean.getPrice_info()+"元起");
        baseViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onClickListener!=null){
                    onClickListener.onClick(position);
                }
            }
        });
    }
    OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    interface OnClickListener{
        void onClick(int position);
    }
    }



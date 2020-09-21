package com.example.netease_shap.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.netease_shap.R;
import com.example.netease_shap.base.BaseAdapter;
import com.example.netease_shap.bean.BrandinfoBean;
import com.example.netease_shap.bean.MUJIbean;

import java.util.List;

public class MUJIAdapter extends BaseAdapter {

    public MUJIAdapter(Context context, List datas) {
        super(context, datas);
    }

    @Override
    protected int getlayout() {
        return R.layout.new_layout;
    }

    @Override
    protected void bindData(BaseViewHolder baseViewHolder, Object o, int position) {
        MUJIbean.DataBeanX.GoodsListBean data= (MUJIbean.DataBeanX.GoodsListBean) o;
        ImageView newproduct_img = (ImageView) baseViewHolder.getviewbyid(R.id.newproduct_img);
        TextView newproduct_name = (TextView) baseViewHolder.getviewbyid(R.id.newproduct_name);
        TextView newproduct_retail_price = (TextView) baseViewHolder.getviewbyid(R.id.newproduct_retail_price);

            Glide.with(context).load(data.getList_pic_url()).into((newproduct_img));

     newproduct_name.setText(data.getName());
     newproduct_retail_price.setText("¥"+data.getRetail_price());
    }
}

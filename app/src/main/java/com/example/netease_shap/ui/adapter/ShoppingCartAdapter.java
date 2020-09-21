package com.example.netease_shap.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.netease_shap.R;
import com.example.netease_shap.base.BaseAdapter;
import com.example.netease_shap.bean.ShoppingCartBean;
import com.example.netease_shap.common.CartCustomView;

import java.util.List;

public class ShoppingCartAdapter extends BaseAdapter {
    public boolean isEditor; //是否是编辑状态

    private CheckBoxClick click;
    public ShoppingCartAdapter(Context context, List datas) {
        super(context, datas);
    }

    @Override
    protected int getlayout() {
        return R.layout.shoppingcart_layout;
    }

    @Override
    protected void bindData(BaseViewHolder baseViewHolder, Object o, int position) {
        ShoppingCartBean.DataBean.CartListBean bean= (ShoppingCartBean.DataBean.CartListBean) o;
        CheckBox checkBox = (CheckBox) baseViewHolder.getviewbyid(R.id.checkbox_select);
        TextView txtName = (TextView) baseViewHolder.getviewbyid(R.id.txt_name);
        TextView txtNumber = (TextView)baseViewHolder.getviewbyid(R.id.txt_number);
        TextView txtPrice = (TextView) baseViewHolder.getviewbyid(R.id.txt_price);
        ImageView img_icon = (ImageView) baseViewHolder.getviewbyid(R.id.img_icon);
        TextView txtSelect = (TextView) baseViewHolder.getviewbyid(R.id.txt_select);
        CartCustomView cartCustomView = (CartCustomView) baseViewHolder.getviewbyid(R.id.view_number);
        if(isEditor){
            txtName.setVisibility(View.GONE);
            txtNumber.setVisibility(View.GONE);
            txtSelect.setVisibility(View.VISIBLE);
            cartCustomView.setVisibility(View.VISIBLE);
        }else{
            txtName.setVisibility(View.VISIBLE);
            txtNumber.setVisibility(View.VISIBLE);
            txtSelect.setVisibility(View.GONE);
            cartCustomView.setVisibility(View.GONE);
        }
        Glide.with(context).load(bean.getList_pic_url()).into(img_icon);
        txtName.setText(bean.getGoods_name());
        txtNumber.setText("X"+bean.getNumber());
        txtPrice.setText("￥"+bean.getRetail_price());
        cartCustomView.initView();
        cartCustomView.setValue(bean.getNumber());
        cartCustomView.setOnClickListener(new CartCustomView.IClick() {
            @Override
            public void clickCB(int value) {
                bean.setNumber(value);
            }
        });
        //checkBox.setChecked(bean.select);
        checkBox.setSelected(bean.select);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                bean.select = !bean.select;
                if(click != null){
                    click.checkChange();
                }
            }
        });


    }

    public void setOnItemCheckBoxClickListener(CheckBoxClick click){
        this.click = click;
    }


    public interface CheckBoxClick{
        void checkChange();
    }
    }


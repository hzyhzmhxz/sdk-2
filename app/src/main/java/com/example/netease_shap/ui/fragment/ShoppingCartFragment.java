package com.example.netease_shap.ui.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.netease_shap.PaymentActivity;
import com.example.netease_shap.R;
import com.example.netease_shap.base.BaseFragment;
import com.example.netease_shap.bean.DeleteCartBean;
import com.example.netease_shap.bean.ShoppingCartBean;
import com.example.netease_shap.insertfaces.IBasePresenter;
import com.example.netease_shap.insertfaces.ICart;
import com.example.netease_shap.mvp.presenter.CartListPersenter;
import com.example.netease_shap.ui.adapter.ShoppingCartAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


public class ShoppingCartFragment extends BaseFragment<ICart.ICartPersenter> implements ICart.ICartView {


    @BindView(R.id.layout_top)
    FrameLayout layoutTop;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.radio_select)
    CheckBox radioSelect;
    @BindView(R.id.txt_checkall)
    TextView txtCheckall;
    @BindView(R.id.txt_allPrice)
    TextView txtAllPrice;
    @BindView(R.id.txt_edit)
    TextView txtEdit;
    @BindView(R.id.txt_submit)
    TextView txtSubmit;
    @BindView(R.id.layout_bottom)
    RelativeLayout layoutBottom;
    ShoppingCartAdapter cartListAdapter;
    List<ShoppingCartBean.DataBean.CartListBean> list;

    private int allNumber;
    private int allPrice;
    @Override
    protected void initData() {
    mpresenter.getCartList();
    }

    @Override
    protected CartListPersenter initPresenter() {
        return new CartListPersenter();
    }

    @Override
    protected void initView(View view) {
        list = new ArrayList<>();
        cartListAdapter = new ShoppingCartAdapter(context,list);
        recyclerview.setLayoutManager(new LinearLayoutManager(context));
        recyclerview.setAdapter(cartListAdapter);
        txtCheckall.setText("全选(0)");
        cartListAdapter.setOnItemCheckBoxClickListener(new ShoppingCartAdapter.CheckBoxClick() {
            @Override
            public void checkChange() {
                //判断当前是否全选
                boolean bool = CheckSelectAll();
                txtCheckall.setText("全选("+allNumber+")");
                txtAllPrice.setText("￥"+allPrice);
                radioSelect.setSelected(bool);
                cartListAdapter.notifyDataSetChanged();
            }
        });
    }

    private boolean CheckSelectAll() {
        allNumber = 0;
        allPrice = 0;
        boolean isSelectAll = true;
        for(ShoppingCartBean.DataBean.CartListBean item:list){
            if(item.select){
                allNumber += item.getNumber();
                allPrice += item.getNumber()*item.getRetail_price();
            }
            if(isSelectAll && !item.select){
                isSelectAll = false;
            }
        }
        return isSelectAll;
    }


    @Override
    protected int getlayout() {
        return R.layout.fragment_shopping_cart;
    }

    @Override
    public void getCartListReturn(ShoppingCartBean result) {
        list.addAll(result.getData().getCartList());
        cartListAdapter.notifyDataSetChanged();
    }

    @Override
    public void deleteCartListReturn(DeleteCartBean result) {

    }
    @OnClick({R.id.radio_select, R.id.txt_edit, R.id.txt_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.radio_select:
                selectAll();
                break;
            case R.id.txt_edit:
                clickEdit();
                break;
            case R.id.txt_submit:
                submitData();
                break;
        }
    }

    private void submitData() {
        if ("下单".equals(txtSubmit.getText())){
            Intent intent = new Intent(context, PaymentActivity.class);
            startActivity(intent);

        }else  if ("删除所选".equals(txtSubmit.getText())){
            StringBuilder stringBuilder = new StringBuilder();
            List<Integer> ids=getSelectProducts();
            for (Integer id:ids){
                stringBuilder.append(id);
                stringBuilder.append(",");
            }
            if (stringBuilder.length()>0){
                stringBuilder.deleteCharAt(stringBuilder.length()-1);
                String s = stringBuilder.toString();
                mpresenter.deleteCartList(s);
            }else {
                Toast.makeText(context, "没有选中要删除的商品", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private List<Integer> getSelectProducts() {
        ArrayList<Integer> ids = new ArrayList<>();
        for (ShoppingCartBean.DataBean.CartListBean item:list){
            if (item.select){
                ids.add(item.getProduct_id());
            }
        }
        return ids;
    }


    private void clickEdit() {
//当前是默认的状态---编辑状态
        if("编辑".equals(txtEdit.getText())){
            cartListAdapter.isEditor = true;
            txtEdit.setText("完成");
            txtSubmit.setText("删除所选");
            txtAllPrice.setVisibility(View.GONE);
        }else if("完成".equals(txtEdit.getText())){   //编辑状态进入默认状态
            cartListAdapter.isEditor = false;
            txtEdit.setText("编辑");
            txtSubmit.setText("下单");
            txtAllPrice.setVisibility(View.VISIBLE);
            txtAllPrice.setText("￥0");
        }
        resetSelect(false);
        cartListAdapter.notifyDataSetChanged();
    }

    private void selectAll() {
        resetSelect(!radioSelect.isChecked());
        radioSelect.setSelected(!radioSelect.isChecked());
        txtCheckall.setText("全选("+allNumber+")");
        txtAllPrice.setText("￥"+allPrice);
        cartListAdapter.notifyDataSetChanged();
    }

    private void resetSelect(boolean b) {
        allNumber=0;
        allPrice=0;
        for (ShoppingCartBean.DataBean.CartListBean item:list){
            item.select=b;
            if (b){
                allNumber += item.getNumber();
                allPrice += item.getNumber()*item.getRetail_price();
            }
        }
        if (!b){
            allNumber = 0;
            allPrice = 0;
        }
    }
}
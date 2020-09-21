package com.example.netease_shap;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.netease_shap.base.BaseActivity;
import com.example.netease_shap.bean.AdressBean;
import com.example.netease_shap.insertfaces.ICart;
import com.example.netease_shap.mvp.presenter.AdressPersenter;
import com.example.netease_shap.util.SystemUtils;
import com.weigan.loopview.LoopView;
import com.weigan.loopview.OnItemSelectedListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ManageActivity extends BaseActivity<ICart.IAdressPersenter> implements ICart.IAdressView {


    @BindView(R.id.et_naem)
    EditText etNaem;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.txt_adress)
    TextView txtAdress;
    @BindView(R.id.et_minute)
    EditText etMinute;
    @BindView(R.id.radio_manage)
    CheckBox radioManage;
    @BindView(R.id.txt_setting)
    TextView txtSetting;
    @BindView(R.id.layout_bottom)
    RelativeLayout layoutBottom;
    @BindView(R.id.manage_cancel)
    Button manageCancel;
    @BindView(R.id.manage_save)
    Button manageSave;
    @BindView(R.id.layout_adress)
    RelativeLayout layoutAdress;
    private Map<Integer, List<AdressBean.DataBean>> addressMap;

    private LoopView province,city,area;
    private TextView txtProvince,txtCity,txtArea;
    private PopupWindow mPopWindow;
    private int curProvinceId,curCityId,curAreaId; //当前省市区的ID
    private String procive;
    private String city1;
    private String area1;

    @Override
    protected void initData() {
        mpresenter.getAdressById(1);
    }

    @Override
    protected ICart.IAdressPersenter initPresenter() {
        return new AdressPersenter();
    }

    @Override
    protected void initView() {
        addressMap = new HashMap<>();
    }
    @OnClick({R.id.txt_adress})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.txt_adress:
                openAdressSelect();
                break;
        }
    }

        private void openAdressSelect(){
            if(mPopWindow != null && mPopWindow.isShowing()){

            }else{
                View contentView = LayoutInflater.from(this).inflate(R.layout.layout_popwindow_adress, null);
                int height = SystemUtils.dp2px(this,250);
                mPopWindow = new PopupWindow(contentView, LinearLayout.LayoutParams.MATCH_PARENT,height);
                mPopWindow.setBackgroundDrawable(new BitmapDrawable());
                mPopWindow.setFocusable(false);
                mPopWindow.setOutsideTouchable(false);
                mPopWindow.setContentView(contentView);
                contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
                province = contentView.findViewById(R.id.adress_province);
                city = contentView.findViewById(R.id.adress_city);
                area = contentView.findViewById(R.id.adress_area);
                txtProvince = contentView.findViewById(R.id.txt_province);
                txtCity = contentView.findViewById(R.id.txt_city);
                txtArea = contentView.findViewById(R.id.txt_area);
                TextView txt_submit = contentView.findViewById(R.id.txt_submit);
                txt_submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        txtAdress.setText(procive+"-"+city1+"-"+area1);
                        mPopWindow.dismiss();
                        mPopWindow = null;
                    }
                });
                mPopWindow.showAtLocation(layoutAdress, Gravity.BOTTOM,0,0);

                //省份
                province.setListener(new OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(int index) {
                        List<AdressBean.DataBean> proviceList = addressMap.get(1); //key为1固定为省的数据
                        AdressBean.DataBean dataBean = proviceList.get(index);
                        curProvinceId = dataBean.getId();
                        mpresenter.getAdressById(curProvinceId);
                        List<String> items = new ArrayList<>();
                        items.add("请选择");
                        city.setItems(items);
                        txtProvince.setText(dataBean.getName());
                        procive = dataBean.getName();
                        txtCity.setText("请选择城市");
                        txtArea.setText("请选中区域");
                    }
                });

                city.setListener(new OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(int index) {
                        List<AdressBean.DataBean> cityList = addressMap.get(curProvinceId); //key省份id
                        AdressBean.DataBean dataBean = cityList.get(index);
                        curCityId = dataBean.getId();
                        mpresenter.getAdressById(curCityId);
                        area.setItems(new ArrayList<>());
                        txtCity.setText(dataBean.getName());
                        city1 = dataBean.getName();
                        txtArea.setText("请选中区域");
                    }
                });

                area.setListener(new OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(int index) {
                        List<AdressBean.DataBean> areaList = addressMap.get(curCityId); //key省份id
                        AdressBean.DataBean dataBean = areaList.get(index);
                        curAreaId = dataBean.getId();
                        txtArea.setText(dataBean.getName());
                        area1 = dataBean.getName();
                    }
                });

                //初始化省份的数据
                List<AdressBean.DataBean> pList = addressMap.get(1);
                if(pList == null) return;
                List<String> adresses = getAdressStrings(pList);
                if(pList == null || adresses.size() == 0){
                    mpresenter.getAdressById(1);
                }else{
                    province.setItems(adresses);
                    curProvinceId = pList.get(0).getId();
                    txtProvince.setText(adresses.get(0));
                }

            }
        }




    @Override
    protected int getlayout() {
        return R.layout.activity_manage;
    }
    /**
     * 请求数据回来的方法
     * @param result
     */
    @Override
    public void getAdressByIdReturn(AdressBean result) {
        List<AdressBean.DataBean> list = null;
        int type = 0;
        for(AdressBean.DataBean item:result.getData()){
            int key = item.getParent_id();
            list = addressMap.get(key);
            if(list == null){
                list = new ArrayList<>();
                addressMap.put(key,list);
            }
            boolean bool = hasList(item.getId(),list);
            if(!bool) list.add(item);
            if(type == 0){
                type = item.getType();
            }
        }
        if(list == null) return;
        List<String> adresses = getAdressStrings(list);
        if(type == 1){
            //刷新省的数据
            if(province != null){
                curProvinceId = list.get(0).getId();
                txtProvince.setText(list.get(0).getName());
                province.setItems(adresses);
            }
        }else if(type == 2){
            //刷新市的数据
            if(city != null){
                curCityId = list.get(0).getId();
                txtCity.setText(list.get(0).getName());
                city.setItems(adresses);
            }
        }else{
            //区
            if(area != null){
                curAreaId = list.get(0).getId();
                txtArea.setText(list.get(0).getName());
                area.setItems(adresses);
            }
        }
    }

    /**
     * 判断当前的地址列表中是否有这个地址
     * @param id
     * @param list
     * @return
     */
    private boolean hasList(int id, List<AdressBean.DataBean> list){
        boolean bool = false;
        for(AdressBean.DataBean item:list){
            if(item.getId() == id){
                bool = true;
                break;
            }
        }
        return bool;
    }

    /**
     * 提取省市区的名字
     * @param list
     * @return
     */
    private List<String> getAdressStrings(List<AdressBean.DataBean> list){
        List<String> adresses = new ArrayList<>();
        for(AdressBean.DataBean item:list){
            adresses.add(item.getName());
        }
        return adresses;
    }



}
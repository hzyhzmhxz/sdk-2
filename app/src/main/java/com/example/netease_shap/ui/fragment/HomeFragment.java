package com.example.netease_shap.ui.fragment;

import android.content.Intent;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.netease_shap.BrandListActivity;
import com.example.netease_shap.DetailGoodActivity;
import com.example.netease_shap.R;
import com.example.netease_shap.base.BaseFragment;
import com.example.netease_shap.bean.HomeBean;
import com.example.netease_shap.insertfaces.IContract;
import com.example.netease_shap.mvp.presenter.PresenterImpl;
import com.example.netease_shap.ui.adapter.HomeAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class HomeFragment extends BaseFragment<IContract.Ipresenter> implements IContract.Iview {

    HomeAdapter homeAdapter;
    List<HomeBean.HomeListBean> list;

    @BindView(R.id.home_rv)
    RecyclerView homeRv;

    @Override
    protected void initData() {
        mpresenter.gethome();
    }

    @Override
    protected PresenterImpl initPresenter() {
        return new PresenterImpl();
    }

    @Override
    protected void initView(View view) {
        list = new ArrayList<>();
        homeAdapter = new HomeAdapter(list, context);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 2);
        homeAdapter.setSpanSizeLookup(new BaseQuickAdapter.SpanSizeLookup() {
            @Override
            public int getSpanSize(GridLayoutManager gridLayoutManager, int i) {
                int type = list.get(i).currentType;
                switch (type) {
                    case HomeBean.ITEM_TYPE_BANNER:
                    case HomeBean.ITEM_TYPE_TAB:
                    case HomeBean.ITEM_TYPE_TITLE:
                    case HomeBean.ITEM_TYPE_HOT:
                    case HomeBean.ITEM_TYPE_TITLETOP:
                    case HomeBean.ITEM_TYPE_TOPIC:
                        return 2;
                    case HomeBean.ITEM_TYPE_BRAND:

                    case HomeBean.ITEM_TYPE_NEW:
                    case HomeBean.ITEM_TYPE_CATEGORY:
                        return 1;

                }
                return 0;
            }
        });
        homeRv.setLayoutManager(gridLayoutManager);
        homeAdapter.bindToRecyclerView(homeRv);
        homeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                int type = list.get(position).currentType;
                Intent intent = new Intent();
                switch (type){
                    case HomeBean.ITEM_TYPE_BANNER:
                        break;
                    case HomeBean.ITEM_TYPE_BRAND:
                        HomeBean.DataBean.BrandListBean brandListBean= (HomeBean.DataBean.BrandListBean) list.get(position).data;
                        intent.putExtra("id",brandListBean.getId());
                        intent.setClass(context, BrandListActivity.class);
                        startActivity(intent);
                        break;
                    case HomeBean.ITEM_TYPE_HOT:
                        HomeBean.DataBean.HotGoodsListBean bean = (HomeBean.DataBean.HotGoodsListBean) list.get(position).data;
                        intent.putExtra("id",bean.getId());
                        intent.setClass(context, DetailGoodActivity.class);
                        startActivityForResult(intent,200);
                        break;
                    case HomeBean.ITEM_TYPE_TITLE:
                        break;
                    case HomeBean.ITEM_TYPE_TITLETOP:
                        break;
                    case HomeBean.ITEM_TYPE_TOPIC:


                        break;
                    case HomeBean.ITEM_TYPE_CATEGORY:
                        break;
                }
            }
        });
    }



    @Override
    protected int getlayout() {
        return R.layout.fragment_home;
    }

    @Override
    public void onhome(List<HomeBean.HomeListBean> homeBean) {
        list.addAll(homeBean);
        homeAdapter.notifyDataSetChanged();
    }
}
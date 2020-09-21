package com.example.netease_shap.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.netease_shap.DetailGoodActivity;
import com.example.netease_shap.MainActivity;
import com.example.netease_shap.R;
import com.example.netease_shap.TopicActivity;
import com.example.netease_shap.bean.HomeBean;
import com.example.netease_shap.util.SystemUtils;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class HomeAdapter extends BaseMultiItemQuickAdapter<HomeBean.HomeListBean, BaseViewHolder> {
    private Context context;
    private final String string;
    private TopicAdapter topicAdapter;
    public HomeAdapter(List<HomeBean.HomeListBean> data, Context context) {
        super(data);
        this.context = context;
        string = context.getString(R.string.word_price_brand);
        addItemType(HomeBean.ITEM_TYPE_BANNER, R.layout.banner_layout);
        addItemType(HomeBean.ITEM_TYPE_TAB, R.layout.tab_layout);
        addItemType(HomeBean.ITEM_TYPE_TITLETOP, R.layout.title_top_layout);
        addItemType(HomeBean.ITEM_TYPE_BRAND, R.layout.brand_layout);
        addItemType(HomeBean.ITEM_TYPE_TITLE, R.layout.layout_title);
        addItemType(HomeBean.ITEM_TYPE_NEW, R.layout.new_layout);
        addItemType(HomeBean.ITEM_TYPE_TITLETOP, R.layout.layout_title);
        addItemType(HomeBean.ITEM_TYPE_HOT, R.layout.layout_home_hot);
        addItemType(HomeBean.ITEM_TYPE_TITLETOP, R.layout.layout_title);
        addItemType(HomeBean.ITEM_TYPE_TOPIC, R.layout.layout_home_topiclist);
        addItemType(HomeBean.ITEM_TYPE_CATEGORY, R.layout.category_layout);
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeBean.HomeListBean item) {
        switch (item.getItemType()) {
            case HomeBean.ITEM_TYPE_TITLE:
                updateTitle(helper, (String) item.data);
                break;
            case HomeBean.ITEM_TYPE_TITLETOP:
                updateTitle(helper, (String) item.data);
                break;
            case HomeBean.ITEM_TYPE_BANNER:
                updateBanner(helper, (List<HomeBean.DataBean.BannerBean>) item.data);
                break;
            case HomeBean.ITEM_TYPE_TAB:
                updateTab(helper, (List<HomeBean.DataBean.ChannelBean>) item.data);
                break;
            case HomeBean.ITEM_TYPE_BRAND:
                updateBrand(helper, (HomeBean.DataBean.BrandListBean) item.data);
                break;
            case HomeBean.ITEM_TYPE_NEW:
                updateNewGood(helper, (HomeBean.DataBean.NewGoodsListBean) item.data);
                break;
            case HomeBean.ITEM_TYPE_HOT:
                udpateHot(helper, (HomeBean.DataBean.HotGoodsListBean) item.data);
                break;
            case HomeBean.ITEM_TYPE_TOPIC:
                updateTopic(helper, (List<HomeBean.DataBean.TopicListBean>) item.data);
                break;
            case HomeBean.ITEM_TYPE_CATEGORY:
                updateCategory(helper, (HomeBean.DataBean.CategoryListBean.GoodsListBean) item.data);
                break;
        }
    }

    private void updateCategory(BaseViewHolder helper, HomeBean.DataBean.CategoryListBean.GoodsListBean data) {
        if(!TextUtils.isEmpty(data.getList_pic_url())){
            Glide.with(context).load(data.getList_pic_url()).into((ImageView) helper.getView(R.id.category_img));
        }
        helper.setText(R.id.category_name,data.getName());
        String price = string.replace("$",String.valueOf(data.getRetail_price()));
        helper.setText(R.id.category_retail_price,price);
    }

    private void updateTopic(BaseViewHolder helper, List<HomeBean.DataBean.TopicListBean> data) {
        RecyclerView recyclerView = helper.getView(R.id.recyclerviewTopic);
        if(topicAdapter == null){
            topicAdapter = new TopicAdapter(context,data);
            recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,RecyclerView.HORIZONTAL));
            recyclerView.setAdapter(topicAdapter);
            topicAdapter.setOnClickListener(new TopicAdapter.OnClickListener() {
                @Override
                public void onClick(int position) {
                    MainActivity mainActivity= (MainActivity) context;
                    Intent intent = new Intent(context, TopicActivity.class);
                    intent.putExtra("id", data.get(position).getId());
                    mainActivity.startActivity(intent);
                }
            });
        }else if(recyclerView.getAdapter() == null){
            recyclerView.setAdapter(topicAdapter);

        }
    }


    private void udpateHot(BaseViewHolder helper, HomeBean.DataBean.HotGoodsListBean data) {
        if(!TextUtils.isEmpty(data.getList_pic_url())){
            Glide.with(context).load(data.getList_pic_url()).into((ImageView) helper.getView(R.id.img_hot));
        }
        helper.setText(R.id.txt_hot_name,data.getName());
        helper.setText(R.id.txt_hot_title,data.getGoods_brief());
        String price = string.replace("$",String.valueOf(data.getRetail_price()));
        helper.setText(R.id.txt_hot_price,price);
    }


    private void updateNewGood(BaseViewHolder helper, HomeBean.DataBean.NewGoodsListBean data) {
        if(!TextUtils.isEmpty(data.getList_pic_url())){
            Glide.with(context).load(data.getList_pic_url()).into((ImageView) helper.getView(R.id.newproduct_img));
        }
        helper.setText(R.id.newproduct_name,data.getName());
        String price = string.replace("$",String.valueOf(data.getRetail_price()));
        helper.setText(R.id.newproduct_retail_price,price);
    }

    private void updateBrand(BaseViewHolder helper, HomeBean.DataBean.BrandListBean data) {
        if(!TextUtils.isEmpty(data.getNew_pic_url())){
            Glide.with(context).load(data.getNew_pic_url()).into((ImageView) helper.getView(R.id.brand_img));
        }
        helper.setText(R.id.brand_name,data.getName());
        String price = string.replace("$",String.valueOf(data.getFloor_price()));
        helper.setText(R.id.brand_floor_price,price);
    }




    private void updateTab(BaseViewHolder helper, List<HomeBean.DataBean.ChannelBean> data) {
        LinearLayout layoutChannels = helper.getView(R.id.home_tab);
        if (layoutChannels.getChildCount() == 0) {
            for (HomeBean.DataBean.ChannelBean item : data) {
                TextView tab = new TextView(context);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1);
                int size = SystemUtils.dp2px(context, 5);
                tab.setLayoutParams(params);
                tab.setTextSize(size);
                tab.setText(item.getName());
                tab.setGravity(Gravity.CENTER);

                Drawable icon = context.getDrawable(R.mipmap.canchu);

// 调用setCompoundDrawables时，必须调用Drawable.setBounds()方法,否则图片不显示
                icon.setBounds(0, 0, icon.getMinimumWidth(), icon.getMinimumHeight());
                tab.setCompoundDrawables(null, icon, null, null);
                openBidevent(item.getIcon_url(), tab, item.getName());
                layoutChannels.addView(tab);
            }
        }
    }
    private void openBidevent(String icon_url, final TextView textView, String name) {
        Glide.with(context).asBitmap().load(icon_url).into(new SimpleTarget<Bitmap>(){
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                BitmapDrawable drawable= new BitmapDrawable(context.getResources(), resource);
                /// 这一步必须要做,否则不会显示.
                drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
                textView.setCompoundDrawables(null,drawable,null,null);
            }
        });
        textView.setText(name);
    }

    private void updateBanner(BaseViewHolder helper, List<HomeBean.DataBean.BannerBean> data) {
        Banner banner = helper.getView(R.id.home_ban);
        List<String> bannerUrls = new ArrayList<>();
        if (data.size() > 0) {
            for (int i = 0; i < data.size(); i++) {
                bannerUrls.add(data.get(i).getImage_url());
            }
        }
        if (banner != null && banner.getTag() == null) {
            banner.setImages(bannerUrls).setImageLoader(new ImageLoader() {
                @Override
                public void displayImage(Context context, Object path, ImageView imageView) {
                    RoundedCorners roundedCorners = new RoundedCorners(20);
                    RequestOptions transform = new RequestOptions().transform(roundedCorners);
                    Glide.with(context).load(path).apply(transform).into(imageView);
                }
            }).start();
            banner.setTag("hexizhe");
        }

    }

    private void updateTitle(BaseViewHolder helper, String data) {
        helper.setText(R.id.txt_title, data);

    }
}


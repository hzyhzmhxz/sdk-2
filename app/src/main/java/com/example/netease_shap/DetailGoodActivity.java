package com.example.netease_shap;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.netease_shap.base.BaseActivity;
import com.example.netease_shap.bean.AddCartInfoBean;
import com.example.netease_shap.bean.GoodDetailBean;
import com.example.netease_shap.common.CartCustomView;
import com.example.netease_shap.insertfaces.ICart;
import com.example.netease_shap.mvp.presenter.CartPersenter;
import com.example.netease_shap.ui.adapter.IssueAdapter;
import com.example.netease_shap.ui.fragment.ShoppingCartFragment;
import com.example.netease_shap.util.SpUtils;
import com.example.netease_shap.util.SystemUtils;
import com.example.netease_shap.util.ToastUtils;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailGoodActivity extends BaseActivity<ICart.IPersenter> implements ICart.IView, View.OnClickListener {


    @BindView(R.id.layout_back)
    RelativeLayout layoutBack;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.txt_name)
    TextView txtName;
    @BindView(R.id.txt_des)
    TextView txtDes;
    @BindView(R.id.txt_price)
    TextView txtPrice;
    @BindView(R.id.txt_product)
    TextView txtProduct;
    @BindView(R.id.layout_norms)
    FrameLayout layoutNorms;
    @BindView(R.id.layout_comment)
    FrameLayout layoutComment;
    @BindView(R.id.layout_imgs)
    LinearLayout layoutImgs;
    @BindView(R.id.layout_parameter)
    LinearLayout layoutParameter;
    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.layout_collect)
    RelativeLayout layoutCollect;
    @BindView(R.id.img_cart)
    ImageView imgCart;
    @BindView(R.id.layout_cart)
    RelativeLayout layoutCart;
    @BindView(R.id.txt_buy)
    TextView txtBuy;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.size)
    TextView size;
    @BindView(R.id.lining)
    TextView lining;
    @BindView(R.id.filler)
    TextView filler;
    @BindView(R.id.weight)
    TextView weight;
    @BindView(R.id.hint)
    TextView hint;
    @BindView(R.id.rv_issue)
    RecyclerView rvIssue;
    @BindView(R.id.botton)
    LinearLayout botton;
    @BindView(R.id.txt_addCart)
    TextView txtAddCart;
    @BindView(R.id.txt_adddnum)
    TextView txtAdddnum;
    private String html = "<html>\n" +
            "            <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no\"/>\n" +
            "            <head>\n" +
            "                <style>\n" +
            "                    p{\n" +
            "                        margin:0px;\n" +
            "                    }\n" +
            "                    img{\n" +
            "                        width:100%;\n" +
            "                        height:auto;\n" +
            "                    }\n" +
            "                </style>\n" +
            "            </head>\n" +
            "            <body>\n" +
            "                $\n" +
            "            </body>\n" +
            "        </html>";
    private PopupWindow mPopWindow;
    private ArrayList<GoodDetailBean.DataBeanX.IssueBean> issueBeans;
    private IssueAdapter issueAdapter;
    private int currentNum = 1;
    private GoodDetailBean goodDetailBean;

    @Override
    protected void initData() {
        int id = getIntent().getIntExtra("id", 0);
        mpresenter.getGoodDetail(id);
    }

    @Override
    protected CartPersenter initPresenter() {
        return new CartPersenter();
    }

    @Override
    protected void initView() {
        rvIssue.setLayoutManager(new LinearLayoutManager(this));
        issueBeans = new ArrayList<>();
        issueAdapter = new IssueAdapter(this, issueBeans);
        rvIssue.setAdapter(issueAdapter);
        layoutNorms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopWindow();
            }
        });
        layoutCollect.setOnClickListener(this);
        txtAddCart.setOnClickListener(this);
        layoutCart.setOnClickListener(this);
    }

    @Override
    protected int getlayout() {
        return R.layout.activity_detail_good;
    }


    @Override
    public void getGoodDetailReturn(GoodDetailBean result) {
        goodDetailBean = result;
        List<String> bannerUrls = new ArrayList<>();
        if (result.getData().getGallery().size() > 0) {
            for (int i = 0; i < result.getData().getGallery().size(); i++) {
                bannerUrls.add(result.getData().getGallery().get(i).getImg_url());
            }
        }
        if (banner != null && banner.getTag() == null) {
            banner.setImages(bannerUrls).setImageLoader(new ImageLoader() {
                @Override
                public void displayImage(Context context, Object path, ImageView imageView) {

                    Glide.with(context).load(path).into(imageView);
                }
            }).start();
            banner.setTag("hexizhe");
        }

        txtName.setText(result.getData().getInfo().getName());
        txtDes.setText(result.getData().getInfo().getGoods_brief());
        txtPrice.setText("¥" + result.getData().getInfo().getRetail_price());
        txtProduct.setText(result.getData().getBrand().getName() + ">");
        tvTime.setText(result.getData().getComment().getData().getAdd_time() + "");
        tvContent.setText(result.getData().getComment().getData().getContent());
        layoutParameter.removeAllViews();  //clear linearlayout
        for (int i = 0; i < result.getData().getComment().getData().getPic_list().size(); i++) {
            ImageView imageView = new ImageView(this);
            String pic_url = result.getData().getComment().getData().getPic_list().get(i).getPic_url();

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(10, 0, 10, 10);
            imageView.setLayoutParams(params);  //设置图片宽高

            new Thread(new Runnable() {
                Drawable drawable = loadImageFromNetwork(pic_url);

                @Override
                public void run() {

                    // post() 特别关键，就是到UI主线程去更新图片
                    imageView.post(new Runnable() {
                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            imageView.setImageDrawable(drawable);
                        }
                    });
                }

            }).start();
            layoutParameter.setOrientation(LinearLayout.HORIZONTAL);
            layoutParameter.addView(imageView); //动态添加图片
        }

        updateDetailInfo(result.getData().getInfo());
        issueBeans.addAll(result.getData().getIssue());
        issueAdapter.notifyDataSetChanged();
    }


    private Drawable loadImageFromNetwork(String data) {
        Drawable drawable = null;
        try {
            drawable = Drawable.createFromStream(
                    new URL(data).openStream(), "aaa.jpg");

        } catch (IOException e) {
            Log.d("test", e.getMessage());
        }
        if (drawable == null) {
            Log.d("test", "null drawable");
        } else {
            Log.d("test", "not null drawable");
        }
        return drawable;
    }

    private void updateDetailInfo(GoodDetailBean.DataBeanX.InfoBean infoBean) {
        if (!TextUtils.isEmpty(infoBean.getGoods_desc())) {
            String h5 = infoBean.getGoods_desc();
            html = html.replace("$", h5);

            webView.loadDataWithBaseURL("about:blank", html, "text/html", "utf-8", null);
            //webView.loadData(html,"text/html","utf-8");
        }
    }

    private void showPopWindow() {
        if (mPopWindow != null && mPopWindow.isShowing()) {

        } else {
            View contentView = LayoutInflater.from(this).inflate(R.layout.layout_popwindow_good, null);
            int height = SystemUtils.dp2px(this, 250);
            mPopWindow = new PopupWindow(contentView, LinearLayout.LayoutParams.MATCH_PARENT, height);
            mPopWindow.setContentView(contentView);
            mPopWindow.setOutsideTouchable(false);
            mPopWindow.setFocusable(false);
            contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
            CartCustomView cartCustomView = contentView.findViewById(R.id.layout_cartwindow);
            TextView txtClose = contentView.findViewById(R.id.txt_close);
            TextView txt_price = contentView.findViewById(R.id.txt_price);
            ImageView img_good = contentView.findViewById(R.id.img_good);
            txt_price.setText(goodDetailBean.getData().getInfo().getRetail_price()+"元起");
            Glide.with(this).load(goodDetailBean.getData().getInfo().getList_pic_url()).into(img_good);
            backgroundAlpha(0.5f);
            mPopWindow.setOnDismissListener(new poponDismissListener());
            txtClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mPopWindow.dismiss();
                    mPopWindow = null;
                }
            });
            int[] pt = new int[2];
            //获取到的屏幕宽高(除开了当前组件的宽高）
            botton.getLocationInWindow(pt);
            // Display display = getWindowManager().getDefaultDisplay();
            // int activityheight = display.getHeight();
            mPopWindow.showAtLocation(botton, Gravity.NO_GRAVITY, 0, pt[1] - height);
            cartCustomView.initView();
            cartCustomView.setOnClickListener(new CartCustomView.IClick() {
                @Override
                public void clickCB(int value) {
                    //value
                    currentNum = value;
                }
            });
        }
    }

    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);

    }




    class poponDismissListener implements PopupWindow.OnDismissListener {

        @Override
        public void onDismiss() {
            // TODO Auto-generated method stub
            //Log.v("List_noteTypeActivity:", "我是关闭事件");
            backgroundAlpha(1f);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_collect:
                break;
            case R.id.txt_addCart:
                addCart();
                break;
            case R.id.layout_cart:

                setResult(1000);
                finish();
                break;
        }
    }

    private void addCart() {
        String token = SpUtils.getInstance().getString("token");
        if (!TextUtils.isEmpty(token)) {
            //判断当前的规格弹框是否打开
            if (mPopWindow != null && mPopWindow.isShowing()) {
                //添加到购物车的操作
                if (goodDetailBean != null && goodDetailBean.getData().getProductList().size() > 0) {
                    Log.d(TAG, "addCart: "+goodDetailBean.getData().getProductList().size());
                    int goodsId = goodDetailBean.getData().getProductList().get(0).getGoods_id();
                    int productId = goodDetailBean.getData().getProductList().get(0).getId();
                    mpresenter.addCart(goodsId, currentNum, productId);
                    mPopWindow.dismiss();
                    mPopWindow = null;
                } else {
                    Toast.makeText(this, "没有产品数据", Toast.LENGTH_SHORT).show();
                }
            } else {
                showPopWindow();
            }
        } else {
            Toast.makeText(this, "未登录", Toast.LENGTH_SHORT).show();
            //Intent跳转到登录


        }
    }

    //添加到购物车返回
    @Override
    public void addCartInfoReturn(AddCartInfoBean result) {

        if(result.getErrno()==0){
            int count = result.getData().getCartTotal().getGoodsCount();
            Log.d(TAG, "addCartInfoReturn: "+count);
            txtAdddnum.setText(String.valueOf(count));
            ToastUtils.onLongToast("添加成功");
        }   else {
            ToastUtils.onLongToast("库从不足");
        }


    }

    private static final String TAG = "DetailGoodActivity";


}
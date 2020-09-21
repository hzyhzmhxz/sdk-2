package com.example.netease_shap.net;






import com.example.netease_shap.bean.AddCartInfoBean;
import com.example.netease_shap.bean.AdressBean;
import com.example.netease_shap.bean.BrandinfoBean;
import com.example.netease_shap.bean.DeleteCartBean;
import com.example.netease_shap.bean.GoodDetailBean;
import com.example.netease_shap.bean.HomeBean;
import com.example.netease_shap.bean.MUJIbean;
import com.example.netease_shap.bean.ShoppingCartBean;
import com.example.netease_shap.bean.TopicBean;
import com.example.netease_shap.bean.TopicListBean;

import io.reactivex.Flowable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Apiservice {
 @GET("index")
    Flowable<HomeBean> getHome();
    //商品购买页详情
    @GET("goods/detail")
    Flowable<GoodDetailBean> getGoodDetail(@Query("id") int id);
    @GET("brand/detail")
    Flowable<BrandinfoBean> getbrandinfo(@Query("id") int id);
   @GET("goods/list")
   Flowable<MUJIbean> getgoodlist(@Query("id") int id);
    @GET("topic/detail")
    Flowable<TopicBean> gettopic(@Query("id") int id);
    @GET("topic/related")
    Flowable<TopicListBean> gettopiclist(@Query("id") int id);
    //添加到购物车
    @POST("cart/add")
    @FormUrlEncoded
    Flowable<AddCartInfoBean> addCart(@Field("goodsId") int goodsId, @Field("number") int number, @Field("productId") int productId);

    @GET("cart/index")
    Flowable<ShoppingCartBean> getCartList();

    //删除购物车
    @POST("cart/delete")
    @FormUrlEncoded
    Flowable<DeleteCartBean> cartDelete(@Field("productIds") String productIds);
        //地址管理
    @GET("region/list")
    Flowable<AdressBean> getAdressById(@Query("parentId") int parentId);

}

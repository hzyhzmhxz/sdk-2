package com.example.netease_shap;

import android.os.Bundle;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.netease_shap.base.BaseActivity;
import com.example.netease_shap.bean.TopicBean;
import com.example.netease_shap.bean.TopicListBean;
import com.example.netease_shap.insertfaces.Itopic;
import com.example.netease_shap.mvp.presenter.TopicPersenter;
import com.example.netease_shap.ui.adapter.TopicListAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TopicActivity extends BaseActivity<Itopic.Ipresenter> implements Itopic.Iview {

    @BindView(R.id.topic_wv)
    WebView topicWv;
    @BindView(R.id.topic_write)
    ImageView topicWrite;

    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.topic_more)
    TextView topicMore;

    @BindView(R.id.topic_rv)
    RecyclerView topicRv;
    private String htmlStr = "<html>\n" +
            "            <head>" +
            "                        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\"> " +
            "                        <style>*{margin:0;padding:0;}img{max-width: 100%; width:auto; height:auto;}</style>" +
            "            </head>" +
            "            <body>\n" +
            "                $\n" +
            "            </body>\n" +
            "        </html>";
    private ArrayList<TopicListBean.DataBean> dataBeans;
    private TopicListAdapter topicListAdapter;

    @Override
    protected void initData() {
        int id = getIntent().getIntExtra("id", 0);
        Log.d(TAG, "initData: " + id);
        mpresenter.gettopic(id);
        mpresenter.gettopiclist(id);
    }

    @Override
    protected TopicPersenter initPresenter() {
        return new TopicPersenter();
    }

    @Override
    protected void initView() {
    topicRv.setLayoutManager(new LinearLayoutManager(this));
        dataBeans = new ArrayList<>();
        topicListAdapter = new TopicListAdapter(this, dataBeans);
        topicRv.setAdapter(topicListAdapter);

    }

    @Override
    protected int getlayout() {
        return R.layout.activity_topic;
    }


    @Override
    public void gettopic(TopicBean topicBean) {
        updateDetailInfo(topicBean.getData());
    }

    private static final String TAG = "TopicActivity";

    private void updateDetailInfo(TopicBean.DataBean infoBean) {
        int start = 0;
        int index = infoBean.getContent().indexOf("//");
        String html = infoBean.getContent().substring(index + 2);
        StringBuilder sb = new StringBuilder();
        sb.append(infoBean.getContent().substring(start, index));
        while (index != -1) {
            sb.append("http://");
            index = html.indexOf("//");
            if (index == -1) {
                sb.append(html);
                break;
            }
            sb.append(html.substring(start, index));
            html = html.substring(index + 2);
        }
        htmlStr = htmlStr.replace("$", sb.toString());
        topicWv.setWebChromeClient(new WebChromeClient());
        topicWv.getSettings().setJavaScriptEnabled(true);
        topicWv.loadData(htmlStr, "text/html; charset=utf-8", "utf-8");
    }

    @Override
    public void gettopiclist(TopicListBean topicListBean) {
        dataBeans.addAll(topicListBean.getData());
        topicListAdapter.notifyDataSetChanged();
    }



}
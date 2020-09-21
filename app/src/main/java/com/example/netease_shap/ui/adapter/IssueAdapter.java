package com.example.netease_shap.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.netease_shap.R;
import com.example.netease_shap.base.BaseAdapter;
import com.example.netease_shap.bean.GoodDetailBean;

import java.util.List;

public class IssueAdapter extends BaseAdapter {
    public IssueAdapter(Context context, List datas) {
        super(context, datas);
    }

    @Override
    protected int getlayout() {
        return R.layout.issue_layout;
    }

    @Override
    protected void bindData(BaseViewHolder baseViewHolder, Object o, int position) {
        GoodDetailBean.DataBeanX.IssueBean data= (GoodDetailBean.DataBeanX.IssueBean) o;
        TextView tv_issue = (TextView) baseViewHolder.getviewbyid(R.id.tv_issue);
        TextView tv_answer = (TextView) baseViewHolder.getviewbyid(R.id.tv_answer);
        tv_issue.setText(data.getQuestion());
        tv_answer.setText(data.getAnswer());
    }
}

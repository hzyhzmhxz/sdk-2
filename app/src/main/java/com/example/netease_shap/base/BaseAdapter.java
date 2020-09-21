package com.example.netease_shap.base;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public abstract class BaseAdapter<T> extends RecyclerView.Adapter {


    protected Context context;
    protected List<T> datas;
    private IClick callback;
    public void setClick(IClick cb){
        callback = cb;
    }
    public BaseAdapter(Context context, List<T> datas) {
        this.context = context;
        this.datas = datas;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(getlayout(), parent,false);
        final BaseViewHolder baseViewHolder = new BaseViewHolder(view);
        baseViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (callback!=null){
                    callback.click(baseViewHolder.getLayoutPosition());
                }
            }
        });
        return baseViewHolder;
    }

    protected abstract int getlayout();

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        BaseViewHolder baseViewHolder=(BaseViewHolder) holder;
        T t = datas.get(position);
        bindData(baseViewHolder,t,position);
    }

    protected abstract void bindData(BaseViewHolder baseViewHolder, T t, int position);
    public interface IClick{
        void click(int pos);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }


    public class BaseViewHolder extends RecyclerView.ViewHolder {
        //SparseArray就是稀疏数组
        SparseArray sparseArray=new SparseArray();
        public BaseViewHolder(@NonNull View itemView) {
            super(itemView);

        }
        public View getviewbyid(int id){
            View view= (View) sparseArray.get(id);
            if(view==null){
                view = itemView.findViewById(id);
                sparseArray.append(id,view);
            }
            return view;
        }
    }


}

package com.example.liangmutian.airrecyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.liangmutian.airrecyclerview.swipetoloadlayout.BaseRecyclerAdapter;

import java.util.List;


/**
 * Created by ex-liyongqiang001 on 16/7/1.
 */
public class Adapter extends BaseRecyclerAdapter<BaseRecyclerAdapter.BaseRecyclerViewHolder, ActType> {

    private List<ActType> list;

    public Adapter(List<ActType> list) {
        super(list);
        this.list = list;
    }

    @Override
    public BaseRecyclerViewHolder createViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
        BaseRecyclerViewHolder holder = null;
        holder = new ProductHolder(inflater.inflate(R.layout.item_choose_act_type, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(BaseRecyclerViewHolder holder, int position, final ActType data) {
        ProductHolder productHolder = (ProductHolder) holder;
        productHolder.tvName.setText(data.name);
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }


    class ProductHolder extends BaseRecyclerViewHolder {
        public TextView tvName;

        public ProductHolder(View itemView) {
            super(itemView);
            tvName = findView(R.id.tv_type_name);

        }
    }
}

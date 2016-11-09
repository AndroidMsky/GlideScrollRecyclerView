package com.example.liangmutian.airrecyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.example.liangmutian.airrecyclerview.swipetoloadlayout.BaseRecyclerAdapter;

import java.util.List;


/**
 */
public class Adapter extends BaseRecyclerAdapter<BaseRecyclerAdapter.BaseRecyclerViewHolder, ActType> {

    private List<ActType> list;
    private Context mContext;
    private String url1 = "http://img2.imgtn.bdimg.com/it/u=2313041955,732263015&fm=21&gp=0.jpg";
    private String url2 = "http://p4.yokacdn.com/pic/cr/2013/0115/2026031677.jpg";
    private String url3 = "http://img5.imgtn.bdimg.com/it/u=3931738195,194607292&fm=21&gp=0.jpg";
    private String url4 = "http://img0.imgtn.bdimg.com/it/u=1174544731,79277711&fm=21&gp=0.jpg";


    public Adapter(List<ActType> list, Context context) {
        super(list);
        this.mContext = context;
        this.list = list;
    }

    @Override
    public BaseRecyclerViewHolder createViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
        BaseRecyclerViewHolder holder = null;


        switch (viewType) {
            case ActType.SHOW_TYPE_1:
                holder = new ProductHolder(inflater.inflate(R.layout.item_choose_act_type, parent, false));
                break;
            case ActType.SHOW_TYPE_2:
                holder = new ProductHolder2(inflater.inflate(R.layout.item_choose_act_type2, parent, false));
                break;

        }
        return holder;


    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position).showType;
    }

    @Override
    public void onBindViewHolder(BaseRecyclerViewHolder holder, final int position, final ActType data) {


        switch (getItemViewType(position)) {
            case ActType.SHOW_TYPE_1:
                ProductHolder productHolder = (ProductHolder) holder;
                productHolder.tvName.setText(data.name);

                productHolder.imgRight.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(mContext, "" + position, Toast.LENGTH_LONG).show();
                    }
                });

                if (data.name.equals("01"))
                    Glide.with(mContext).load(url1)
                            .placeholder(R.mipmap.ic_launcher)
                            .centerCrop()
                            .into(productHolder.imgRight);
                if (data.name.equals("02"))
                    Glide.with(mContext).load(url2)
                            .placeholder(R.mipmap.ic_launcher)
                            .centerCrop()
                            .into(productHolder.imgRight);
                if (data.name.equals("03"))
                    Glide.with(mContext).load(url4)
                            .placeholder(R.mipmap.ic_launcher)
                            .centerCrop()
                            .into(productHolder.imgRight);
                if (data.name.equals("04"))
                    Glide.with(mContext).load(url3)
                            .skipMemoryCache(true)
                            .centerCrop()
                            .into(productHolder.imgRight);


                break;
            case ActType.SHOW_TYPE_2:


                ProductHolder2 productHolder2 = (ProductHolder2) holder;
                productHolder2.tvName.setText(data.name);

                productHolder2.imgRight.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(mContext, "w:::" + position, Toast.LENGTH_LONG).show();
                    }
                });

                if (data.name.equals("01"))
                    Glide.with(mContext).load(url1)
                            .placeholder(R.mipmap.ic_launcher)
                            .centerCrop()
                            .into(productHolder2.imgRight);
                if (data.name.equals("02"))
                    Glide.with(mContext).load(url2)
                            .placeholder(R.mipmap.ic_launcher)
                            .centerCrop()
                            .into(productHolder2.imgRight);
                if (data.name.equals("03"))
                    Glide.with(mContext).load(url4)
                            .placeholder(R.mipmap.ic_launcher)
                            .centerCrop()
                            .into(productHolder2.imgRight);
                if (data.name.equals("04"))
                    Glide.with(mContext).load(url3)
                            .skipMemoryCache(true)
                            .centerCrop()
                            .into(productHolder2.imgRight);

                break;

        }


    }

    class ProductHolder extends BaseRecyclerViewHolder {
        public TextView tvName;
        public ImageView imgRight;

        public ProductHolder(View itemView) {
            super(itemView);
            tvName = findView(R.id.tv_type_name);
            imgRight = findView(R.id.img_right);

        }
    }

    class ProductHolder2 extends BaseRecyclerViewHolder {
        public TextView tvName;
        public ImageView imgRight;

        public ProductHolder2(View itemView) {
            super(itemView);
            tvName = findView(R.id.tv_type_name);
            imgRight = findView(R.id.img_right);

        }
    }
}

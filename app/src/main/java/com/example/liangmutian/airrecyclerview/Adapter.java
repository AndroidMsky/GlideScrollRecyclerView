package com.example.liangmutian.airrecyclerview;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.example.liangmutian.airrecyclerview.swipetoloadlayout.BaseRecyclerAdapter;

import java.util.List;


/**
 */
public class Adapter extends BaseRecyclerAdapter<BaseRecyclerAdapter.BaseRecyclerViewHolder, ActType> {

    private List<ActType> list;
    private Context mContext;

    private boolean skipcache = true;


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

                Glide.with(mContext).load(data.URl)
                        .placeholder(R.mipmap.ic_launcher)
                        .centerCrop()
                        .skipMemoryCache(skipcache)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .into(new GlideDrawableImageViewTarget(productHolder.imgRight) {
                            @Override
                            public void onResourceReady(GlideDrawable drawable, GlideAnimation anim) {
                                super.onResourceReady(drawable, anim);
                                Log.d("-------------------+", "" + (position+1));
                            }
                        });


//                Glide.with(mContext).load(data.URl)
//                        .placeholder(R.mipmap.ic_launcher)
//                        .centerCrop()
//                        .skipMemoryCache(true)
//                        .diskCacheStrategy(DiskCacheStrategy.NONE)
//                        .into();


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


                Glide.with(mContext).load(data.URl)
                        .placeholder(R.mipmap.ic_launcher)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(skipcache)

                        .centerCrop()

                        .into(new GlideDrawableImageViewTarget(productHolder2.imgRight) {
                            @Override
                            public void onResourceReady(GlideDrawable drawable, GlideAnimation anim) {
                                super.onResourceReady(drawable, anim);
                                Log.d("--------------------", "" + (position+1));
                                //progressBar.setVisibility(View.GONE);
                            }
                        });
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
        public  ImageView imgRight;

        public ProductHolder2(View itemView) {
            super(itemView);
            tvName = findView(R.id.tv_type_name);
            imgRight = findView(R.id.img_right);

        }
    }
}

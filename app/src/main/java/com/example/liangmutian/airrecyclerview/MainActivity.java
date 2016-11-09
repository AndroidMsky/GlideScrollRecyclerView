package com.example.liangmutian.airrecyclerview;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.example.liangmutian.airrecyclerview.swipetoloadlayout.OnLoadMoreListener;
import com.example.liangmutian.airrecyclerview.swipetoloadlayout.OnRefreshListener;
import com.example.liangmutian.airrecyclerview.swipetoloadlayout.SuperRefreshRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements OnRefreshListener, OnLoadMoreListener {

    private SuperRefreshRecyclerView superRecyclerView;
    private Adapter adapter;
    private List<ActType> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        superRecyclerView = (SuperRefreshRecyclerView) findViewById(R.id.super_recyclerview);
        superRecyclerView.init(new LinearLayoutManager(this), this, this);
        superRecyclerView.setRefreshEnabled(true);
        superRecyclerView.setLoadingMoreEnable(true);
        // superRecyclerView.addOnAttachStateChangeListener();


        for (int i = 0; i < 3; i++) {
            ActType info5 = new ActType("01", 1);
            ActType info3 = new ActType("02", 2);
            ActType info2 = new ActType("03", 1);
            list.add(info5);
            list.add(info3);
            list.add(info2);
        }
        adapter = new Adapter(list, this);
        superRecyclerView.setAdapter(adapter);
        superRecyclerView.showData();
        // adapter.setOnItemClickListener(itemClickListener);


    }

    private void setData() {
        list.clear();
        for (int i = 0; i < 3; i++) {
            ActType info5 = new ActType("01", 2);
            ActType info3 = new ActType("02", 1);
            ActType info2 = new ActType("03", 2);
            list.add(info5);
            list.add(info3);
            list.add(info2);
        }

    }

    @Override
    public void onLoadMore() {


        ActType info2 = new ActType("04", 1);
        ActType info3 = new ActType("03", 1);
        ActType info4 = new ActType("02", 2);


        list.add(info2);
        list.add(info3);

        list.add(info4);


        adapter.notifyDataSetChanged();

        superRecyclerView.setLoadingMore(false);

    }

    @Override
    public void onRefresh() {

        new Handler().postDelayed(new Runnable() {

            public void run() {

                setData();
                superRecyclerView.setRefreshing(false);

            }

        }, 3000);

    }
}

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


        for (int i = 0; i < 10; i++) {
            ActType info5 = new ActType("01");
            ActType info3 = new ActType("02");
            ActType info2 = new ActType("03");
            list.add(info5);
            list.add(info3);
            list.add(info2);
        }
        adapter = new Adapter(list);
        superRecyclerView.setAdapter(adapter);
        superRecyclerView.showData();
        // adapter.setOnItemClickListener(itemClickListener);


    }

    private void setData(){
        list.clear();
        for (int i = 0; i < 100; i++) {
            ActType info5 = new ActType("01");
            ActType info3 = new ActType("02");
            ActType info2 = new ActType("03");
            list.add(info5);
            list.add(info3);
            list.add(info2);
        }

    }

    @Override
    public void onLoadMore() {


        ActType info2 = new ActType("new03");


        list.add(info2);

        adapter.notifyDataSetChanged();

        superRecyclerView.setLoadingMore(false);

        superRecyclerView.moveToPosition(list.size() - 1);


    }

    @Override
    public void onRefresh() {

        new Handler().postDelayed(new Runnable(){

            public void run() {

                setData();
                superRecyclerView.setRefreshing(false);

                //execute the task

            }

        }, 3000);



       //superRecyclerView.setRefreshing(false);


    }
}

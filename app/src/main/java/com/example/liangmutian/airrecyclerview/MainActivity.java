package com.example.liangmutian.airrecyclerview;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.example.liangmutian.airrecyclerview.swipetoloadlayout.ChangeScrollStateCallback;
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

    private String url1 = "http://img2.imgtn.bdimg.com/it/u=2313041955,732263015&fm=21&gp=0.jpg";
    private String url2 = "http://p4.yokacdn.com/pic/cr/2013/0115/2026031677.jpg";
    private String url3 = "http://img5.imgtn.bdimg.com/it/u=3931738195,194607292&fm=21&gp=0.jpg";
    private String url4 = "http://img0.imgtn.bdimg.com/it/u=1174544731,79277711&fm=21&gp=0.jpg";

    private String url11 = "http://a.hiphotos.baidu.com/zhidao/pic/item/4afbfbedab64034ff00f9069a9c379310b551d9a.jpg";
    private String url12 = "http://www.286868.org.cn/imgall/mnsg453fmixgenlnfzrw63i/web/cmsphp/article/201505/8d19ff6db94a466c861f4adf760e3d4e.jpg";
    private String url13 = "http://imgsrc.baidu.com/forum/w%3D580/sign=8f6610cf5e6034a829e2b889fb1249d9/9e0049afa40f4bfbedae079a014f78f0f6361887.jpg";
    private String url14 = "http://www.1919111.net/imgall/on2gc5djmmxhoz3qmv2c4y3pnu/editor/attached/image/20141124/20141124225933_71813.jpg";
    private String url15 = "http://img4.duitang.com/uploads/item/201407/16/20140716112423_BBvCR.thumb.700_0.jpeg";
    private String url16 = "http://www.22777788.cc/imgall/nfwwomzomrxxkytbnyxgg33n/view/commodity_story/imedium/public/p7634330.jpg";
    private String url17 = "http://cdn.duitang.com/uploads/item/201501/07/20150107194411_VQEAy.thumb.700_0.jpeg";
    private String url18 = "http://static.wgpet.com/editor/attached/image/20141124/20141124230747_89589.jpeg";
    private String url19 = "http://img4.duitang.com/uploads/item/201601/28/20160128211836_ivVs2.thumb.700_0.jpeg";
    private String url110 = "http://cdnq.duitang.com/uploads/item/201412/11/20141211192322_EytUS.jpeg";
    private String url111 = "";
    private String url112= "";
    private String url113 = "";
    private String url114= "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        superRecyclerView = (SuperRefreshRecyclerView) findViewById(R.id.super_recyclerview);
        superRecyclerView.init(new LinearLayoutManager(this), this, this);
        superRecyclerView.setRefreshEnabled(true);
        superRecyclerView.setLoadingMoreEnable(true);
        // superRecyclerView.addOnAttachStateChangeListener();

        superRecyclerView.setChangeScrollStateCallback(new ChangeScrollStateCallback() {

            //                     0 表示停止滑动的状态 SCROLL_STATE_IDLE
//
//                    1表示正在滚动，用户手指在屏幕上 SCROLL_STATE_TOUCH_SCROLL
//
//                    2表示正在滑动。用户手指已经离开屏幕 SCROLL_STATE_FLING
            @Override
            public void change(int c) {

                switch (c) {
                    case 2:
                        Glide.with(MainActivity.this).pauseRequests();
                         Log.d("AAAAAAAAAAAAAAA", "暂停加载" + c);

                        break;
                    case 0:
                        Glide.with(MainActivity.this).resumeRequests();
                        Log.d("AAAAAAAAAAAAAAA","恢复加载" + c);

                        break;
                    case 1:
                        Glide.with(MainActivity.this).resumeRequests();
                        Log.d("AAAAAAAAAAAAAAA", "恢复加载" + c);

                        break;


                }

            }
        });


        for (int i = 0; i < 5; i++) {
            ActType info1 = new ActType("01", 1, url11);
            ActType info2 = new ActType("02", 1, url12);
            ActType info3 = new ActType("03", 1, url13);
            ActType info4 = new ActType("04", 1, url14);
            ActType info5 = new ActType("05", 1, url15);
            ActType info6 = new ActType("06", 1, url16);
            ActType info7 = new ActType("07", 1, url17);


            list.add(info1);
            list.add(info2);
            list.add(info3);
            list.add(info4);
            list.add(info5);
            list.add(info6);
            list.add(info7);

        }
        adapter = new Adapter(list, this);
        superRecyclerView.setAdapter(adapter);
        superRecyclerView.showData();
        // adapter.setOnItemClickListener(itemClickListener);


    }

    private void setData() {
        list.clear();
        for (int i = 0; i < 3; i++) {
            ActType info5 = new ActType("01", 2, url11);
            ActType info3 = new ActType("04", 1, url4);
            ActType info2 = new ActType("02", 2, url2);
            list.add(info5);
            list.add(info3);
            list.add(info2);
        }

    }

    @Override
    public void onLoadMore() {


        ActType info2 = new ActType("04", 1, url4);
        ActType info3 = new ActType("02", 1, url2);
        ActType info4 = new ActType("01", 2, url11);


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

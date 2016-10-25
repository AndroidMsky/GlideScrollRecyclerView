package com.example.liangmutian.airrecyclerview.swipetoloadlayout;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.example.liangmutian.airrecyclerview.R;


/**
 * Created by ex-liyongqiang001 on 16/6/30.
 */
public class SuperRefreshRecyclerView extends FrameLayout {

    private RelativeLayout emptyView,errorView;
    private SwipeToLoadLayout swipeToLoadLayout;
    private SwipeLoadMoreFooterLayout swipeLoadMoreFooterLayout;
    private RecyclerView recyclerView;
    private boolean move;
    private int mIndex;
    private RecyclerView.LayoutManager layoutManager;

    private Context mContext;

    public SuperRefreshRecyclerView(Context context) {
        super(context);
        initView(context);
    }

    public SuperRefreshRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public SuperRefreshRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context){
        mContext=context;
        LayoutInflater.from(mContext).inflate(R.layout.layout_super_refresh_recycler,this);
        emptyView=(RelativeLayout)findViewById(R.id.layout_empty);
        errorView=(RelativeLayout)findViewById(R.id.layout_error);
        swipeToLoadLayout=(SwipeToLoadLayout)findViewById(R.id.swipe_to_load);
        swipeLoadMoreFooterLayout=(SwipeLoadMoreFooterLayout)findViewById(R.id.swipe_load_more_footer);
        recyclerView=(RecyclerView)findViewById(R.id.swipe_target);
    }


    public void init(RecyclerView.LayoutManager layoutManager,OnRefreshListener onRefreshListener,OnLoadMoreListener onLoadMoreListener){
        recyclerView.setLayoutManager(layoutManager);
        this.layoutManager=layoutManager;
        swipeToLoadLayout.setOnRefreshListener(onRefreshListener);
        swipeToLoadLayout.setOnLoadMoreListener(onLoadMoreListener);
        recyclerView.setOnScrollListener(new RecyclerViewListener());
    }

    public void showEmpty(OnClickListener onEmptyClick){
        swipeToLoadLayout.setVisibility(GONE);
        emptyView.setVisibility(VISIBLE);
        errorView.setVisibility(GONE);
        if(onEmptyClick!=null){
            emptyView.setOnClickListener(onEmptyClick);
        }
    }

    public void showError(OnClickListener onErrorClick){
        swipeToLoadLayout.setVisibility(GONE);
        emptyView.setVisibility(GONE);
        errorView.setVisibility(VISIBLE);
        if(onErrorClick!=null){
            errorView.setOnClickListener(onErrorClick);
        }
    }

    public void showData(){
        swipeToLoadLayout.setVisibility(VISIBLE);
        emptyView.setVisibility(GONE);
        errorView.setVisibility(GONE);
    }

    public void setEmptyView(View view){
        emptyView.removeAllViews();
        emptyView.addView(view);
    }

    public boolean isRefreshing(){
        return swipeToLoadLayout.isRefreshing();
    }

    public void setRefreshing(boolean refreshing){
        swipeToLoadLayout.setRefreshing(refreshing);
    }

    public boolean isLoadingMore(){
        return swipeToLoadLayout.isLoadingMore();
    }

    public void setLoadingMore(boolean loadMore){
        swipeToLoadLayout.setLoadingMore(loadMore);
    }

    public void setLoadingMoreEnable(boolean enable){
        swipeToLoadLayout.setLoadMoreEnabled(enable);
    }

    public void setRefreshEnabled(boolean enable){
        swipeToLoadLayout.setRefreshEnabled(enable);
    }

    public void setAdapter(RecyclerView.Adapter adapter){
        recyclerView.setAdapter(adapter);
    }

    public void addItemDecoration(RecyclerView.ItemDecoration itemDecoration){
        recyclerView.addItemDecoration(itemDecoration);
    }

    public void moveToPosition(int n) {
        mIndex=n;
        LinearLayoutManager  mLinearLayoutManager=(LinearLayoutManager)layoutManager;
        int firstItem = mLinearLayoutManager.findFirstVisibleItemPosition();
        int lastItem = mLinearLayoutManager.findLastVisibleItemPosition();
        //然后区分情况
        if (n <= firstItem ){
            //当要置顶的项在当前显示的第一个项的前面时
            recyclerView.scrollToPosition(n);
        }else if ( n <= lastItem ){
            //当要置顶的项已经在屏幕上显示时
            int top = recyclerView.getChildAt(n - firstItem).getTop();
            recyclerView.scrollBy(0, top);
        }else{
            //当要置顶的项在当前显示的最后一项的后面时
            recyclerView.scrollToPosition(n);
            //这里这个变量是用在RecyclerView滚动监听里面的
            move = true;
        }

    }

    class RecyclerViewListener extends RecyclerView.OnScrollListener{
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            //在这里进行第二次滚动（最后的100米！）
            if (move ){
                move = false;
                //获取要置顶的项在当前屏幕的位置，mIndex是记录的要置顶项在RecyclerView中的位置
                LinearLayoutManager  mLinearLayoutManager=(LinearLayoutManager)layoutManager;
                int n = mIndex - mLinearLayoutManager.findFirstVisibleItemPosition();
                if ( 0 <= n && n < recyclerView.getChildCount()){
                    //获取要置顶的项顶部离RecyclerView顶部的距离
                    int top = recyclerView.getChildAt(n).getTop();
                    //最后的移动
                    recyclerView.scrollBy(0, top);
                }
            }
        }
    }
}

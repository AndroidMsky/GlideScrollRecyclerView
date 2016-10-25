package com.example.liangmutian.airrecyclerview.swipetoloadlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.example.liangmutian.airrecyclerview.R;


/**
 * Created by Aspsine on 2015/8/13.
 */
public class SwipeLoadMoreFooterLayout extends FrameLayout implements SwipeLoadMoreTrigger, SwipeTrigger {

    private Context mContext;

    public SwipeLoadMoreFooterLayout(Context context) {
        this(context, null);
        init(context);
    }

    public SwipeLoadMoreFooterLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        init(context);
    }

    public SwipeLoadMoreFooterLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        mContext=context;
        LayoutInflater.from(mContext).inflate(R.layout.layout_footer_loading, this);
    }

    @Override
    public void onLoadMore() {
        //setVisibility(VISIBLE);
        Log.i("info", "onLoadMore");
    }

    @Override
    public void onPrepare() {
        Log.i("info","onPrepare");
    }

    @Override
    public void onMove(int y, boolean isComplete, boolean automatic) {
        //Log.i("info","onMove");
    }

    @Override
    public void onRelease() {
        Log.i("info","onRelease");
    }

    @Override
    public void onComplete() {
        //setVisibility(GONE);
        Log.i("info","onComplete");
    }

    @Override
    public void onReset() {
        Log.i("info","onReset");
    }

}

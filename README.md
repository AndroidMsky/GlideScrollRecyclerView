
让RecyclerView优先加载可见item，如何在RecyclerView滑动中停止图片的加载保证页面流畅。

先看效果图：
![这里写图片描述](http://img.blog.csdn.net/20161110135815675)
![这里写图片描述](http://img.blog.csdn.net/20161110135838081)

额。算了再流畅GIF也无法展示，想看效果去GITHUB下载代码吧。
本文Github代码链接 
https://github.com/AndroidMsky/GlideScrollRecyclerView
欢迎star哦。


对下拉刷新上滑加载RecyclerView还不明白的请看：
http://blog.csdn.net/androidmsky/article/details/52922348
对多样式布局Gilde基本用法还不懂得请看：
http://blog.csdn.net/androidmsky/article/details/52944370
两篇都是笔者的博文，相信入门搭建起可用框架还是有帮助的。

让我们来回顾一下陈旧的Listview，如果item中有网络图片，那时候我们怎么做的呢？我们要注意一下几个重点：
1.网络图片获取
2.网络图片二级缓存（至少一级缓存）
3.在高速滑动时候停止图片加载线程
4.在低速滑动，停止滑动时候开启线程
5.下拉刷新
6.上滑加载
7.优先加载可见item

如果不用第三方框架，自己维护线程池等等东西，想想都复杂。而如今我们不需要这些东西了，本文的Dome轻轻松松做到这7点。
首先1 2  glide给予我们完美的支持，5 6 我前文提到过的RecyclerView也有完美的支持。今天主要讲 3 4 7这三点如何实现。

首先看7.优先加载可见item，之前在listview我们可能这样做，写个滑动监听并且获取可见item的位置，然后加载对应图片，那么我们使用RecyclerView+Gilde到底如何做这一点呢，笔者一开始也有些犹豫，于是直接issues到了github ：

https://github.com/bumptech/glide/issues/1575#event-852660755

![这里写图片描述](http://img.blog.csdn.net/20161110141357542)


看来老外对于自己的亲儿子项目还是很关爱的生怕我不懂啪啪啪举了几个例子，我只好用CET3.9的水平翻译一下了。
我的意思是当glide和recyclerview配合使用的时候，glide会不会优先加载可见item。
TWiStErRob大概说，所有的加载优先级都是默认的，但是可以通过设置priority来这是优先级，但是不是在特别的情况下这个方法一般不被使用，他举了个例子说，如果一个item中有3个图片，他们的信息有重叠的部分，其中有一张图是最重要，信息最全的时候，可以提高这张图的优先级。
然而TWiStErRob给我的答案似乎并没有理解我想问的（可能是我CET3.9）的原因，我想算啦吧，问别人不如自己实践出来的更真实。于是我准备出如下场景：
1.网速很渣（因为速度快瞬间加载完很多图难以测试）
2.打开一个游戏的下载（先把比较渣的网速给我占了！）
3.item比较多
4.当前可见item比较少
5.设置Glide跳过二级缓存
6.对Glide加载成功进行监听并且打印图片对应位置log


```
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
```

首先进来缓慢滑动几次：

![这里写图片描述](http://img.blog.csdn.net/20161110143357771)

前四张图片被加载了符合预期。


接下来我们快速滑动到底部：

![这里写图片描述](http://img.blog.csdn.net/20161110143657469)


如我所料，优先加载了可见的底部的item。

到这里7问题就解决了。我们以正缺的姿势去使用RecyclerView+Gilde就可以了。

接下来是：
3.在高速滑动时候停止图片加载线程
4.在低速滑动，停止滑动时候开启线程
RecyclerView是可以滑动监听的但是别高兴的太早：

```
SuperRefreshRecyclerView.一脸懵逼
```
你点不出设置滑动监听方法，其实是这样的：

```
public class SuperRefreshRecyclerView extends FrameLayout {

    private RelativeLayout emptyView,errorView;
    private SwipeToLoadLayout swipeToLoadLayout;
    private SwipeLoadMoreFooterLayout swipeLoadMoreFooterLayout;
    private RecyclerView recyclerView;
```
SuperRefreshRecyclerView中有个原声的RecyclerView所以我们肯定不能直接.到RecyclerView的方法了。于是决定自己写个callback接口就好了：

```
public interface ChangeScrollStateCallback  {

    public void change(int c);

}
```
然后提供set方法：

```
public void setChangeScrollStateCallback(ChangeScrollStateCallback mChangeScrollStateCallback){
        this.mChangeScrollStateCallback=mChangeScrollStateCallback;

    }
```

并在Activity中调用：

```
superRecyclerView.setChangeScrollStateCallback(new ChangeScrollStateCallback() {

//0 表示停止滑动的状态 SCROLL_STATE_IDLE
//1表示正在滚动，用户手指在屏幕上 SCROLL_STATE_TOUCH_SCROLL
//2表示正在滑动。用户手指已经离开屏幕 SCROLL_STATE_FLING
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
```

Glide提供的暂停恢复方法有没有很爽：

```
Glide.with(MainActivity.this).pauseRequests();
Glide.with(MainActivity.this).resumeRequests();
                       
```
                       

高速滑动一下屏幕：

![这里写图片描述](http://img.blog.csdn.net/20161110144657107)


缓慢滑动屏幕：

![这里写图片描述](http://img.blog.csdn.net/20161110144753311)


一切符合预期，1-7点我们就这样轻松的完成了。同事也给大家建议，在怀疑一个地方的时候不妨打断点打日志去看看到底是怎么回事，不断提高自己解决问题的能力，不要只会问而不会做。

欢迎关注作者。欢迎评论讨论。欢迎拍砖。
如果觉得这篇文章对你有帮助，欢迎打赏，
欢迎star，Fork我的github。
喜欢作者的也可以Follow。也算对作者的一种支持。 
本文Github代码链接 
https://github.com/AndroidMsky/GlideScrollRecyclerView



欢迎加作者自营安卓开发交流群：308372687 

![这里写图片描述](http://img.blog.csdn.net/20161028111556438)


博主原创未经允许不得转载，转载必究。





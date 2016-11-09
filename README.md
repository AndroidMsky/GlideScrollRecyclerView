# AirRecyclerViewGlide
AirRecyclerView+Glide+layouts
今天准时给大家更新啦，RecyclerView的第二篇，加入图片加载框架Glide和多样式布局。
第一篇关于RecyclerView还不了解请阅读：
http://blog.csdn.net/androidmsky/article/details/52922348
本文Github链接如下：
https://github.com/AndroidMsky/AirRecyclerViewGlide
国际惯例效果图如下：

![这里写图片描述](http://img.blog.csdn.net/20161027111646419)


先介绍多样式布局，可分为三步走，**第一步**在bean类中加入控制布局的int字段，来供给Adapter选择布局。这里我们定义两种布局，一种图片在中间背景白色，一种图片在左边背景粉色。
 
  Bean代码如下：
  

```
public class ActType implements Serializable {
    public static final int SHOW_TYPE_1=1;
    public static final int SHOW_TYPE_2=2;
    public int showType;
    public String name;
    public String code;
    public ArrayList<ActType> actTypeList=new ArrayList<>();
    public ActType(String code,int showType){
        this.showType=showType;
        this.name= code;
        this.code=code;
    }
```


第二步在createViewHolder让你adapter去选择两种VIewHolder

  

```
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
```
并且创建两个Holder类：

```
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
```
第三步在onBindViewHolder去指定每个Holder的内容：

```
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


                ／／此处省略Type2的内容代码，和1基本一样。
 

                break;

        }


    }
```

经过三步走，相信多种布局样式可以搞定， 接下来的是如何使用Glide。

```
Glide.with(mContext).load(url4)
.placeholder(R.mipmap.ic_launcher)
.centerCrop().into(productHolder.imgRight);
```
看看我们需要什么参数，上下文，图片url，默认显示图片，一个imgView。就是这么简单，轻轻松松给RecyclerView加入喽图片，而且Glide给我处理了二级缓存，很多东西我们都可以不去关心。

```
 compile files('libs/glide-3.6.1.jar')
```
笔者是用jar引入的Glide大家也可以用其它方式引入，这个简单精益的小Glide相信大家会喜欢。

通过这两篇文章，相信大家都可以弄出一个带图片。类似微笑朋友圈展示的简单效果。
当然这两篇文章还都是介绍肿么用，很多框架都有很多值得注意的坑，我会在这一系列的第三篇文章中编写。



欢迎关注作者。欢迎评论讨论。欢迎拍砖。 
如果觉得这篇文章对你有帮助 欢迎**star我的github**。也算对笔者的一种支持。 
本文Github代码链接 
https://github.com/AndroidMsky/AirRecyclerViewGlide

欢迎加作者自营安卓开发交流群：308372687

博主原创未经允许不许转载。

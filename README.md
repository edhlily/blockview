# blockview
Android动态格子布局（淘宝，京东等首页）

![https://github.com/zjsx/blockview/blob/master/screen/blockview-01.png?raw=true](https://github.com/zjsx/blockview/blob/master/screen/blockview-01.png?raw=true)

![https://github.com/zjsx/blockview/blob/master/screen/blockview-05.png?raw=true](https://github.com/zjsx/blockview/blob/master/screen/blockview-05.png?raw=true)

这是一套可以开发动态商城首页，营造节日气氛，在线更新布局的Android动态布局框架。框架和核心View继承RecyclerView并包装holder的处理。开发者只需关心JSON的编写以及按照接口定义完成自定义布局的编写。目前已经默认支持9种布局及控件。

```Android
//本库默认支持的Block
regesterBlock("gallery", new GalleryContainer());//画廊一类的布局
regesterBlock("table", new TableContainer());//表格布局
regesterBlock("grid", new GridContainer());//跨行跨列的表格布局
regesterBlock("linear", new Linear());//线性布局
regesterBlock("frame", new Frame());//帧布局

regesterBlock("text", new TextItem());//文字控件
regesterBlock("image", new ImageItem());//图片控件
regesterBlock("verticalBanner", new VerticalBanner());//垂直滚动的Banner
regesterBlock("horizontalBanner", new HorizontalBanner());//水平滚动的Banner
```

以及9种单位的定义

    //本库默认支持的sizeParser
    globalSizeParsers.add(new EmptyNullParser());//空的情况
    globalSizeParsers.add(new PXParser());//px单位
    globalSizeParsers.add(new DPParser(context));//dp
    globalSizeParsers.add(new WrapParser());//自适应
    globalSizeParsers.add(new FillParser());//适应父控件
    globalSizeParsers.add(new SWParser());//屏幕宽度百分比
    globalSizeParsers.add(new SHParser());//屏幕高度百分比


> 后面有时间会对这些控件和单位进行详细的说明



## 使用方法



#### 导入blocklayout Libaray,在布局中添加blockview



```xml
<com.zjsx.blocklayout.widget.BlockView
        android:id="@+id/rvMain"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="vertical"
        android:overScrollMode="never"
        android:scrollbars="none" />
```



#### 实现BlockManager

```java
public abstract class BlockManager {

    /**
     * 这个模块是否可以点击
     * 判断一个block是否可以点击，先判断block的clickable是否为true
     * 如果clickable为true，再调用这个方法进一步自定义是否可以点击。
     * @param block
     */
    public abstract boolean isClickable(Block block);

    /**
     * 处理点击事件
     *
     * @param block
     */
    public abstract void onItemClick(Block block);

    /**
     * 加载图片
     *
     * @param context
     * @param url
     * @param imageView
     */
    public abstract void loadImg(Context context, String url, final ImageView imageView);

    /**
     * 设置背景
     *
     * @param target
     * @param url
     * @param errorBgColor
     */
    public abstract void setBackgroundImage(View target, String url, int errorBgColor);
}
```

设置

```
blockView.setBlockManager(blockManager);
```



将json转化为bean，bean实现BlockConfig或者继承DefaultBlockConfig



```
public interface BlockConfig {
    /**
     * blockView背景颜色
     * @return
     */
    String getBackColor();

    /**
     * blockView背景图
     * @return
     */
    String getBackImage();

    /**
     * blockView里面的模块
     * @return
     */
    ArrayList<Block> getModules();

    /**
     * blockView水平划分几格
     * @return
     */
    int getCellCount();


    /**
     * blockView的布局方式，支持三种
     * 1: linear 默认 一行一个大模块，参考：RecyclerView.LinearLayoutManager
     * 2: grid 一行多个模块，#getCellCount决定一行几个模块，参考:RecyclerView.GridLayoutManager
     * 3: stagger 一行多个模块，模块间可以不对齐，参考:RecyclerView.StaggerGridLayoutManager
     *
     * 分别对应常量：
     *
     * int LAYOUT_TYPE_LINEAR = 0;
     * int LAYOUT_TYPE_GRID = 1;
     * int LAYOUT_TYPE_STAGGER = 2;
     *
     * @return
     */
    String getLayout();

    /**
     * 大模块之间的间隔
     * @return
     */
    String getSpacing();

    /**
     * 间隔模式
     * 1:middle 默认 只有大模块之间有间隔
     * 2:around 大模块之间以及外层布局之间都有间隔
     *
     * 分别对应常量：
     * int SPACING_MODE_MIDDLE = 0;
     * int SPACING_MODE_AROUND = 1;
     *
     * @return
     */
    String getSpacingMode();


    /**
     * 那些模块外围有间隔
     * 1：decentralized 默认 那些不占满格子（cellSpan!=cellCount）的模块才有
     * 2: all 所有的模块都有
     *
     * 分别对应常量：
     *int SPACING_ITEM_DECENTRALIZED = 0;
     *int SPACING_ITEM_ALL = 1;
     *
     * DECENTRALIZED
     * @return
     */
    String getSpacingItem();

}
```

加载bean并解析成布局

```
//加载布局配置文件
blockView.setConfig(config);
```

这时候布局就会被加载出来了，那么核心是布局文件是如何定义的呢？下面是一个示例：

```json
{
  "modules": [
    {
      "type": "horizontalBanner",
      "height": "0.5sw",
      "autoScroll": true,
      "manual": true,
      "duration": 1000,
      "delay": 1000,
      "items": [
        {
          "type": "image",
          "src": "file:///android_asset/jd01/images/720p_01.jpg",
          "link": "海天梦享"
        },
        {
          "type": "image",
          "src": "file:///android_asset/jd01/images/720p_01.jpg",
          "link": "海天梦享"
        }
      ]
    }]
}
```

那么上边的json会加载成一个水平自动滚动的banner，这个banner滚动一次1s，然后暂停1s，重复轮播。

modules里面可以添加任意多个子item，item的类型使用type定义，控件和容器是同等的地位，可以嵌套等等。还可以自定义item类型，这个参见里面的demo。

下面看一套仿淘宝的json:目前使用图片填充，自己实现时可以图片和文字分开（参考京东DEMO里面的值得买和京东体育的控件的实现）。

先看下效果：

![https://github.com/zjsx/blockview/blob/master/screen/blockview-01.png?raw=true](https://github.com/zjsx/blockview/blob/master/screen/blockview-01.png?raw=true)

![https://github.com/zjsx/blockview/blob/master/screen/blockview-02.png?raw=true](https://github.com/zjsx/blockview/blob/master/screen/blockview-02.png?raw=true)

![https://github.com/zjsx/blockview/blob/master/screen/blockview-03.png?raw=true](https://github.com/zjsx/blockview/blob/master/screen/blockview-03.png?raw=true)

json文件

```
{
  "layout": "stagger",
  "cellCount": 2,
  "spacing": "5dp",
  "spacingMode": "middle",
  "spacingItem": "decentralized",
  "backColor": "#EBEBEB",
  "backImage": "",
  "modules": [
    {
      "type": "horizontalBanner",
      "height": "0.3sw",
      "autoScroll": true,
      "manual": true,
      "duration": 1000,
      "delay": 1000,
      "items": [
        {
          "type": "image",
          "src": "assets/taobao01/images/taobao(01).jpg",
          "link": "海天梦享"
        },
        {
          "type": "image",
          "src": "assets/taobao01/images/taobao(02).jpg",
          "link": "海天梦享"
        },
        {
          "type": "image",
          "src": "assets/taobao01/images/taobao(03).jpg",
          "link": "海天梦享"
        },
        {
          "type": "image",
          "src": "assets/taobao01/images/taobao(04).jpg",
          "link": "海天梦享"
        },
        {
          "type": "image",
          "src": "assets/taobao01/images/taobao(05).jpg",
          "link": "海天梦享"
        },
        {
          "type": "image",
          "src": "assets/taobao01/images/taobao(06).jpg",
          "link": "海天梦享"
        }
      ]
    },
    {
      "type": "table",
      "link": "menu",
      "backColor": "#FFFFFF",
      "backImage": "",
      "rowHeight": "1bw",
      "roundTopLeft": "10",
      "roundTopRight": "10",
      "colCount": 5,
      "items": [
        {
          "type": "image",
          "src": "assets/taobao01/images/taobao (1).gif",
          "link": "京东超市"
        },
        {
          "type": "image",
          "src": "assets/taobao01/images/taobao (2).gif",
          "link": "全球购"
        },
        {
          "type": "image",
          "src": "assets/taobao01/images/taobao (3).gif",
          "link": "服装城"
        },
        {
          "type": "image",
          "src": "assets/taobao01/images/taobao (4).gif",
          "link": "京东生鲜"
        },
        {
          "type": "image",
          "src": "assets/taobao01/images/taobao (5).gif",
          "link": "京东到家"
        },
        {
          "type": "image",
          "src": "assets/taobao01/images/taobao (6).gif",
          "link": "充值中心"
        },
        {
          "type": "image",
          "src": "assets/taobao01/images/taobao (7).gif",
          "link": "领京豆"
        },
        {
          "type": "image",
          "src": "assets/taobao01/images/taobao (8).gif",
          "link": "领劵"
        },
        {
          "type": "image",
          "src": "assets/taobao01/images/taobao (9).gif",
          "link": "慧赚钱"
        },
        {
          "type": "image",
          "src": "assets/taobao01/images/taobao (10).gif",
          "link": "全部"
        }
      ]
    },
    {
      "type": "linear",
      "orientation": "horizontal",
      "height": "0.15sw",
      "items": [
        {
          "type": "image",
          "height": "fill",
          "width": "wrap",
          "src": "assets/taobao01/images/taobao (11).gif"
        },
        {
          "type": "verticalBanner",
          "height": "fill",
          "weight": 1,
          "autoScroll": true,
          "manual": true,
          "duration": 1000,
          "delay": 1000,
          "items": [
            {
              "type": "image",
              "width": "fill",
              "src": "assets/taobao01/images/taobao (12).gif"
            },
            {
              "type": "image",
              "width": "fill",
              "src": "assets/taobao01/images/taobao (12).gif"
            }
          ]
        }
      ]
    },
    {
      "type": "image",
      "marginTop": "10dp",
      "marginBottom": "10dp",
      "src": "assets/taobao01/images/taobao (13).gif",
      "link": "梦之蓝"
    },
    {
      "type": "table",
      "colCount": 2,
      "height": "0.6sw",
      "items": [
        {
          "type": "image",
          "src": "assets/taobao01/images/taobao (14).gif",
          "link": "超市"
        },
        {
          "type": "image",
          "src": "assets/taobao01/images/taobao (15).gif",
          "link": "nokia"
        },
        {
          "type": "image",
          "src": "assets/taobao01/images/taobao (16).gif",
          "link": "下单"
        },
        {
          "type": "image",
          "src": "assets/taobao01/images/taobao (17).gif",
          "link": "家电"
        }
      ]
    },
    {
      "type": "image",
      "marginTop": "10dp",
      "marginBottom": "10dp",
      "src": "assets/taobao01/images/taobao (18).gif",
      "link": "梦之蓝"
    },
    {
      "type": "image",
      "src": "assets/taobao01/images/taobao (19).gif",
      "link": "梦之蓝"
    },
    {
      "type": "grid",
      "height": "wrap",
      "rowHeight": "1.45bw",
      "rowCount": 2,
      "colCount": 5,
      "items": [
        {
          "type": "image",
          "rowStart": 1,
          "colStart": 1,
          "rowSpan": 1,
          "colSpan": 2,
          "src": "assets/taobao01/images/taobao (20).gif",
          "link": "3c"
        },
        {
          "type": "image",
          "rowStart": 1,
          "colStart": 3,
          "rowSpan": 1,
          "colSpan": 1,
          "src": "assets/taobao01/images/taobao (21).gif",
          "link": "家电"
        },
        {
          "type": "image",
          "rowStart": 1,
          "colStart": 4,
          "rowSpan": 1,
          "colSpan": 1,
          "src": "assets/taobao01/images/taobao (22).gif",
          "link": "超市"
        },
        {
          "type": "image",
          "rowStart": 1,
          "colStart": 5,
          "rowSpan": 1,
          "colSpan": 1,
          "src": "assets/taobao01/images/taobao (23).gif",
          "link": "爱家"
        },
        {
          "type": "image",
          "rowStart": 2,
          "colStart": 1,
          "rowSpan": 1,
          "colSpan": 2,
          "src": "assets/taobao01/images/taobao (24).gif",
          "link": "爱宝宝"
        },
        {
          "type": "image",
          "rowStart": 2,
          "colStart": 3,
          "rowSpan": 1,
          "colSpan": 1,
          "src": "assets/taobao01/images/taobao (25).gif",
          "link": "爱美丽"
        },
        {
          "type": "image",
          "rowStart": 2,
          "colStart": 4,
          "rowSpan": 1,
          "colSpan": 1,
          "src": "assets/taobao01/images/taobao (26).gif",
          "link": "爱吃"
        },
        {
          "type": "image",
          "rowStart": 2,
          "colStart": 5,
          "rowSpan": 1,
          "colSpan": 1,
          "src": "assets/taobao01/images/taobao (27).gif",
          "link": "爱逛"
        }
      ]
    },
    {
      "type": "image",
      "marginTop": "10dp",
      "src": "assets/taobao01/images/taobao (28).gif",
      "link": "梦之蓝"
    },
    {
      "type": "table",
      "colCount": 2,
      "height": "0.6sw",
      "items": [
        {
          "type": "image",
          "src": "assets/taobao01/images/taobao (29).gif",
          "link": "超市"
        },
        {
          "type": "image",
          "src": "assets/taobao01/images/taobao (30).gif",
          "link": "nokia"
        },
        {
          "type": "image",
          "src": "assets/taobao01/images/taobao (31).gif",
          "link": "下单"
        },
        {
          "type": "image",
          "src": "assets/taobao01/images/taobao (32).gif",
          "link": "家电"
        }
      ]
    },
    {
      "type": "image",
      "marginTop": "10dp",
      "src": "assets/taobao01/images/taobao (33).gif",
      "link": "梦之蓝"
    },
    {
      "type": "grid",
      "height": "wrap",
      "rowHeight": "1.45bw",
      "rowCount": 2,
      "colCount": 5,
      "items": [
        {
          "type": "image",
          "rowStart": 1,
          "colStart": 1,
          "rowSpan": 1,
          "colSpan": 2,
          "src": "assets/taobao01/images/taobao (34).gif",
          "link": "3c"
        },
        {
          "type": "image",
          "rowStart": 1,
          "colStart": 3,
          "rowSpan": 1,
          "colSpan": 1,
          "src": "assets/taobao01/images/taobao (35).gif",
          "link": "家电"
        },
        {
          "type": "image",
          "rowStart": 1,
          "colStart": 4,
          "rowSpan": 1,
          "colSpan": 1,
          "src": "assets/taobao01/images/taobao (36).gif",
          "link": "超市"
        },
        {
          "type": "image",
          "rowStart": 1,
          "colStart": 5,
          "rowSpan": 1,
          "colSpan": 1,
          "src": "assets/taobao01/images/taobao (37).gif",
          "link": "爱家"
        },
        {
          "type": "image",
          "rowStart": 2,
          "colStart": 1,
          "rowSpan": 1,
          "colSpan": 2,
          "src": "assets/taobao01/images/taobao (38).gif",
          "link": "爱宝宝"
        },
        {
          "type": "image",
          "rowStart": 2,
          "colStart": 3,
          "rowSpan": 1,
          "colSpan": 1,
          "src": "assets/taobao01/images/taobao (39).gif",
          "link": "爱美丽"
        },
        {
          "type": "image",
          "rowStart": 2,
          "colStart": 4,
          "rowSpan": 1,
          "colSpan": 1,
          "src": "assets/taobao01/images/taobao (40).gif",
          "link": "爱吃"
        },
        {
          "type": "image",
          "rowStart": 2,
          "colStart": 5,
          "rowSpan": 1,
          "colSpan": 1,
          "src": "assets/taobao01/images/taobao (41).gif",
          "link": "爱逛"
        }
      ]
    },
    {
      "type": "image",
      "src": "assets/taobao01/images/taobao (42).gif",
      "link": "梦之蓝"
    },
    {
      "type": "image",
      "src": "assets/taobao01/images/taobao (43).gif",
      "link": "梦之蓝"
    },
    {
      "type": "image",
      "marginTop": "10dp",
      "src": "assets/taobao01/images/taobao (44).gif",
      "link": "梦之蓝"
    },
    {
      "type": "grid",
      "height": "wrap",
      "rowHeight": "1.2bw",
      "rowCount": 4,
      "colCount": 4,
      "items": [
        {
          "type": "image",
          "rowStart": 1,
          "colStart": 1,
          "rowSpan": 1,
          "colSpan": 1,
          "src": "assets/taobao01/images/taobao (45).gif",
          "link": "3c"
        },
        {
          "type": "image",
          "rowStart": 1,
          "colStart": 2,
          "rowSpan": 1,
          "colSpan": 1,
          "src": "assets/taobao01/images/taobao (46).gif",
          "link": "家电"
        },
        {
          "type": "image",
          "rowStart": 1,
          "colStart": 3,
          "rowSpan": 1,
          "colSpan": 1,
          "src": "assets/taobao01/images/taobao (47).gif",
          "link": "超市"
        },
        {
          "type": "image",
          "rowStart": 1,
          "colStart": 4,
          "rowSpan": 1,
          "colSpan": 1,
          "src": "assets/taobao01/images/taobao (48).gif",
          "link": "爱家"
        },
        {
          "type": "image",
          "rowStart": 2,
          "colStart": 1,
          "rowSpan": 1,
          "colSpan": 2,
          "src": "assets/taobao01/images/taobao (49).gif",
          "link": "爱宝宝"
        },
        {
          "type": "image",
          "rowStart": 2,
          "colStart": 3,
          "rowSpan": 1,
          "colSpan": 2,
          "src": "assets/taobao01/images/taobao (50).gif",
          "link": "爱美丽"
        },
        {
          "type": "image",
          "rowStart": 3,
          "colStart": 1,
          "rowSpan": 1,
          "colSpan": 2,
          "src": "assets/taobao01/images/taobao (51).gif",
          "link": "爱吃"
        },
        {
          "type": "image",
          "rowStart": 3,
          "colStart": 3,
          "rowSpan": 1,
          "colSpan": 2,
          "src": "assets/taobao01/images/taobao (52).gif",
          "link": "爱逛"
        },
        {
          "type": "image",
          "rowStart": 4,
          "colStart": 1,
          "rowSpan": 1,
          "colSpan": 2,
          "src": "assets/taobao01/images/taobao (53).gif",
          "link": "爱吃"
        },
        {
          "type": "image",
          "rowStart": 4,
          "colStart": 3,
          "rowSpan": 1,
          "colSpan": 2,
          "src": "assets/taobao01/images/taobao (54).gif",
          "link": "爱逛"
        }
      ]
    },
    {
      "type": "image",
      "marginTop": "10dp",
      "src": "assets/taobao01/images/taobao (55).gif",
      "link": "梦之蓝"
    },
    {
      "type": "image",
      "height": "0.3sw",
      "src": "assets/taobao01/images/taobao (56).gif",
      "link": "梦之蓝"
    },
    {
      "type": "table",
      "colCount": 4,
      "height": "0.4sw",
      "items": [
        {
          "type": "image",
          "src": "assets/taobao01/images/taobao (57).gif",
          "link": "超市"
        },
        {
          "type": "image",
          "src": "assets/taobao01/images/taobao (58).gif",
          "link": "nokia"
        },
        {
          "type": "image",
          "src": "assets/taobao01/images/taobao (59).gif",
          "link": "下单"
        },
        {
          "type": "image",
          "src": "assets/taobao01/images/taobao (60).gif",
          "link": "家电"
        }
      ]
    },
    {
      "type": "image",
      "scaleType": "fitCenter",
      "src": "assets/taobao01/images/taobao (61).gif",
      "link": "梦之蓝"
    },
    {
      "type": "image",
      "marginTop": "10dp",
      "src": "assets/taobao01/images/taobao (62).gif",
      "link": "梦之蓝"
    },
    {
      "type": "table",
      "colCount": 2,
      "rowHeight": "0.8bw",
      "items": [
        {
          "type": "image",
          "src": "assets/taobao01/images/taobao (63).gif",
          "link": "超市"
        },
        {
          "type": "image",
          "src": "assets/taobao01/images/taobao (64).gif",
          "link": "nokia"
        },
        {
          "type": "image",
          "src": "assets/taobao01/images/taobao (65).gif",
          "link": "下单"
        },
        {
          "type": "image",
          "src": "assets/taobao01/images/taobao (66).gif",
          "link": "家电"
        },
        {
          "type": "image",
          "src": "assets/taobao01/images/taobao (67).gif",
          "link": "下单"
        },
        {
          "type": "image",
          "src": "assets/taobao01/images/taobao (68).gif",
          "link": "家电"
        }
      ]
    },
    {
      "type": "image",
      "marginTop": "10dp",
      "src": "assets/taobao01/images/taobao (69).gif",
      "link": "梦之蓝"
    },
    {
      "type": "image",
      "colSpan": 1,
      "width": "fill",
      "src": "assets/taobao01/images/taobao (29).gif",
      "link": "推荐产品1"
    },
    {
      "type": "image",
      "colSpan": 1,
      "width": "fill",
      "src": "assets/taobao01/images/taobao (70).gif",
      "link": "推荐产品1"
    },
    {
      "type": "image",
      "colSpan": 1,
      "width": "fill",
      "src": "assets/taobao01/images/taobao (71).gif",
      "link": "推荐产品2"
    },
    {
      "type": "image",
      "colSpan": 1,
      "width": "fill",
      "src": "assets/taobao01/images/taobao (72).gif",
      "link": "推荐产品3"
    },
    {
      "type": "image",
      "colSpan": 1,
      "width": "fill",
      "src": "assets/taobao01/images/taobao (72).gif",
      "link": "推荐产品4"
    },
    {
      "type": "image",
      "colSpan": 1,
      "width": "fill",
      "src": "assets/taobao01/images/taobao (74).gif",
      "link": "推荐产品5"
    },
    {
      "type": "image",
      "colSpan": 1,
      "width": "fill",
      "src": "assets/taobao01/images/taobao (75).gif",
      "link": "推荐产品6"
    },
    {
      "type": "image",
      "colSpan": 1,
      "width": "fill",
      "src": "assets/taobao01/images/taobao (76).gif",
      "link": "推荐产品6"
    },
    {
      "type": "image",
      "colSpan": 1,
      "width": "fill",
      "src": "assets/taobao01/images/taobao (77).gif",
      "link": "推荐产品6"
    },
    {
      "type": "image",
      "colSpan": 1,
      "width": "fill",
      "src": "assets/taobao01/images/taobao (78).gif",
      "link": "推荐产品6"
    },
    {
      "type": "image",
      "colSpan": 1,
      "width": "fill",
      "src": "assets/taobao01/images/taobao (79).gif",
      "link": "推荐产品6"
    },
    {
      "type": "image",
      "colSpan": 1,
      "width": "fill",
      "src": "assets/taobao01/images/taobao (80).gif",
      "link": "推荐产品6"
    },
    {
      "type": "image",
      "colSpan": 1,
      "width": "fill",
      "src": "assets/taobao01/images/taobao (81).gif",
      "link": "推荐产品6"
    },
    {
      "type": "image",
      "colSpan": 1,
      "width": "fill",
      "src": "assets/taobao01/images/taobao (82).gif",
      "link": "推荐产品6"
    },
    {
      "type": "image",
      "colSpan": 1,
      "width": "fill",
      "src": "assets/taobao01/images/taobao (83).gif",
      "link": "推荐产品6"
    },
    {
      "type": "image",
      "colSpan": 1,
      "width": "fill",
      "src": "assets/taobao01/images/taobao (84).gif",
      "link": "推荐产品6"
    },
    {
      "type": "image",
      "colSpan": 1,
      "width": "fill",
      "src": "assets/taobao01/images/taobao (85).gif",
      "link": "推荐产品6"
    },
    {
      "type": "image",
      "colSpan": 1,
      "width": "fill",
      "src": "assets/taobao01/images/taobao (86).gif",
      "link": "推荐产品6"
    },
    {
      "type": "image",
      "colSpan": 1,
      "width": "fill",
      "src": "assets/taobao01/images/taobao (87).gif",
      "link": "推荐产品6"
    }
  ]
}
```


#### 下面是粗略实现京东首页的截图





##### 注：由于时间关系目前文档还不完善，后面会逐步完善。

任何疑问+QQ:990401226(再见思想)
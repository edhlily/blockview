# 默认支持的Block模块配置

框架主要支持两大类的模块，第一种是可以包含其它模块的，我们姑且称它为容器，第二种当然就是不能再添加其它模块的，我们就叫做控件吧。当然你也可以定义一个复杂的容器，里面包含很复杂的控件，然后将这整个容器当成一个不能再添加其它控件的“控件”。这些模块都可以设置属性，正如Android中的view一样，所有的模块都有共同的属性，一些模块有自己特有的属性，也有一些属性只有在特定的情况下才有用。

## 所有模块共有的属性

所以这些控件都支持以下属性，当然部分属性只有在某些特定的容器中或者条件下才会起作用。

| 属性名              | 作用                | 描述                                       |
| ---------------- | ----------------- | ---------------------------------------- |
| type             | 模块类型，类型在整个配置中唯一   | 系统默认支持：text,image,frame,linear,table,grid, gallery,verticalBanner,horizontalBanner |
| name             | 模块名               | 方便识别和配置                                  |
| width            | 模块的宽度             | 宽度可以有多种配置方案，支持wrap,fill,dp,px,sw,sh等配置，还可以自定义，具体单位和大小配置请参照：`单位和大小`， 在以下情况中不起作用1：作为最外层的模块，宽度会撑满屏幕；2：verticalBanner,horizontalBanner的直接子模块会填满banner；3：grid和table的直接子模块会填满格子 |
| height           | 模块的高度             | 同宽度，且如果作为gallery的直接子模块高度不起作用，会填满高度。      |
| backColor        | 背景颜色              | 支持16进制的格式：#FFFFFF,同样支持透明度：#55000000      |
| backImage        | 背景图片              | 可以配置本地图片或者在线图片，只需要实现BlockManager.setBackgroundImage即可 |
| marginBottom     | 距离下面模块的距离         | 单位也可以支持多种配置方案，参见`单位和大小`                  |
| marginTop        | 距离上面模块的距离         | 参见`单位和大小`                                |
| marginLeft       | 距离左边模块的距离         | 参见`单位和大小`                                |
| marginRight      | 距离右边模块的距离         | 参见`单位和大小`                                |
| paddingBottom    | 下边距               | 参见`单位和大小`                                |
| paddingTop       | 上边距               | 参见`单位和大小`                                |
| paddingLeft      | 左边距               | 参见`单位和大小`                                |
| paddingRight     | 右边距               | 参见`单位和大小`                                |
| roundTopLeft     | 左上圆角大小            | 参见`单位和大小`                                |
| roundTopRight    | 右上圆角大小            | 参见`单位和大小`                                |
| roundBottomLeft  | 左下圆角大小            | 参见`单位和大小`                                |
| roundBottomRight | 右下圆角大小            | 参见`单位和大小`                                |
| rowStart         | 起始行               | 数字                                       |
| colStart         | 起始列               | 数字                                       |
| rowSpan          | 占多少行              | 数字                                       |
| colSpan          | 占多少列              | 数字                                       |
| layoutGravity    | 在父布局中的位置          | 和LinearLayout，FramLayout中的控件的layoutGravity属性作用类似，只有在linear或frame模块中且作为linear或frame的直接子模块时有效,可选`center`,`left`,`top`,`right`,`bottom`,`centerVertical`,`centerHorizontal`,还可以多个属性组合使用，中间使用“\|”分隔 |
| weight           | 在linear中占宽度或高度的比重 | 和LinearLayout中的控件的weight作用类似，只有在linear模块中且作为linear的直接子模块时有效 |
| link             | 用于点击后触发不同的事件      | 类似url功能，建议使用acitvity://user/center?version=1，http[s]://github.com/zjsx;app://taobao?from=blockview等形式 |
| clickable        | 当前模块是否支持点击        | 默认true                                   |

### 控件：不能再添加其它容器或者布局的模块

#### text

显示文字的模块，同Android中的TextView控件。

| 属性名       | 作用        | 描述                                       |
| --------- | --------- | ---------------------------------------- |
| text      | 距离左边模块的距离 | 参见`单位和大小`                                |
| textColor | 文字颜色      | 支持16进制，如#FFFFFF                          |
| gravity   | 文字的位置     | `center`,`left`,`top`,`right`,`bottom`,`centerVertical`,`centerHorizontal`可以使用“\|”来组合使用 |
| textSize  | 文字的大小     | 参见`单位和大小`                                |
| maxLine   | 最大多少行     |                                          |
| textStyle | 文字样式      | 支持`bold`,`boldItalic`,`italic`,`normal`,默认为`normal` |

示例01：

```
{
  "layout": "grid",
  "cellCount": 2,
  "backColor": "#000000",
  "debug": true,
  "version": 1.0,
  "modules": [
    {
      "type": "text",
      "text": "DEMO",
      "height": "wrap",
      "width": "fill",
      "textSize": "18sp",
      "marginTop": "50dp",
      "paddingTop": "10dp",
      "paddingBottom": "10dp",
      "paddingLeft": "0.1sw",
      "paddingRight": "0.1sw",
      "textColor": "#000000",
      "textStyle": "bold",
      "backColor": "#FF0000",
      "gravity": "center",
      "link": "demo"
    }
  ]
}
```

效果01：

#### image

显示图片的模块，同Android中的ImageView

| 属性名       | 作用     | 描述                                       |
| --------- | ------ | ---------------------------------------- |
| src       | 图片地址   | 需要实现BlockManager.loadImg方法               |
| scaleType | 图片缩放类型 | 和ImageView一样支持：`center`，`centerCrop`，`centerInside`，`fitCenter`，`fitEnd`，`fitStart`，`matrix`，`fitXY`,默认`fitXY` |

示例02：

```
{
  ...
  "modules": [
    ...,
    {
      "type": "image",
      "src": "file:///android_asset/jd01/images/720p_12.jpg",
      "link": "梦之蓝"
    }
  ]
}
```

效果02：

### 容器：可以添加其它容器或者控件的布局模块

#### frame

正如Android中的FrameLayout一样，里面的控件可以按照层级叠加在一起。

| 属性名   | 作用                  | 描述                                       |
| ----- | ------------------- | ---------------------------------------- |
| items | List<Block>,用来存放子模块 | 子模块可以是任意可识别的类型，并且里面的类型不要求一致，比如里面可以放一个text，一个image和一个linear容器。 |

示例03：

```
{
  ...
  "modules": [
    ...,
    {
      "type": "frame",
      "link": "值得买",
      "items": [
        {
          "type": "image",
          "width": "fill",
          "scaleType": "fitCenter",
          "backColor": "#FF0000",
          "src": "assets/taobao01/images/taobao (13).gif"
        },
        {
          "type": "text",
          "text": "Frame测试",
          "backColor": "#55000000",
          "style": "bold",
          "width": "fill",
          "gravity": "center",
          "textColor": "#FFFFFF",
          "layoutGravity": "bottom"
        },
        {
          "type": "image",
          "src": "file:///android_asset/jd01/images/new.png",
          "width": "0.2sw",
          "height": "wrap",
          "scaleType": "fitCenter",
          "layoutGravity": "top|left"
        }
      ]
    }
  ]
}

```

效果03：

#### linear

正如Android中的LinearLayout一样，可以纵向或者横向排列子控件。

| 属性名         | 作用                  | 描述                                |
| ----------- | ------------------- | --------------------------------- |
| orientation | 方向                  | 水平布局：`vertical`，纵向布局：`horizontal` |
| items       | List<Block>,用来存放子模块 |                                   |

示例04：

```
{
  ...
  "modules": [
    {
      "type": "linear",
      "height": "wrap",
      "width": "fill",
      "orientation": "vertical",
      "items": [
        {
          "type": "text",
          "text": "item1",
          "height": "wrap",
          "width": "fill",
          "textSize": "18sp",
          "paddingTop": "10dp",
          "paddingBottom": "10dp",
          "paddingLeft": "0.1sw",
          "paddingRight": "0.1sw",
          "textColor": "#000000",
          "textStyle": "bold",
          "backColor": "#FF0000",
          "gravity": "center",
          "link": "item1"
        },
        ...
      ]
    },
    {
      "type": "linear",
      "height": "wrap",
      "width": "fill",
      "orientation": "horizontal",
      "items": [
        ...
      ]
    }
  ]
}
```

效果04：

#### table

表格布局，可以纵向划分均匀的划分为多个格子。

| 属性名       | 作用                         | 描述        |
| --------- | -------------------------- | --------- |
| colCount  | 横向划分为几个格子                  | 数字        |
| spacing   | 格子之间的空隙是                   | 参见`单位和大小` |
| rowHeight | 如果不指定table的高度的话，需要指定每一行的高度 | 参见`单位和大小` |
| items     | List<Block>,用来存放子模块        |           |

示例05：

```
{
  ...
  "modules": [
    {
      "type": "table",
      "height": "wrap",
      "colCount": 5,
      "rowHeight": "1bw",
      "spacing": "10dp",
      "paddingTop": "10dp",
      "paddingBottom": "10dp",
      "paddingLeft": "10dp",
      "paddingRight": "10dp",
      "width": "fill",
      "items": [
        {
          "type": "text",
          "text": "item1",
          "textSize": "18sp",
          "textColor": "#000000",
          "textStyle": "bold",
          "backColor": "#FF0000",
          "gravity": "center",
          "link": "item1"
        },
        ...
      ]
    },
    {
      "type": "table",
      "height": "wrap",
      "colCount": 4,
      "rowHeight": "0.1sw",
      "spacing": "1dp",
      "width": "fill",
      "items": [
        ...
      ]
    }
  ]
}
```

效果05：



#### grid

格子布局，同Android GridLayout布局，可以横向和纵向划分为多个格子，且子控件可以跨行或者跨列。

| 属性名       | 作用                         | 描述        |
| --------- | -------------------------- | --------- |
| rowCount  | 纵向划分为几行                    | 数字        |
| colCount  | 横向划分为几列                    | 数字        |
| spacing   | 子控件之间的间隔                   | 参见`单位和大小` |
| rowHeight | 如果不指定整个grid的高度那么就需要指定一行的高度 | 参见`单位和大小` |
| items     | List<Block>,用来存放子模块        |           |

示例06：

```
{
  "layout": "grid",
  "cellCount": 2,
  "backColor": "#000000",
  "debug": true,
  "version": 1.0,
  "modules": [
    {
      "type": "grid",
      "height": "0.8sw",
      "colCount": 4,
      "rowCount": 3,
      "spacing": "10dp",
      "paddingTop": "10dp",
      "paddingBottom": "10dp",
      "paddingLeft": "10dp",
      "paddingRight": "10dp",
      "width": "fill",
      "items": [
        {
          "type": "text",
          "text": "item1",
          "rowStart": 1,
          "colStart": 1,
          "rowSpan": 2,
          "colSpan": 2,
          "textSize": "18sp",
          "textColor": "#000000",
          "textStyle": "bold",
          "backColor": "#FF0000",
          "gravity": "center",
          "link": "item1"
        },
        {
          "type": "text",
          "text": "item2",
          "rowStart": 1,
          "colStart": 3,
          "rowSpan": 1,
          "colSpan": 2,
          "textSize": "18sp",
          "textColor": "#000000",
          "textStyle": "bold",
          "backColor": "#FF0000",
          "gravity": "center",
          "link": "item2"
        },
        {
          "type": "text",
          "text": "item3",
          "rowStart": 2,
          "colStart": 3,
          "rowSpan": 1,
          "colSpan": 1,
          "textSize": "18sp",
          "textColor": "#000000",
          "textStyle": "bold",
          "backColor": "#FF0000",
          "gravity": "center",
          "link": "item3"
        },
        {
          "type": "text",
          "text": "item4",
          "rowStart": 2,
          "colStart": 4,
          "rowSpan": 1,
          "colSpan": 2,
          "textSize": "18sp",
          "textColor": "#000000",
          "textStyle": "bold",
          "backColor": "#FF0000",
          "gravity": "center",
          "link": "item4"
        },
        {
          "type": "text",
          "text": "item5",
          "rowStart": 3,
          "colStart": 1,
          "rowSpan": 1,
          "colSpan": 1,
          "textSize": "18sp",
          "textColor": "#000000",
          "textStyle": "bold",
          "backColor": "#FF0000",
          "gravity": "center",
          "link": "item5"
        },
        {
          "type": "text",
          "text": "item5",
          "rowStart": 3,
          "colStart": 2,
          "rowSpan": 1,
          "colSpan": 1,
          "textSize": "18sp",
          "textColor": "#000000",
          "textStyle": "bold",
          "backColor": "#FF0000",
          "gravity": "center",
          "link": "item5"
        },
        {
          "type": "text",
          "text": "item5",
          "rowStart": 3,
          "colStart": 3,
          "rowSpan": 1,
          "colSpan": 1,
          "textSize": "18sp",
          "textColor": "#000000",
          "textStyle": "bold",
          "backColor": "#FF0000",
          "gravity": "center",
          "link": "item5"
        },
        {
          "type": "text",
          "text": "item5",
          "rowStart": 3,
          "colStart": 4,
          "rowSpan": 1,
          "colSpan": 1,
          "textSize": "18sp",
          "textColor": "#000000",
          "textStyle": "bold",
          "backColor": "#FF0000",
          "gravity": "center",
          "link": "item5"
        }
      ]
    }
  ]
}
```

效果06：

#### gallery

gallery里面的子控件是可以横向滑动的，对应Android里面横向滚动的RecyclerView。

| 属性名   | 作用                  | 描述   |
| ----- | ------------------- | ---- |
| items | List<Block>,用来存放子模块 |      |

示例07：

```
{
  ...
  "modules": [
    {
      "type": "gallery",
      "height": "0.3sw",
      "width": "fill",
      "items": [
        {
          "type": "text",
          "text": "item1",
          "height": "wrap",
          "width": "0.6sw",
          "textSize": "18sp",
          "paddingTop": "10dp",
          "marginLeft": "10dp",
          "paddingBottom": "10dp",
          "paddingLeft": "0.1sw",
          "paddingRight": "0.1sw",
          "textColor": "#000000",
          "textStyle": "bold",
          "backColor": "#FF0000",
          "gravity": "center",
          "link": "item1"
        },
        ...
      ]
    }
  ]
}
```

效果07：

#### verticalBanner

左右滚动的Banner

| 属性名        | 作用                  | 描述           |
| ---------- | ------------------- | ------------ |
| duration   | 滚动一页的时间             | 单位毫秒         |
| delay      | 一页停留的时间             | 单位毫秒         |
| manual     | 是否可以手动滚动            | 默认：true      |
| reverse    | 滚动方向                | 默认：false,右到左 |
| autoScroll | 是否自动滚动              | 默认：true      |
| items      | List<Block>,用来存放子模块 |              |

示例08：

```
{
  "layout": "grid",
  "cellCount": 2,
  "backColor": "#000000",
  "debug": true,
  "version": 1.0,
  "modules": [
    {
      "type": "horizontalBanner",
      "height": "0.4sw",
      "autoScroll": true,
      "manual": true,
      "duration": 1000,
      "delay": 1000,
      "items": [
        {
          "type": "text",
          "gravity": "center",
          "textStyle": "bold",
          "textSize": "20sp",
          "backColor": "#FFFFFF",
          "text": "demo演示",
          "link": "window://demo?demoConfig=demo%2fstart.json"
        },
        {
          "type": "text",
          "gravity": "center",
          "textStyle": "bold",
          "textSize": "20sp",
          "backColor": "#FFFFFF",
          "text": "demo演示",
          "link": "window://demo?demoConfig=demo%2fstart.json"
        }
      ]
    },
    {
      "type": "horizontalBanner",
      "backImage": "assets/taobao01/images/taobao (13).gif",
      "height": "0.212sw",
      "autoScroll": true,
      "manual": true,
      "reverse": true,
      "duration": 1000,
      "delay": 1000,
      "items": [
        {
          "type": "text",
          "gravity": "center",
          "textStyle": "bold",
          "textSize": "20sp",
          "backColor": "#99000000",
          "textColor": "#FFFFFF",
          "text": "我的github地址",
          "link": "http://github.com/zjsx"
        },
        {
          "type": "text",
          "gravity": "center",
          "textStyle": "bold",
          "textSize": "20sp",
          "backColor": "#99000000",
          "textColor": "#FFFFFF",
          "text": "我的github地址",
          "link": "http://github.com/zjsx"
        }
      ]
    }
  ]
}
```

效果08：

#### horizontalBanner

上下滚动的Banner，除reverse外，其它属性和verticalBanner一致。

| 属性名     | 作用      | 描述       |
| ------- | ------- | -------- |
| reverse | 是否反方向滚动 | 默认下到上的方向 |

示例09：

```
{
  "layout": "grid",
  "cellCount": 2,
  "backColor": "#000000",
  "debug": true,
  "spacing":"10dp",
  "version": 1.0,
  "modules": [
    {
      "type": "verticalBanner",
      "height": "0.2sw",
      "autoScroll": true,
      "manual": true,
      "duration": 1000,
      "delay": 1000,
      "items": [
        {
          "type": "text",
          "gravity": "center",
          "textStyle": "bold",
          "textSize": "20sp",
          "backColor": "#FFFFFF",
          "text": "demo1",
          "link": "demo1"
        },
        {
          "type": "text",
          "gravity": "center",
          "textStyle": "bold",
          "textSize": "20sp",
          "backColor": "#FFFFFF",
          "text": "demo2",
          "link": "demo2"
        }
      ]
    },
    {
      "type": "verticalBanner",
      "height": "0.2sw",
      "marginTop":"10dp",
      "autoScroll": true,
      "manual": true,
      "duration": 1000,
      "reverse": true,
      "delay": 1000,
      "items": [
        {
          "type": "text",
          "gravity": "center",
          "textStyle": "bold",
          "textSize": "20sp",
          "backColor": "#FFFFFF",
          "text": "demo3",
          "link": "demo3"
        },
        {
          "type": "text",
          "gravity": "center",
          "textStyle": "bold",
          "textSize": "20sp",
          "backColor": "#FFFFFF",
          "text": "demo4",
          "link": "demo4"
        }
      ]
    }
  ]
}
```

效果09：
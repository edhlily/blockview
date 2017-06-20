package com.zjsx.blocklayout.config;

import com.zjsx.blocklayout.module.Block;
import com.zjsx.blocklayout.tools.BlockUtil;

import java.io.Serializable;
import java.util.ArrayList;

public interface BlockConfig {

    /**
     * 布局的三种模式
     * <p>
     * LAYOUT_TYPE_LINEAR:对应LinearLayoutManager纵向线性布局
     * LAYOUT_TYPE_GRID:对应GridLayoutManager纵向格子布局
     * LAYOUT_TYPE_STAGGER:对应的StaggeredGridLayoutManager纵向瀑布流布局
     */
    int LAYOUT_TYPE_LINEAR = 0;
    int LAYOUT_TYPE_GRID = 1;
    int LAYOUT_TYPE_STAGGER = 2;

    /**
     * 外层item之间的间隔模式
     * 默认：SPACING_MODE_MIDDLE:只有item之间有间隔
     * |item-item|
     * |    |
     * |item-item|
     * SPACING_MODE_ARROUND:item和四周都有间隔
     * |-item-item-|
     * |    |
     * |-item-item-|
     */
    int SPACING_MODE_MIDDLE = 0;
    int SPACING_MODE_AROUND = 1;

    /**
     * 指明那些item可以有spacing
     * 默认：SPACING_ITEM_DECENTRALIZED 只有span没有占满横向向格子的item才能有spacing
     * |   item  |
     * |item-item|
     * |
     * |   item  |
     * SPACING_ITEM_ALL 所有的item都会有格子
     * |  item   |
     * |
     * |item-item|
     * |
     * |   item  |
     */
    int SPACING_ITEM_DECENTRALIZED = 0;
    int SPACING_ITEM_ALL = 1;

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

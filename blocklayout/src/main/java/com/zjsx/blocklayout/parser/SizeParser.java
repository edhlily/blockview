package com.zjsx.blocklayout.parser;

/**
 * <p>
 * Size解析器
 * <p>
 * 比如将1sw解析为屏幕的宽度，将1sh解析为屏幕的高度
 */

public interface SizeParser {

    /**
     * 当前parser能解析的后缀类型
     *
     * @param suffix
     * @return
     */
    boolean accept(String suffix);

    /**
     * 获取前缀代表多少px
     *
     * @param size 数字前缀 如1px，和1sw中的1
     * @return
     */
    float getSize(float size);

}

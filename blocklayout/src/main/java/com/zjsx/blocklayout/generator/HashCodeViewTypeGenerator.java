package com.zjsx.blocklayout.generator;

/**
 * Created by eddie on 2017-08-06.
 * 用hash值的方式获取Block的唯一ViewType
 */

public class HashCodeViewTypeGenerator implements ViewTypeGenerator {
    @Override
    public int generate(String viewTypeName) {

        if (viewTypeName == null) {
            throw new NullPointerException("viewTypeName can not be NULL!");
        }

        return viewTypeName.hashCode();
    }
}

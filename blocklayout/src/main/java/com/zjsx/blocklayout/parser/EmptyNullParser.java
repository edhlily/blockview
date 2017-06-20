package com.zjsx.blocklayout.parser;

import android.view.ViewGroup;

import com.zjsx.blocklayout.tools.BlockUtil;

public class EmptyNullParser implements SizeParser {
    @Override
    public boolean accept(String suffix) {
        return BlockUtil.isEmpty(suffix);
    }

    @Override
    public float getSize(float size) {
        return size == -1 ? ViewGroup.LayoutParams.WRAP_CONTENT : size;
    }
}

package com.zjsx.blocklayout.parser;

import android.content.Context;

import com.zjsx.blocklayout.tools.BlockUtil;

public class DPParser implements SizeParser {

    Context mContext;

    public DPParser(Context context) {
        mContext = context;
    }

    @Override
    public boolean accept(String suffix) {
        return "dp".equalsIgnoreCase(suffix);
    }

    @Override
    public float getSize(float size) {
        return BlockUtil.dip2px(mContext, size);
    }
}

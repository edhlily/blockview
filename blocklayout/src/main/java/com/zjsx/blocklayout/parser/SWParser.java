package com.zjsx.blocklayout.parser;

import com.zjsx.blocklayout.tools.BlockUtil;

public class SWParser implements SizeParser {
    @Override
    public boolean accept(String suffix) {
        return "sw".equalsIgnoreCase(suffix);
    }

    @Override
    public float getSize(float size) {
        return BlockUtil.SCREEN_WIDTH_PX * size;
    }
}

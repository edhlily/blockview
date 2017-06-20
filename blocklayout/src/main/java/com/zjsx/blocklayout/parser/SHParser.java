package com.zjsx.blocklayout.parser;

import com.zjsx.blocklayout.tools.BlockUtil;

public class SHParser implements SizeParser {
    @Override
    public boolean accept(String suffix) {
        return "sh".equalsIgnoreCase(suffix);
    }

    @Override
    public float getSize(float size) {
        return BlockUtil.SCREEN_HEIGHT_PX * size;
    }
}

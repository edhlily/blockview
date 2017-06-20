package com.zjsx.blocklayout.parser;

public class BWParser implements SizeParser {

    int baseWidth;

    public BWParser(int baseWidth) {
        this.baseWidth = baseWidth;
    }

    @Override
    public boolean accept(String suffix) {
        return "bw".equalsIgnoreCase(suffix);
    }

    @Override
    public float getSize(float size) {
        return baseWidth * size;
    }
}

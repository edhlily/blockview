package com.zjsx.blocklayout.parser;

public class BHParser implements SizeParser {

    int baseHeight;

    public BHParser(int baseHeight) {
        this.baseHeight = baseHeight;
    }

    @Override
    public boolean accept(String suffix) {
        return "bw".equalsIgnoreCase(suffix);
    }

    @Override
    public float getSize(float size) {
        return baseHeight * size;
    }
}

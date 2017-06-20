package com.zjsx.blocklayout.parser;


public class PXParser implements SizeParser {
    @Override
    public boolean accept(String suffix) {
        return "px".equalsIgnoreCase(suffix);
    }

    @Override
    public float getSize(float size) {
        return size;
    }
}

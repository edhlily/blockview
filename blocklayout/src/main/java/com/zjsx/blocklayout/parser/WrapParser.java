package com.zjsx.blocklayout.parser;

import android.view.ViewGroup;

public class WrapParser implements SizeParser {

    @Override
    public boolean accept(String suffix) {
        return "wrap".equalsIgnoreCase(suffix);
    }

    @Override
    public float getSize(float size) {
        return ViewGroup.LayoutParams.WRAP_CONTENT;
    }
}

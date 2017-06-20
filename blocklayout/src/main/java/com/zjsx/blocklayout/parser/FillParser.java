package com.zjsx.blocklayout.parser;

import android.view.ViewGroup;

public class FillParser implements SizeParser {
    @Override
    public boolean accept(String suffix) {
        return "fill".equalsIgnoreCase(suffix);
    }

    @Override
    public float getSize(float size) {
        return ViewGroup.LayoutParams.MATCH_PARENT;
    }
}

package com.zjsx.blocklayout.module;

import android.view.ViewGroup;

import com.zjsx.blocklayout.config.BlockContext;
import com.zjsx.blocklayout.holder.ImageHolder;

public class ImageItem extends Block<ImageItem> {
    private String src;
    private String scaleType;

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getScaleType() {
        return scaleType;
    }

    public void setScaleType(String scaleType) {
        this.scaleType = scaleType;
    }

    @Override
    public ImageHolder newHolder(BlockContext config, ViewGroup parent) {
        return new ImageHolder(config, parent);
    }
}

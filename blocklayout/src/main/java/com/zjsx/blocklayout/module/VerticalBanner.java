package com.zjsx.blocklayout.module;

import android.view.ViewGroup;

import com.zjsx.blocklayout.config.BlockContext;
import com.zjsx.blocklayout.holder.VerticalBannerHolder;

public class VerticalBanner extends Banner<VerticalBanner> {
    @Override
    protected VerticalBannerHolder newHolder(BlockContext config, ViewGroup parent) {
        return new VerticalBannerHolder(config, parent);
    }
}

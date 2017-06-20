package com.zjsx.blocklayout.module;

import android.view.ViewGroup;

import com.zjsx.blocklayout.config.BlockManager;
import com.zjsx.blocklayout.holder.VerticalBannerHolder;

public class VerticalBanner extends Banner<VerticalBanner> {
    @Override
    protected VerticalBannerHolder newHolder(BlockManager config, ViewGroup parent) {
        return new VerticalBannerHolder(config, parent);
    }
}

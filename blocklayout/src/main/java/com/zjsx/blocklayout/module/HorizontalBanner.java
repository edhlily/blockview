package com.zjsx.blocklayout.module;

import android.view.ViewGroup;

import com.zjsx.blocklayout.config.BlockManager;
import com.zjsx.blocklayout.holder.HorizontalBannerHolder;

public class HorizontalBanner extends Banner<HorizontalBanner> {

    @Override
    public HorizontalBannerHolder newHolder(BlockManager config, ViewGroup parent) {
        return new HorizontalBannerHolder(config,parent);
    }
}

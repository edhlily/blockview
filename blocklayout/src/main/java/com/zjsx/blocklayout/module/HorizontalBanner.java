package com.zjsx.blocklayout.module;

import android.view.ViewGroup;

import com.zjsx.blocklayout.config.BlockContext;
import com.zjsx.blocklayout.holder.HorizontalBannerHolder;


public class HorizontalBanner extends Banner<HorizontalBanner> {

    @Override
    public HorizontalBannerHolder newHolder(BlockContext config, ViewGroup parent) {
        return new HorizontalBannerHolder(config,parent);
    }
}

package com.zjsx.blocklayout.holder;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.zjsx.blocklayout.config.BlockManager;
import com.zjsx.blocklayout.module.Block;
import com.zjsx.blocklayout.module.VerticalBanner;
import com.zjsx.blocklayout.widget.banner.VerticalViewPager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class VerticalBannerHolder extends BlockHolder<VerticalBanner> {
    VerticalViewPager bannerView;
    BannerAdapter adapter;
    final List<Block> mDatas = new ArrayList<>();

    public VerticalBannerHolder(BlockManager config, ViewGroup parent) {
        super(config, new VerticalViewPager(parent.getContext()), config.getViewType(VerticalBanner.class));
        bannerView = (VerticalViewPager) itemView;
        adapter = new BannerAdapter();
        bannerView.setAdapter(adapter);
    }

    @Override
    public void bindData(final VerticalBanner banner) {
        mDatas.clear();
        mDatas.addAll(banner.getItems());
        adapter.notifyDataSetChanged();
        bannerView.setManual(banner.isManual());
        bannerView.setScrollDelay(banner.getRealDelay());
        bannerView.setScrollDuration(banner.getRealDuration());
        if (banner.isAutoScroll()) {
            bannerView.autoScroll();
        } else {
            bannerView.stopScroll();
        }
    }

    public class BannerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((BlockHolder) object).itemView;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Block item = mDatas.get(position % mDatas.size());
            BlockHolder itemHolder = getRecylerViewHolder(item.getClass());
            if (itemHolder == null) {
                itemHolder = item.getHolder(config, bannerView);
                itemHolder.setItemLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            }
            itemHolder.bind(item);
            container.addView(itemHolder.itemView);
            return itemHolder;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            BlockHolder itemHolder = (BlockHolder) object;
            container.removeView(itemHolder.itemView);
        }
    }
}
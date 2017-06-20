package com.zjsx.blocklayout.holder;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.example.blocklayout.R;
import com.zjsx.blocklayout.config.BlockManager;
import com.zjsx.blocklayout.module.Block;
import com.zjsx.blocklayout.module.HorizontalBanner;
import com.zjsx.blocklayout.widget.banner.HorizontalViewPager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class HorizontalBannerHolder extends BlockHolder<HorizontalBanner> {
    HorizontalViewPager bannerView;
    BannerAdapter adapter;
    final List<Block> mDatas = new ArrayList<>();

    public HorizontalBannerHolder(BlockManager config, ViewGroup parent) {
        super(config, new HorizontalViewPager(parent.getContext()), config.getViewType(HorizontalBanner.class));
        bannerView = (HorizontalViewPager) itemView;
        adapter = new BannerAdapter();
        bannerView.setAdapter(adapter);
    }

    @Override
    public void bindData(final HorizontalBanner banner) {
        mDatas.clear();
        mDatas.addAll(banner.getItems());
        bannerView.setAdapter(adapter);
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
        public int getItemPosition(Object object) {
            return super.getItemPosition(object);
        }

        @Override
        public int getCount() {
            return mDatas.size() == 0 ? 0 : Integer.MAX_VALUE;
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
                itemHolder.setItemLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
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
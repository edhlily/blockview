package com.zjsx.blocklayout.holder;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.zjsx.blocklayout.config.BlockConfig;
import com.zjsx.blocklayout.config.BlockContext;
import com.zjsx.blocklayout.config.ChildManualRecycle;
import com.zjsx.blocklayout.module.Block;
import com.zjsx.blocklayout.module.HorizontalBanner;
import com.zjsx.blocklayout.widget.banner.HorizontalViewPager;

import java.util.ArrayList;
import java.util.List;


public class HorizontalBannerHolder extends BlockHolder<HorizontalBanner> implements ChildManualRecycle {
    HorizontalViewPager bannerView;
    BannerAdapter adapter;
    final List<Block> mDatas = new ArrayList<>();
    int start;

    public HorizontalBannerHolder(BlockContext blockContext, ViewGroup parent) {
        super(blockContext, new HorizontalViewPager(parent.getContext()), BlockConfig.getInstance().getViewType(HorizontalBanner.class));
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

        if (banner.isReverse()) {
            bannerView.setCurrentItem(adapter.getCount() - 1);
            bannerView.setDirection(-1);
            start = adapter.getCount() - 1;
        } else {
            bannerView.setCurrentItem(0);
            bannerView.setDirection(1);
            start = 0;
        }

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
            Block item = mDatas.get((start + bannerView.getDirection() * position) % mDatas.size());
            BlockHolder itemHolder = getRecylerViewHolder(item.getClass());

            if (itemHolder == null) {
                itemHolder = item.getHolder(blockContext, bannerView);
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
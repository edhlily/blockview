package com.zjsx.blocklayout.widget;

import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.zjsx.blocklayout.config.BlockConfig;
import com.zjsx.blocklayout.config.BlockContext;
import com.zjsx.blocklayout.config.BlockDataConfig;

import static com.zjsx.blocklayout.config.BlockDataConfig.LAYOUT_TYPE_STAGGER;
import static com.zjsx.blocklayout.config.BlockDataConfig.SPACING_ITEM_DECENTRALIZED;
import static com.zjsx.blocklayout.config.BlockDataConfig.SPACING_MODE_AROUND;

class BlockItemDecoration extends RecyclerView.ItemDecoration {
    BlockContext blockContext;
    BlockDataConfig config;
    int spanCount;
    int spacingMode;
    int spacingSize;

    public BlockItemDecoration(BlockContext blockContext, BlockDataConfig config) {
        this.blockContext = blockContext;
        this.config = config;
        spanCount = config.getCellCount();
        spanCount = spanCount > 0 ? spanCount : 1;
        spacingMode = BlockConfig.getInstance().getSpacingMode(config.getSpacingMode());
        spacingSize = BlockConfig.getInstance().getSize(config.getSpacing());
        if (blockContext.getCurentLayoutType() == LAYOUT_TYPE_STAGGER) {
            //stagger模式下，如果下面的散列item没有设置高度，并且spacing过小的话会导致下面的散列item变成一列
            //所以这里设置一个最小值
            if (BlockConfig.getInstance().getSize(config.getSpacing()) < 0) {
                spacingSize = BlockConfig.getInstance().getSize("5dp");
            }
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, final View view, final RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (spacingSize <= 0) return;

        int position = parent.getChildAdapterPosition(view);

        if (BlockConfig.getInstance().getSpacingItem(config.getSpacingItem()) == SPACING_ITEM_DECENTRALIZED) {

            //占满所有横向cell的item不通过这里设置，间隔，通过手动设置margin的方式
            if (parent.getLayoutManager() instanceof GridLayoutManager) {
                if (((GridLayoutManager) parent.getLayoutManager()).getSpanSizeLookup().getSpanSize(position) == spanCount) {
                    return;
                }
            } else if (parent.getLayoutManager() instanceof StaggeredGridLayoutManager) {
                if (((StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams()).isFullSpan()) {
                    return;
                }
            }
        }

        if (spanCount == 1) {
            outRect.bottom = spacingSize;
            if (spacingMode == SPACING_MODE_AROUND) {
                outRect.left = spacingSize;
                outRect.right = spacingSize;
            }
        } else {
            int spanIndex = 0;
            if (parent.getLayoutManager() instanceof GridLayoutManager) {
                spanIndex = ((GridLayoutManager.LayoutParams) view.getLayoutParams()).getSpanIndex();
            } else if (parent.getLayoutManager() instanceof StaggeredGridLayoutManager) {
                spanIndex = ((StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams()).getSpanIndex();
            }
            outRect.bottom = spacingSize;
            if (spacingMode == SPACING_MODE_AROUND) {
                if (spanIndex == 0) {
                    outRect.left = spacingSize;
                }
                outRect.right = spacingSize;

            } else {
                if (spanIndex == 0) {
                    outRect.left = 0;
                } else {
                    outRect.left = spacingSize;
                }
            }
        }
    }

}
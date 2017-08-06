package com.zjsx.blocklayout.holder;

import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.zjsx.blocklayout.config.BlockConfig;
import com.zjsx.blocklayout.config.BlockContext;
import com.zjsx.blocklayout.module.Block;
import com.zjsx.blocklayout.module.Frame;

public class FrameHolder extends BlockHolder<Frame> {
    public FrameLayout mFrameLayout;

    public FrameHolder(BlockContext blockContext, ViewGroup parent) {
        super(blockContext, new FrameLayout(parent.getContext()), BlockConfig.getInstance().getViewType(Frame.class));
        mFrameLayout = (FrameLayout) itemView;
    }

    @Override
    public void bindData(final Frame frame) {
        mFrameLayout.removeAllViews();
        com.zjsx.blocklayout.holder.BlockHolder blockHolder;
        FrameLayout.LayoutParams layoutParams;
        int width = ViewGroup.LayoutParams.WRAP_CONTENT;
        int height = ViewGroup.LayoutParams.WRAP_CONTENT;
        for (Block item : frame.getItems()) {
            if (item != null) {
                blockHolder = getHolder(item, mFrameLayout);
                width = BlockConfig.getInstance().getSize(item.getWidth());
                height = BlockConfig.getInstance().getSize(item.getHeight());
                blockHolder.setItemLayoutParams(width, height);
                layoutParams = new FrameLayout.LayoutParams(width, height);
                layoutParams.gravity = BlockConfig.getInstance().getViewGravity(item.getLayoutGravity());
                mFrameLayout.addView(blockHolder.itemView, layoutParams);
                blockHolder.bind(item);
            }
        }
    }
}

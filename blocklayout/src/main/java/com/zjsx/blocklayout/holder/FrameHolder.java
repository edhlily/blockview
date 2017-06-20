package com.zjsx.blocklayout.holder;

import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.zjsx.blocklayout.config.BlockManager;
import com.zjsx.blocklayout.module.Block;
import com.zjsx.blocklayout.module.Frame;

public class FrameHolder extends BlockHolder<Frame> {
    public FrameLayout mFrameLayout;

    public FrameHolder(BlockManager config, ViewGroup parent) {
        super(config, new FrameLayout(parent.getContext()), config.getViewType(Frame.class));
        mFrameLayout = (FrameLayout) itemView;
    }

    @Override
    public void bindData(final Frame frame) {
        mFrameLayout.removeAllViews();
        BlockHolder blockHolder;
        FrameLayout.LayoutParams layoutParams;
        int width = ViewGroup.LayoutParams.WRAP_CONTENT;
        int height = ViewGroup.LayoutParams.WRAP_CONTENT;
        for (Block item : frame.getItems()) {
            if (item != null) {
                blockHolder = getHolder(item, mFrameLayout);
                width = config.getSize(item.getWidth());
                height = config.getSize(item.getHeight());
                blockHolder.setItemLayoutParams(width, height);
                layoutParams = new FrameLayout.LayoutParams(width, height);
                layoutParams.gravity = config.getViewGravity(item.getLayoutGravity());
                mFrameLayout.addView(blockHolder.itemView, layoutParams);
                blockHolder.bind(item);
            }
        }
    }
}

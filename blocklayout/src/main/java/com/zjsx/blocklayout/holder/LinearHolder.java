package com.zjsx.blocklayout.holder;

import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.zjsx.blocklayout.config.BlockManager;
import com.zjsx.blocklayout.module.Block;
import com.zjsx.blocklayout.module.Linear;
import com.zjsx.blocklayout.widget.roundview.RoundLinearLayout;

public class LinearHolder extends BlockHolder<Linear> {
    public RoundLinearLayout mLinearLayout;

    public LinearHolder(BlockManager config, ViewGroup parent) {
        super(config, new RoundLinearLayout(parent.getContext()), config.getViewType(Linear.class));
        mLinearLayout = (RoundLinearLayout) itemView;
    }

    @Override
    public void bindData(final Linear linear) {
        mLinearLayout.removeAllViews();
        BlockHolder blockHolder;
        LinearLayout.LayoutParams layoutParams;
        int width = ViewGroup.LayoutParams.WRAP_CONTENT;
        int height = ViewGroup.LayoutParams.WRAP_CONTENT;
        if ("vertical".equals(linear.getOrientation())) {
            mLinearLayout.setOrientation(LinearLayout.VERTICAL);
            for (Block item : linear.getItems()) {
                blockHolder = getHolder(item, mLinearLayout);
                width = config.getSize(item.getWidth());
                height = config.getSize(item.getHeight());
                layoutParams = new LinearLayout.LayoutParams(width, height);
                layoutParams.weight = item.getWeight();
                layoutParams.gravity = config.getViewGravity(item.getLayoutGravity());
                mLinearLayout.addView(blockHolder.itemView, layoutParams);
                blockHolder.bind(item);
            }
        } else {
            mLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
            for (Block item : linear.getItems()) {
                blockHolder = (BlockHolder) getRecylerViewHolder(item.getClass());
                if (blockHolder == null) {
                    blockHolder = item.getHolder(config, mLinearLayout);
                }
                width = config.getSize(item.getWidth());
                height = config.getSize(item.getHeight());
                layoutParams = new LinearLayout.LayoutParams(width, height);
                layoutParams.weight = item.getWeight();
                layoutParams.gravity = config.getViewGravity(item.getLayoutGravity());
                mLinearLayout.addView(blockHolder.itemView, layoutParams);
                blockHolder.bind(item);
            }
        }


    }
}

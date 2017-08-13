package com.zjsx.blocklayout.holder;

import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.zjsx.blocklayout.config.BlockConfig;
import com.zjsx.blocklayout.config.BlockContext;
import com.zjsx.blocklayout.config.ChildManualRecycle;
import com.zjsx.blocklayout.module.Block;
import com.zjsx.blocklayout.module.Linear;
import com.zjsx.blocklayout.widget.roundview.RoundLinearLayout;

public class LinearHolder extends BlockHolder<Linear> implements ChildManualRecycle {
    public RoundLinearLayout mLinearLayout;

    public LinearHolder(BlockContext blockContext, ViewGroup parent) {
        super(blockContext, new RoundLinearLayout(parent.getContext()), BlockConfig.getInstance().getViewType(Linear.class));
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
                width = BlockConfig.getInstance().getSize(item.getWidth());
                height = BlockConfig.getInstance().getSize(item.getHeight());
                layoutParams = new LinearLayout.LayoutParams(width, height);
                layoutParams.weight = item.getWeight();
                layoutParams.gravity = BlockConfig.getInstance().getViewGravity(item.getLayoutGravity());
                mLinearLayout.addView(blockHolder.itemView, layoutParams);
                blockHolder.bind(item);
            }
        } else {
            mLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
            for (Block item : linear.getItems()) {
                blockHolder = (BlockHolder) getRecylerViewHolder(item.getClass());
                if (blockHolder == null) {
                    blockHolder = item.getHolder(blockContext, mLinearLayout);
                }
                width = BlockConfig.getInstance().getSize(item.getWidth());
                height = BlockConfig.getInstance().getSize(item.getHeight());
                layoutParams = new LinearLayout.LayoutParams(width, height);
                layoutParams.weight = item.getWeight();
                layoutParams.gravity = BlockConfig.getInstance().getViewGravity(item.getLayoutGravity());
                mLinearLayout.addView(blockHolder.itemView, layoutParams);
                blockHolder.bind(item);
            }
        }


    }
}

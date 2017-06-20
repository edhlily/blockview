package com.zjsx.blocklayout.holder;

import android.view.ViewGroup;
import android.widget.TextView;

import com.zjsx.blocklayout.config.BlockManager;
import com.zjsx.blocklayout.module.TextItem;

public class TextHolder extends BlockHolder<TextItem> {
    @Override
    public void bindData(final TextItem item) {
        TextView tv = (TextView) itemView;
        tv.setText(item.getText());
        BlockManager.setTextColor(tv, item.getTextColor());
        BlockManager.setTextSize(tv, item.getTextSize());
        tv.setGravity(config.getViewGravity(item.getGravity()));
        tv.setTypeface(null, config.getTextStyle(item.getTextStyle()));
        if (item.getMaxLine() != 0) {
            tv.setMaxLines(item.getMaxLine());
        }
    }

    public TextHolder(BlockManager config, ViewGroup parent) {
        super(config, new TextView(parent.getContext()),config.getViewType(TextItem.class));
    }
}
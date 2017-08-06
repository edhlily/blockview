package com.zjsx.blocklayout.holder;

import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zjsx.blocklayout.config.BlockConfig;
import com.zjsx.blocklayout.config.BlockContext;
import com.zjsx.blocklayout.module.TextItem;

public class TextHolder extends BlockHolder<TextItem> {
    @Override
    public void bindData(final TextItem item) {
        TextView tv = (TextView) itemView;
        tv.setText(item.getText());
        BlockConfig.getInstance().setTextColor(tv, item.getTextColor());
        tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, BlockConfig.getInstance().getSize(item.getTextSize()));
        tv.setGravity(BlockConfig.getInstance().getViewGravity(item.getGravity()));
        tv.setTypeface(null, BlockConfig.getInstance().getTextStyle(item.getTextStyle()));
        if (item.getMaxLine() != 0) {
            tv.setMaxLines(item.getMaxLine());
        }
    }

    public TextHolder(BlockContext blockContext, ViewGroup parent) {
        super(blockContext, new TextView(parent.getContext()), BlockConfig.getInstance().getViewType(TextItem.class));
    }
}
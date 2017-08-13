package com.zjsx.blocklayout.holder;

import android.view.ViewGroup;

import com.zjsx.blocklayout.config.BlockConfig;
import com.zjsx.blocklayout.config.BlockContext;
import com.zjsx.blocklayout.config.ChildManualRecycle;
import com.zjsx.blocklayout.module.Block;
import com.zjsx.blocklayout.module.GridContainer;
import com.zjsx.blocklayout.parser.BWParser;
import com.zjsx.blocklayout.tools.BlockUtil;
import com.zjsx.blocklayout.widget.DolphinGridLayout;

public class GridHolder extends BlockHolder<GridContainer> implements ChildManualRecycle {
    private DolphinGridLayout gridLayout;

    public GridHolder(BlockContext blockContext, ViewGroup parent) {
        super(blockContext, new DolphinGridLayout(parent.getContext()), BlockConfig.getInstance().getViewType(GridContainer.class));
        gridLayout = (DolphinGridLayout) itemView;
    }

    @Override
    public void bindData(final GridContainer container) {
        gridLayout.removeAllViews();
        gridLayout.setColumnCount(container.getColCount());
        gridLayout.setRowCount(container.getRowCount());
        gridLayout.setItemHorizontalSpace((int) BlockConfig.getInstance().getSize(container.getSpacing()));
        gridLayout.setItemVerticalSpace((int) BlockConfig.getInstance().getSize(container.getSpacing()));
        int parentHeight = BlockConfig.getInstance().getSize(container.getHeight());
        if (parentHeight == ViewGroup.LayoutParams.WRAP_CONTENT) {
            float totalItemWidth = BlockUtil.SCREEN_WIDTH_PX
                    - (container.getColCount() - 1) * BlockConfig.getInstance().getSize(container.getSpacing())
                    - BlockConfig.getInstance().getSize(container.getPaddingLeft())
                    - BlockConfig.getInstance().getSize(container.getPaddingRight())
                    - BlockConfig.getInstance().getSize(container.getMarginLeft())
                    - BlockConfig.getInstance().getSize(container.getMarginRight());
            int baseWidth = (int) Math.ceil(((double) totalItemWidth) / container.getColCount());
            gridLayout.setRowHeight(BlockConfig.getInstance().getSize(container.getRowHeight(), new BWParser(baseWidth)));
        } else {
            gridLayout.setRowHeight(-1);
        }
        BlockHolder blockHolder;
        DolphinGridLayout.LayoutParams layoutParams;
        for (Block item : container.getItems()) {
            blockHolder = getHolder(item, gridLayout);
            blockHolder.setItemLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams = new DolphinGridLayout.LayoutParams(DolphinGridLayout.LayoutParams.MATCH_PARENT, DolphinGridLayout.LayoutParams.MATCH_PARENT);
            layoutParams.rowIndex = item.getRow();
            layoutParams.columnIndex = item.getColumn();
            layoutParams.rowSpec = item.getRowSpec();
            layoutParams.columnSpec = item.getColumnSpec();
            gridLayout.addView(blockHolder.itemView, layoutParams);
            blockHolder.bind(item);
        }
    }


}
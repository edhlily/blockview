package com.zjsx.blocklayout.holder;

import android.view.ViewGroup;

import com.zjsx.blocklayout.config.BlockConfig;
import com.zjsx.blocklayout.config.BlockContext;
import com.zjsx.blocklayout.config.ChildManualRecycle;
import com.zjsx.blocklayout.module.Block;
import com.zjsx.blocklayout.module.TableContainer;
import com.zjsx.blocklayout.parser.BWParser;
import com.zjsx.blocklayout.tools.BlockUtil;
import com.zjsx.blocklayout.widget.DolphinGridLayout;

public class TableHolder extends BlockHolder<TableContainer> implements ChildManualRecycle {
    private DolphinGridLayout gridLayout;

    public TableHolder(BlockContext blockContext, ViewGroup parent) {
        super(blockContext, new DolphinGridLayout(parent.getContext()), BlockConfig.getInstance().getViewType(TableContainer.class));
        gridLayout = (DolphinGridLayout) itemView;
    }

    @Override
    public void bindData(final TableContainer container) {
        gridLayout.removeAllViews();
        gridLayout.setColumnCount(container.getColCount());
        if (container.getItems().size() % container.getColCount() == 0) {
            gridLayout.setRowCount(container.getItems().size() / container.getColCount());
        } else {
            gridLayout.setRowCount(1 + container.getItems().size() / container.getColCount());
        }
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
        Block item;
        for (int i = 0; i < container.getItems().size(); i++) {
            item = container.getItems().get(i);
            blockHolder = getHolder(item, gridLayout);
            layoutParams = new DolphinGridLayout.LayoutParams(DolphinGridLayout.LayoutParams.MATCH_PARENT, DolphinGridLayout.LayoutParams.MATCH_PARENT);
            layoutParams.rowIndex = i / container.getColCount();
            layoutParams.columnIndex = i % container.getColCount();
            layoutParams.rowSpec = 1;
            layoutParams.columnSpec = 1;
            gridLayout.addView(blockHolder.itemView, layoutParams);
            blockHolder.bind(item);
        }
    }


}
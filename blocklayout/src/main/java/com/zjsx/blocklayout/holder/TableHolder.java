package com.zjsx.blocklayout.holder;

import android.view.ViewGroup;

import com.zjsx.blocklayout.config.BlockManager;
import com.zjsx.blocklayout.module.Block;
import com.zjsx.blocklayout.module.TableContainer;
import com.zjsx.blocklayout.parser.BWParser;
import com.zjsx.blocklayout.tools.BlockUtil;
import com.zjsx.blocklayout.widget.DolphinGridLayout;

public class TableHolder extends BlockHolder<TableContainer> {
    private DolphinGridLayout gridLayout;

    public TableHolder(BlockManager config, ViewGroup parent) {
        super(config, new DolphinGridLayout(parent.getContext()), config.getViewType(TableContainer.class));
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
        gridLayout.setItemHorizontalSpace((int) config.getSize(container.getSpacing()));
        gridLayout.setItemVerticalSpace((int) config.getSize(container.getSpacing()));
        int parentHeight = config.getSize(container.getHeight());
        if (parentHeight == ViewGroup.LayoutParams.WRAP_CONTENT) {
            float totalItemWidth = BlockUtil.SCREEN_WIDTH_PX
                    - (container.getColCount() - 1) * config.getSize(container.getSpacing())
                    - config.getSize(container.getPaddingLeft())
                    - config.getSize(container.getPaddingRight())
                    - config.getSize(container.getMarginLeft())
                    - config.getSize(container.getMarginRight());
            int baseWidth = (int) Math.ceil(((double) totalItemWidth) / container.getColCount());
            gridLayout.setRowHeight(config.getSize(container.getRowHeight(), new BWParser(baseWidth)));
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
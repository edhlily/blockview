package com.zjsx.blocklayout.module;

import android.view.ViewGroup;

import com.zjsx.blocklayout.config.BlockManager;
import com.zjsx.blocklayout.holder.BlockHolder;
import com.zjsx.blocklayout.holder.TableHolder;

import java.util.ArrayList;

public class TableContainer extends Block<TableContainer> {
    private int colCount;
    private String spacing;
    private String rowHeight;
    private ArrayList<Block> items;

    public int getColCount() {
        return colCount;
    }

    public void setColCount(int colCount) {
        this.colCount = colCount;
    }

    public String getRowHeight() {
        return rowHeight;
    }

    public void setRowHeight(String rowHeight) {
        this.rowHeight = rowHeight;
    }

    public ArrayList<Block> getItems() {
        return items;
    }

    boolean sorted;

    public void sortItems() {
        if (!sorted) {
            Block item;
            for (int i = 0; i < items.size(); i++) {
                item = items.get(i);
                item.setRowStart((i / getColCount()) + 1);
                item.setColStart((i % getColCount()) + 1);
                item.setRowSpan(1);
                item.setColSpan(1);
            }

            sorted = true;
        }
    }

    public void setItems(ArrayList<Block> items) {
        this.items = items;
    }

    public String getSpacing() {
        return spacing;
    }

    public void setSpacing(String spacing) {
        this.spacing = spacing;
    }

    @Override
    protected BlockHolder<TableContainer> newHolder(BlockManager config, ViewGroup parent) {
        return new TableHolder(config, parent);
    }
}

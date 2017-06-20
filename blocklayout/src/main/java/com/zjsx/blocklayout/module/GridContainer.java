package com.zjsx.blocklayout.module;

import android.view.ViewGroup;

import com.zjsx.blocklayout.config.BlockManager;
import com.zjsx.blocklayout.holder.BlockHolder;
import com.zjsx.blocklayout.holder.GridHolder;

import java.util.ArrayList;

public class GridContainer extends Block<GridContainer> {
    private int rowCount;
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

    public ArrayList<Block> getItems() {
        return items;
    }

    public void setItems(ArrayList<Block> items) {
        this.items = items;
    }

    public int getRowCount() {
        return rowCount;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

    public String getSpacing() {
        return spacing;
    }

    public void setSpacing(String spacing) {
        this.spacing = spacing;
    }

    public String getRowHeight() {
        return rowHeight;
    }

    public void setRowHeight(String rowHeight) {
        this.rowHeight = rowHeight;
    }

    @Override
    protected BlockHolder<GridContainer> newHolder(BlockManager config, ViewGroup parent) {
        return new GridHolder(config, parent);
    }
}

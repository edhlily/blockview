package com.zjsx.blocklayout.module;

import android.view.ViewGroup;

import com.zjsx.blocklayout.config.BlockContext;
import com.zjsx.blocklayout.holder.LinearHolder;

import java.util.ArrayList;

public class Linear extends Block<Linear> {
    private String orientation;
    private ArrayList<Block> items;

    public ArrayList<Block> getItems() {
        return items;
    }

    public void setItems(ArrayList<Block> items) {
        this.items = items;
    }

    public String getOrientation() {
        return orientation;
    }

    public void setOrientation(String orientation) {
        this.orientation = orientation;
    }

    @Override
    protected LinearHolder newHolder(BlockContext config, ViewGroup parent) {
        return new LinearHolder(config, parent);
    }
}

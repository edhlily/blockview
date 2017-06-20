package com.zjsx.blocklayout.module;

import android.view.ViewGroup;

import com.zjsx.blocklayout.config.BlockManager;
import com.zjsx.blocklayout.holder.FrameHolder;

import java.util.ArrayList;

public class Frame extends Block<Frame> {
    private ArrayList<Block> items;

    public ArrayList<Block> getItems() {
        return items;
    }

    public void setItems(ArrayList<Block> items) {
        this.items = items;
    }

    @Override
    public FrameHolder newHolder(BlockManager config, ViewGroup parent) {
        return new FrameHolder(config,parent);
    }
}

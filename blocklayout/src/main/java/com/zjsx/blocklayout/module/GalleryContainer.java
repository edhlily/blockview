package com.zjsx.blocklayout.module;

import android.view.ViewGroup;

import com.zjsx.blocklayout.config.BlockContext;
import com.zjsx.blocklayout.holder.GalleryHolder;

import java.util.ArrayList;

public class GalleryContainer extends Block<GalleryContainer> {
    private ArrayList<Block> items = new ArrayList<>();

    public ArrayList<Block> getItems() {
        return items;
    }

    public void setItems(ArrayList<Block> items) {
        this.items = items;
    }

    @Override
    protected GalleryHolder newHolder(BlockContext config, ViewGroup parent) {
        return new GalleryHolder(config, parent);
    }
}

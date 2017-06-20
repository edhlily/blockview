package com.zsjx.store.homepage.module;


import android.view.ViewGroup;

import com.zjsx.blocklayout.module.Block;
import com.zsjx.store.homepage.R;
import com.zsjx.store.homepage.app.StoreApp;
import com.zjsx.blocklayout.config.BlockManager;
import com.zsjx.store.homepage.holder.NextPageHolder;

public class NextPageItem extends Block<NextPageItem> {
    public static final int STATUS_LOADING = 1;
    public static final int STATUS_ERROR = 2;
    public static final int STATUS_NOMORE = 3;
    public int status = STATUS_LOADING;

    public NextPageItem(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getText() {
        switch (status) {
            case STATUS_ERROR:
                return StoreApp.get().getString(R.string.index_nextpage_error);
            case STATUS_NOMORE:
                return StoreApp.get().getString(R.string.index_nextpage_nomore);
            default:
                return StoreApp.get().getString(R.string.index_nextpage_loading);
        }
    }

    @Override
    public NextPageHolder newHolder(BlockManager config, ViewGroup parent) {
        return new NextPageHolder(config, parent);
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }
}

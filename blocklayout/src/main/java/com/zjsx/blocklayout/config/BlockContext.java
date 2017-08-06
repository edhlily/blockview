package com.zjsx.blocklayout.config;

import android.content.Context;
import android.view.ViewGroup;

import com.zjsx.blocklayout.holder.BlockHolder;
import com.zjsx.blocklayout.widget.BlockView;

public class BlockContext {

    /**
     * 当前的布局类型
     */
    private int curentLayoutType = 0;


    protected Context mContext;


    public BlockContext(Context context) {
        this.mContext = context;

    }


    public void setCurentLayoutType(int curentLayoutType) {
        this.curentLayoutType = curentLayoutType;
    }

    public int getCurentLayoutType() {
        return curentLayoutType;
    }


    /**
     * 通过ViewType获取Block对应的Holder
     *
     * @param parent
     * @param viewType
     * @return
     */
    public BlockHolder getHolder(ViewGroup parent, int viewType) {
        return BlockConfig.getInstance().getHolder(parent, viewType, this);
    }

    private BlockView.OnBlockClickListener onBlockClickListener;

    public BlockView.OnBlockClickListener getOnBlockClickListener() {
        return onBlockClickListener;
    }

    public void setOnBlockClickListener(BlockView.OnBlockClickListener onBlockClickListener) {
        this.onBlockClickListener = onBlockClickListener;
    }


    private BlockView.BlockClickInterceptor blockClickInterceptor;

    public BlockView.BlockClickInterceptor getBlockClickInterceptor() {
        return blockClickInterceptor;
    }

    public void setBlockClickInterceptor(BlockView.BlockClickInterceptor blockClickInterceptor) {
        this.blockClickInterceptor = blockClickInterceptor;
    }
}

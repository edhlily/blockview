package com.zjsx.blocklayout.holder;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.example.blocklayout.R;
import com.zjsx.blocklayout.config.BlockManager;
import com.zjsx.blocklayout.module.Block;
import com.zjsx.blocklayout.tools.BlockUtil;
import com.zjsx.blocklayout.widget.roundview.RoundLinearLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BlockHolder<T extends Block> extends RecyclerView.ViewHolder {
    private String mImagePath;
    protected BlockManager config;
    private int blockType;

    private Map<Integer, List<BlockHolder>> recycledViewPool = new HashMap<>();

    public void recycleViewHolder(BlockHolder blockHolder) {
        if (!recycledViewPool.containsKey(blockHolder.getBlockType())) {
            recycledViewPool.put(blockHolder.getBlockType(), new ArrayList<BlockHolder>());
        }
        recycledViewPool.get(blockHolder.getBlockType()).add(blockHolder);
    }

    public BlockHolder getRecylerViewHolder(Class<?> clazz) {
        return getRecylerViewHolder(config.getViewType(clazz));
    }

    public BlockHolder getRecylerViewHolder(int viewType) {
        Log.d(getClass().getSimpleName(), "getRecylerViewHolder() called with: viewType = [" + viewType + "]");
        if (recycledViewPool.get(viewType) != null && recycledViewPool.get(viewType).size() > 0) {
            System.out.println("复用View");
            return recycledViewPool.get(viewType).remove(0);
        }
        return null;
    }

    public BlockHolder<T> getHolder(Block<?> item, ViewGroup parent) {
        BlockHolder blockHolder = getRecylerViewHolder(item.getClass());
        if (blockHolder == null) {
            blockHolder = item.getHolder(config, parent);
        }
        return blockHolder;
    }

    public int getBlockType() {
        return blockType;
    }

    public void setBlockType(int blockType) {
        this.blockType = blockType;
    }

    public BlockHolder(final BlockManager config, final View itemView, int blockType) {
        super(itemView);
        this.itemView.setTag(R.id.rootHolder, this);
        this.config = config;
        this.blockType = blockType;

        if (itemView instanceof ViewGroup) {
            ((ViewGroup) itemView).setOnHierarchyChangeListener(new ViewGroup.OnHierarchyChangeListener() {
                @Override
                public void onChildViewAdded(View parent, View child) {

                }

                @Override
                public void onChildViewRemoved(View parent, View child) {
                    BlockHolder blockHolder = (BlockHolder) child.getTag(R.id.rootHolder);
                    blockHolder.setBlock(null);
                    System.out.println(block.getClass().getSimpleName() + ":view 回收:" + blockHolder.getClass().getSimpleName() + ":" + blockHolder.getBlockType());
                    recycleViewHolder(blockHolder);
                }
            });
        }
    }

    public void setItemLayoutParams(int width, int height) {
        itemView.setLayoutParams(new ViewGroup.MarginLayoutParams(width, height));
    }

    public void setItemLayoutParams(ViewGroup.LayoutParams layoutParams) {
        itemView.setLayoutParams(layoutParams);
    }

    T block;

    public T getBlock() {
        return block;
    }

    public void setBlock(T block) {
        this.block = block;
    }

    public void bind(final T block) {
        //判断当前view绑定的对象是否和将要绑定的对象一样
        if (!block.equals(this.block)) {
            this.block = block;
            setPadding(block);
            setMargin(block);
            setRound(block);
            setBackgroud(block);

            if (block.isClickable() && config.isClickable(block)) {
                itemView.setClickable(true);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        config.onItemClick(block);
                    }
                });
            } else {
                itemView.setOnClickListener(null);
                itemView.setClickable(false);
            }
        }

        bindData(block);
    }

    public abstract void bindData(final T block);

    public void setPadding(Block block) {
        itemView.setPadding(
                config.getSize(block.getPaddingLeft()),
                config.getSize(block.getPaddingTop()),
                config.getSize(block.getPaddingRight()),
                config.getSize(block.getPaddingBottom()));
    }

    public void setMargin(Block block) {
        if (itemView.getLayoutParams() != null && itemView.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ((ViewGroup.MarginLayoutParams) itemView.getLayoutParams()).setMargins(
                    config.getSize(block.getMarginLeft()),
                    config.getSize(block.getMarginTop()),
                    config.getSize(block.getMarginRight()),
                    config.getSize(block.getMarginBottom()));
        }
    }

    public void setRound(Block block) {
        if (itemView instanceof RoundLinearLayout) {
            ((RoundLinearLayout) itemView).getDelegate().setBackgroundColor(Color.WHITE);
            ((RoundLinearLayout) itemView).getDelegate().setCornerRadius_TL(config.getSize(block.getRoundTopLeft()));
            ((RoundLinearLayout) itemView).getDelegate().setCornerRadius_TR(config.getSize(block.getRoundTopRight()));
            ((RoundLinearLayout) itemView).getDelegate().setCornerRadius_BL(config.getSize(block.getRoundBottomLeft()));
            ((RoundLinearLayout) itemView).getDelegate().setCornerRadius_BR(config.getSize(block.getRoundBottomRight()));
        }
    }

    private void setBackgroud(Block block) {
        if (!BlockUtil.isEmpty(block.getBackImage())) {
            setBackgroundImage(block.getBackImage());
        } else if (!BlockUtil.isEmpty(block.getBackColor())) {
            setBackgroundColor(block.getBackColor());
        } else {
            setBackgroundColor("#00000000");
        }
    }

    public void setBackgroundColor(String color) {
        if (BlockUtil.isColor(color)) {
            setBackgroundColor(itemView, color);
        }
    }

    public void setBackgroundImage(String imagePath) {
        if (imagePath != null && !imagePath.equals(mImagePath)) {
            mImagePath = imagePath;
            config.setBackgroundImage(itemView, imagePath, Color.TRANSPARENT);
        }
    }

    public void setBackgroundColor(View target, String color) {
        target.setBackgroundColor(BlockManager.getBackColor(color));
    }
}

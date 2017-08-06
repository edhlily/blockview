package com.zjsx.blocklayout.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.zjsx.blocklayout.config.BlockConfig;
import com.zjsx.blocklayout.config.BlockContext;
import com.zjsx.blocklayout.holder.BlockHolder;
import com.zjsx.blocklayout.module.Block;
import com.zjsx.blocklayout.config.BlockDataConfig;
import com.zjsx.blocklayout.tools.BlockUtil;

import java.util.ArrayList;
import java.util.List;

import static com.zjsx.blocklayout.config.BlockDataConfig.LAYOUT_TYPE_GRID;
import static com.zjsx.blocklayout.config.BlockDataConfig.LAYOUT_TYPE_LINEAR;
import static com.zjsx.blocklayout.config.BlockDataConfig.LAYOUT_TYPE_STAGGER;


public class BlockView extends RecyclerView {

    private final List<Block> data = new ArrayList<>();

    private final Adapter adapter = new BlockAdapter();

    private BlockContext blockContext;

    private BlockDataConfig config;

    private BlockItemDecoration decoration;

    public BlockView(Context context) {
        this(context, null);
    }

    public BlockView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BlockView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        blockContext = new BlockContext(context);
        setItemViewCacheSize(50);
        setOverScrollMode(View.OVER_SCROLL_NEVER);
        super.setAdapter(adapter);
    }

    /*不允许从外部设置Adapter*/

    @Override
    public void setAdapter(Adapter adapter) {
        throw new RuntimeException("Adapter already set!");
    }

    public BlockContext getBlockContext() {
        return blockContext;
    }

    public BlockDataConfig getConfig() {
        return config;
    }

    public void setConfig(BlockDataConfig config) {
        if (blockContext == null) {
            throw new NullPointerException("Please set BlockContext first!");
        }
        switch (String.valueOf(config.getLayout())) {
            case "grid":
                blockContext.setCurentLayoutType(LAYOUT_TYPE_GRID);
                break;
            case "stagger":
                blockContext.setCurentLayoutType(LAYOUT_TYPE_STAGGER);
                break;
            default:
                blockContext.setCurentLayoutType(LAYOUT_TYPE_LINEAR);
                break;
        }
        this.config = config;

        if (!BlockUtil.isEmpty(config.getBackImage())) {
            BlockConfig.getInstance().getImageLoader().setBackgroundImage(this, config.getBackImage());
        }

        setBackgroundColor(BlockConfig.getInstance().getBackColor(config.getBackColor()));

        setLayoutManager();
        setData(config.getModules());
    }

    private void setLayoutManager() {
        final int cellCount = config.getCellCount() > 0 ? config.getCellCount() : 1;
        LayoutManager layoutManager;
        switch (blockContext.getCurentLayoutType()) {

            case LAYOUT_TYPE_STAGGER:
                StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(cellCount, VERTICAL);
                staggeredGridLayoutManager.setItemPrefetchEnabled(false);
                layoutManager = staggeredGridLayoutManager;
                break;
            case LAYOUT_TYPE_GRID:
                GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), cellCount);
                gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) {
                        if (data.get(position).getColSpan() == 0) {
                            return cellCount;
                        } else if (data.get(position).getColSpan() <= cellCount) {
                            return data.get(position).getColSpan();
                        } else {
                            return cellCount;
                        }
                    }
                });
                layoutManager = gridLayoutManager;
                break;
            default:
                layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        }

        if (decoration != null) {
            removeItemDecoration(decoration);
        }
        decoration = new BlockItemDecoration(blockContext, config);
        addItemDecoration(decoration);
        setLayoutManager(layoutManager);
    }

    @Override
    public Adapter getAdapter() {
        return adapter;
    }

    public List<Block> getData() {
        return data;
    }

    public void setData(List<Block> data) {
        this.data.clear();
        this.data.addAll(data);

        adapter.notifyDataSetChanged();
    }

    private class BlockAdapter extends RecyclerView.Adapter<BlockHolder> {

        @Override
        public long getItemId(int position) {
            return position;
        }


        @Override
        public int getItemViewType(int position) {
            return BlockConfig.getInstance().getViewType(data.get(position));
        }

        @Override
        public BlockHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            BlockHolder blockHolder = blockContext.getHolder(parent, viewType);
            return blockHolder;
        }

        Block block;

        @Override
        public void onBindViewHolder(BlockHolder holder, int position) {
            block = data.get(position);
            if (block.equals(holder.getBlock())) return;
            try {
                int heightSize = BlockConfig.getInstance().getSize(block.getHeight());

                ViewGroup.LayoutParams layoutParams;

                switch (blockContext.getCurentLayoutType()) {
                    case LAYOUT_TYPE_GRID:
                        layoutParams = new GridLayoutManager.LayoutParams(LayoutParams.MATCH_PARENT, heightSize);
                        break;
                    case LAYOUT_TYPE_STAGGER:
                        //stagger模式设定高度之后里面的margin会造成内容的压缩，所以这里加上会被压缩的高度
                        if (heightSize != ViewGroup.LayoutParams.WRAP_CONTENT) {
                            heightSize += BlockConfig.getInstance().getSize(block.getMarginTop());
                            heightSize += BlockConfig.getInstance().getSize(block.getMarginBottom());
                        }
                        layoutParams = new StaggeredGridLayoutManager.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, heightSize);
                        if (block.getColSpan() == 0) {
                            ((StaggeredGridLayoutManager.LayoutParams) layoutParams).setFullSpan(true);
                        } else {
                            ((StaggeredGridLayoutManager.LayoutParams) layoutParams).setFullSpan(false);

                        }
                        break;
                    default:
                        layoutParams = new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, heightSize);
                        break;
                }

                holder.setItemLayoutParams(layoutParams);
                holder.bind(block);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public int getItemCount() {
            return data.size();
        }
    }

    public interface OnBlockClickListener {
        boolean isClickable(Block block);

        void onBlockClick(Block block);
    }

    public OnBlockClickListener getOnBlockClickListener() {
        return blockContext.getOnBlockClickListener();
    }

    public void setOnBlockClickListener(OnBlockClickListener onBlockClickListener) {
        blockContext.setOnBlockClickListener(onBlockClickListener);
    }

    /**
     * 这个模块是否可以点击
     * 先判断的clickable是否为true
     * 如果clickable为true，再调用这个方法进一步自定义是否可以点击。
     * 最后检查对应的view的clickable属性是否为true
     */
    public interface BlockClickInterceptor {
        boolean isClickable(Block block);
    }


    public BlockClickInterceptor getBlockClickInterceptor() {
        return blockContext.getBlockClickInterceptor();
    }

    public void setBlockClickInterceptor(BlockClickInterceptor blockClickInterceptor) {
        blockContext.setBlockClickInterceptor(blockClickInterceptor);
    }


}

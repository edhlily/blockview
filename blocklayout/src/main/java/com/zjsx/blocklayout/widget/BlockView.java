package com.zjsx.blocklayout.widget;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.zjsx.blocklayout.config.BlockManager;
import com.zjsx.blocklayout.holder.BlockHolder;
import com.zjsx.blocklayout.holder.TabsHolder;
import com.zjsx.blocklayout.module.Block;
import com.zjsx.blocklayout.config.BlockConfig;
import com.zjsx.blocklayout.module.ImageItem;
import com.zjsx.blocklayout.tools.BlockUtil;

import java.util.ArrayList;
import java.util.List;

import static com.zjsx.blocklayout.config.BlockConfig.LAYOUT_TYPE_GRID;
import static com.zjsx.blocklayout.config.BlockConfig.LAYOUT_TYPE_LINEAR;
import static com.zjsx.blocklayout.config.BlockConfig.LAYOUT_TYPE_STAGGER;


public class BlockView extends RecyclerView {

    private final List<Block> data = new ArrayList<>();

    private final Adapter adapter = new BlockAdapter();

    private BlockManager blockManager;

    private BlockConfig config;

    private BlockItemDecoration decoration;

    public BlockView(Context context) {
        this(context, null);
    }

    public BlockView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BlockView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setItemViewCacheSize(50);
        setOverScrollMode(View.OVER_SCROLL_NEVER);
    }

    /*不允许从外部设置Adapter*/

    @Override
    public void setAdapter(Adapter adapter) {
        throw new RuntimeException("Adapter already set!");
    }

    public BlockManager getBlockManager() {
        return blockManager;
    }

    public void setBlockManager(BlockManager blockManager) {
        this.blockManager = blockManager;
        super.setAdapter(adapter);
    }

    public BlockConfig getConfig() {
        return config;
    }

    public void setConfig(BlockConfig config) {
        if (blockManager == null) {
            throw new NullPointerException("Please set BlockManager first!");
        }
        switch (String.valueOf(config.getLayout())) {
            case "grid":
                blockManager.setCurentLayoutType(LAYOUT_TYPE_GRID);
                break;
            case "stagger":
                blockManager.setCurentLayoutType(LAYOUT_TYPE_STAGGER);
                break;
            default:
                blockManager.setCurentLayoutType(LAYOUT_TYPE_LINEAR);
                break;
        }
        this.config = config;

        if (!BlockUtil.isEmpty(config.getBackImage())) {
            blockManager.setBackgroundImage(this, config.getBackImage(), Color.TRANSPARENT);
        }

        setBackgroundColor(BlockManager.getBackColor(config.getBackColor()));

        setLayoutManager();
        setData(config.getModules());
    }

    private void setLayoutManager() {
        final int cellCount = config.getCellCount() > 0 ? config.getCellCount() : 1;
        LayoutManager layoutManager;
        switch (blockManager.getCurentLayoutType()) {

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
        decoration = new BlockItemDecoration(blockManager, config);
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

    class BlockAdapter extends RecyclerView.Adapter<BlockHolder> implements TabsHolder.ContainerReplacer {

        @Override
        public long getItemId(int position) {
            return position;
        }


        @Override
        public int getItemViewType(int position) {
            return blockManager.getViewType(data.get(position));
        }

        @Override
        public BlockHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            BlockHolder blockHolder = blockManager.getHolder(parent, viewType);
            return blockHolder;
        }

        Block block;

        @Override
        public void onBindViewHolder(BlockHolder holder, int position) {
            block = data.get(position);
            if (block.equals(holder.getBlock())) return;
            try {
                int heightSize = blockManager.getSize(block.getHeight());

                ViewGroup.LayoutParams layoutParams;

                switch (blockManager.getCurentLayoutType()) {
                    case LAYOUT_TYPE_GRID:
                        layoutParams = new GridLayoutManager.LayoutParams(LayoutParams.MATCH_PARENT, heightSize);
                        break;
                    case LAYOUT_TYPE_STAGGER:
                        //stagger模式设定高度之后里面的margin会造成内容的压缩，所以这里加上会被压缩的高度
                        if (heightSize != ViewGroup.LayoutParams.WRAP_CONTENT) {
                            heightSize += blockManager.getSize(block.getMarginTop());
                            heightSize += blockManager.getSize(block.getMarginBottom());
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

        @Override
        public void replace(final int position, Block container) {
            data.remove(position);
            data.add(position, container);
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    notifyItemChanged(position);
                }
            });
        }
    }

}

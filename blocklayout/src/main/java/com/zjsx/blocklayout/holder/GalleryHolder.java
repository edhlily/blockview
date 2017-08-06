package com.zjsx.blocklayout.holder;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.zjsx.blocklayout.config.BlockConfig;
import com.zjsx.blocklayout.config.BlockContext;
import com.zjsx.blocklayout.module.Block;
import com.zjsx.blocklayout.module.GalleryContainer;

import java.util.ArrayList;
import java.util.List;

public class GalleryHolder extends BlockHolder<GalleryContainer> {
    public RecyclerView horizontalRecyclerView;
    LinearLayoutManager mLinearLayoutManager;
    final List<Block> mDatas = new ArrayList<>();
    GalleryAdapter mAdapter;

    public GalleryHolder(BlockContext blockContext, ViewGroup parent) {
        super(blockContext, new RecyclerView(parent.getContext()), BlockConfig.getInstance().getViewType(GalleryContainer.class));
        horizontalRecyclerView = (RecyclerView) itemView;
        horizontalRecyclerView.setFocusable(false);
        mLinearLayoutManager = new LinearLayoutManager(parent.getContext(), LinearLayoutManager.HORIZONTAL, false);
        horizontalRecyclerView.setLayoutManager(mLinearLayoutManager);
        mAdapter = new GalleryAdapter();
        horizontalRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void bindData(final GalleryContainer galleryContainer) {
        mDatas.clear();
        mDatas.addAll(galleryContainer.getItems());
        mAdapter.notifyDataSetChanged();
    }

    public class GalleryAdapter extends RecyclerView.Adapter<BlockHolder> {

        @Override
        public int getItemViewType(int position) {
            return BlockConfig.getInstance().getViewType(mDatas.get(position));
        }

        @Override
        public BlockHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            BlockHolder blockHolder = blockContext.getHolder(parent, viewType);
            blockHolder.setItemLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
            return blockHolder;
        }

        @Override
        public void onBindViewHolder(BlockHolder holder, int position) {
            int width = BlockConfig.getInstance().getSize(mDatas.get(position).getWidth());
            holder.setItemLayoutParams(width, ViewGroup.MarginLayoutParams.MATCH_PARENT);
            holder.bind(mDatas.get(position));
        }

        @Override
        public int getItemCount() {
            return mDatas.size();
        }
    }
}

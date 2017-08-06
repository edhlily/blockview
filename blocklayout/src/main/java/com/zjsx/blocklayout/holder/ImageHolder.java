package com.zjsx.blocklayout.holder;

import android.view.ViewGroup;
import android.widget.ImageView;

import com.zjsx.blocklayout.config.BlockConfig;
import com.zjsx.blocklayout.config.BlockContext;
import com.zjsx.blocklayout.module.ImageItem;

public class ImageHolder extends BlockHolder<ImageItem> {
    ImageView imageView;

    public ImageHolder(BlockContext blockContext, ViewGroup parent) {
        super(blockContext, new ImageView(parent.getContext()), BlockConfig.getInstance().getViewType(ImageItem.class));
        imageView = (ImageView) itemView;
    }

    @Override
    public void bindData(final ImageItem date) {
        imageView.setScaleType(BlockConfig.getInstance().getScaleType(date.getScaleType()));
        if (imageView.getLayoutParams().width == ViewGroup.LayoutParams.WRAP_CONTENT
                || imageView.getLayoutParams().height == ViewGroup.LayoutParams.WRAP_CONTENT) {
            imageView.setAdjustViewBounds(true);
        }
        BlockConfig.getInstance().getImageLoader().loadImg(itemView.getContext(), date.getSrc(), imageView);
    }
}
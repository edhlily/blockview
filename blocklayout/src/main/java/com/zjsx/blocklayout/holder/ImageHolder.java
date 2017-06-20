package com.zjsx.blocklayout.holder;

import android.view.ViewGroup;
import android.widget.ImageView;

import com.zjsx.blocklayout.config.BlockManager;
import com.zjsx.blocklayout.module.ImageItem;

public class ImageHolder extends BlockHolder<ImageItem> {
    ImageView imageView;

    public ImageHolder(BlockManager config, ViewGroup parent) {
        super(config, new ImageView(parent.getContext()), config.getViewType(ImageItem.class));
        imageView = (ImageView) itemView;
    }

    @Override
    public void bindData(final ImageItem date) {
        imageView.setScaleType(config.getScaleType(date.getScaleType()));
        if (imageView.getLayoutParams().width == ViewGroup.LayoutParams.WRAP_CONTENT
                || imageView.getLayoutParams().height == ViewGroup.LayoutParams.WRAP_CONTENT) {
            imageView.setAdjustViewBounds(true);
        }
        config.loadImg(itemView.getContext(), date.getSrc(), imageView);
    }
}
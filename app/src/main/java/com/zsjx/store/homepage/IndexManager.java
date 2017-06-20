package com.zsjx.store.homepage;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.zjsx.blocklayout.config.BlockManager;
import com.zjsx.blocklayout.module.Block;
import com.zjsx.blocklayout.module.ImageItem;
import com.zjsx.blocklayout.tools.BlockUtil;
import com.zsjx.store.homepage.lib.tools.ViewBackgroundTarget;
import com.zsjx.store.homepage.module.CountdownClock;

public class IndexManager extends BlockManager {

    public IndexManager(Context context) {
        super(context);
        regesterBlock("countdownClock", new CountdownClock());
    }

    @Override
    public boolean isClickable(Block block) {
        //link不为空才可以点击
        return !BlockUtil.isEmpty(block.getLink());
    }

    @Override
    public void onItemClick(Block block) {
        if (block == null) return;
        if (block instanceof ImageItem) {
            Toast.makeText(mContext, ((ImageItem) block).getSrc(), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(mContext, block.getLink(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void loadImg(Context context, String url, final ImageView imageView) {
        Glide.with(context)
                .load(url.replace("assets", "file:///android_asset"))
                .into(new GlideDrawableImageViewTarget(imageView) {
                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        imageView.setImageDrawable(null);
                        //super.onLoadFailed(e, errorDrawable);
                    }
                });

//        ImageLoader.getInstance().displayImage(url.replace("assets", "assets:/"), imageView);
    }

    @Override
    public void setBackgroundImage(final View target, String url, final int errorBgColor) {
        Glide.with(target.getContext())
                .load(url.replace("assets", "file:///android_asset"))
                .into(new ViewBackgroundTarget<GlideDrawable>(target) {
                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        //super.onLoadFailed(e, errorDrawable);
                        target.setBackgroundColor(errorBgColor);
                    }

                    @Override
                    protected void setResource(GlideDrawable resource) {
                        setBackground(resource);
                    }
                });

//        ImageLoader.getInstance().loadImage(url, new SimpleImageLoadingListener() {
//            @Override
//            public void onLoadingComplete(String url, View view, Bitmap loadedImage) {
//                super.onLoadingComplete(url.replace("assets", "assets:/"), view, loadedImage);
//                view.setBackgroundDrawable(new BitmapDrawable(loadedImage));
//            }
//        });
    }
}

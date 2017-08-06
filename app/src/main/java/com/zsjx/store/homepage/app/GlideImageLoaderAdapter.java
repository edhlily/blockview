package com.zsjx.store.homepage.app;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.zjsx.blocklayout.config.BlockConfig;
import com.zsjx.store.homepage.lib.tools.ViewBackgroundTarget;

/**
 * Created by eddie on 2017-08-06.
 */

public class GlideImageLoaderAdapter implements BlockConfig.ImageLoaderAdapter {
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

    }

    @Override
    public void setBackgroundImage(final View target, String url) {
        Glide.with(target.getContext())
                .load(url.replace("assets", "file:///android_asset"))
                .into(new ViewBackgroundTarget<GlideDrawable>(target) {
                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        //super.onLoadFailed(e, errorDrawable);
                        target.setBackgroundColor(Color.TRANSPARENT);
                    }

                    @Override
                    protected void setResource(GlideDrawable resource) {
                        setBackground(resource);
                    }
                });

    }
}

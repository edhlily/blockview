package com.zsjx.store.homepage.app;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.zjsx.blocklayout.config.BlockConfig;

/**
 * Created by eddie on 2017-08-06.
 */

public class UILImageLoaderAdapter implements BlockConfig.ImageLoaderAdapter {
    @Override
    public void loadImg(Context context, String url, final ImageView imageView) {
        ImageLoader.getInstance().displayImage(url.replace("assets", "assets:/"), imageView);
    }

    @Override
    public void setBackgroundImage(final View target, String url) {

        ImageLoader.getInstance().loadImage(url, new SimpleImageLoadingListener() {
            @Override
            public void onLoadingComplete(String url, View view, Bitmap loadedImage) {
                super.onLoadingComplete(url.replace("assets", "assets:/"), view, loadedImage);
                view.setBackgroundDrawable(new BitmapDrawable(loadedImage));
            }
        });
    }
}

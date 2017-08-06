package com.zsjx.store.homepage.app;

import android.app.Application;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.decode.BaseImageDecoder;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.zjsx.blocklayout.config.BlockConfig;
import com.zjsx.blocklayout.config.BlockConfigSetter;
import com.zsjx.store.homepage.lib.tools.DToast;
import com.zsjx.store.homepage.module.CountdownClock;

import java.io.File;

public class StoreApp extends Application {
    private static StoreApp app;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        DToast.init(this);

        initUIL();

        initBlockConfig();
    }

    //现在不推荐使用UIL这个库，仅仅测试用
    void initUIL() {
        File cacheDir = StorageUtils.getCacheDirectory(this);
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .memoryCacheExtraOptions(480, 800) // default = device screen dimensions
                .diskCacheExtraOptions(480, 800, null)
                .threadPoolSize(3) // default
                .threadPriority(Thread.NORM_PRIORITY - 2) // default
                .tasksProcessingOrder(QueueProcessingType.FIFO) // default
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
                .memoryCacheSize(2 * 1024 * 1024)
                .memoryCacheSizePercentage(13) // default
                .diskCache(new UnlimitedDiskCache(cacheDir)) // default
                .diskCacheSize(50 * 1024 * 1024)
                .diskCacheFileCount(100)
                .diskCacheFileNameGenerator(new HashCodeFileNameGenerator()) // default
                .imageDownloader(new BaseImageDownloader(this)) // default
                .imageDecoder(new BaseImageDecoder(false)) // default
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple()) // default
                .writeDebugLogs()
                .build();

        ImageLoader.getInstance().init(config);
    }

    void initBlockConfig() {
        //使用Glide作为图片加载器
        BlockConfig.ImageLoaderAdapter adapter = new GlideImageLoaderAdapter();

        //使用UniversalImageLoader作为图片加载器
        //BlockConfig.ImageLoaderAdapter adapter = new UILImageLoaderAdapter();

        BlockConfigSetter setter = new BlockConfigSetter(this, adapter)
                .registerBlock("countdownClock", new CountdownClock());


        BlockConfig.getInstance().init(setter);
    }

    public static StoreApp get() {
        return app;
    }
}

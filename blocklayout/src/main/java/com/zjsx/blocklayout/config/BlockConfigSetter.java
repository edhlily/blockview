package com.zjsx.blocklayout.config;

import android.app.Activity;
import android.content.Context;

import com.zjsx.blocklayout.generator.HashCodeViewTypeGenerator;
import com.zjsx.blocklayout.generator.ViewTypeGenerator;
import com.zjsx.blocklayout.module.Block;
import com.zjsx.blocklayout.module.Frame;
import com.zjsx.blocklayout.module.GalleryContainer;
import com.zjsx.blocklayout.module.GridContainer;
import com.zjsx.blocklayout.module.HorizontalBanner;
import com.zjsx.blocklayout.module.ImageItem;
import com.zjsx.blocklayout.module.Linear;
import com.zjsx.blocklayout.module.TabBar;
import com.zjsx.blocklayout.module.TableContainer;
import com.zjsx.blocklayout.module.TextItem;
import com.zjsx.blocklayout.module.VerticalBanner;
import com.zjsx.blocklayout.parser.DPParser;
import com.zjsx.blocklayout.parser.EmptyNullParser;
import com.zjsx.blocklayout.parser.FillParser;
import com.zjsx.blocklayout.parser.PXParser;
import com.zjsx.blocklayout.parser.SHParser;
import com.zjsx.blocklayout.parser.SPParser;
import com.zjsx.blocklayout.parser.SWParser;
import com.zjsx.blocklayout.parser.SizeParser;
import com.zjsx.blocklayout.parser.WrapParser;
import com.zjsx.blocklayout.tools.BlockUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by eddie on 2017-08-06.
 */

public class BlockConfigSetter {

    private Context context;

    private ViewTypeGenerator viewTypeGenerator = new HashCodeViewTypeGenerator();

    private List<SizeParser> sizeParsers = new ArrayList<>();

    private BlockConfig.ImageLoaderAdapter imageLoaderAdapter;

    private Map<String, Block> blockMap = new HashMap<>();


    public BlockConfigSetter(Context context, BlockConfig.ImageLoaderAdapter imageLoaderAdapter) {

        if (context == null) {
            throw new NullPointerException("context can not be null!");
        }

        if (context instanceof Activity) {
            this.context = context.getApplicationContext();
        } else {
            this.context = context;
        }

        BlockUtil.init(context);

        if (imageLoaderAdapter == null) {
            throw new NullPointerException("imageLoader can not be null!");
        }

        this.imageLoaderAdapter = imageLoaderAdapter;

        //本库默认支持的Block
        blockMap.put("gallery", new GalleryContainer());
        blockMap.put("table", new TableContainer());
        blockMap.put("grid", new GridContainer());
        blockMap.put("tab", new TabBar());
        blockMap.put("linear", new Linear());
        blockMap.put("frame", new Frame());

        blockMap.put("text", new TextItem());
        blockMap.put("image", new ImageItem());
        blockMap.put("verticalBanner", new VerticalBanner());
        blockMap.put("horizontalBanner", new HorizontalBanner());

        //本库默认支持的sizeParser
        sizeParsers.add(new EmptyNullParser());
        sizeParsers.add(new PXParser());
        sizeParsers.add(new DPParser(context));
        sizeParsers.add(new SPParser(context));
        sizeParsers.add(new WrapParser());
        sizeParsers.add(new FillParser());
        sizeParsers.add(new SWParser());
        sizeParsers.add(new SHParser());
    }

    public BlockConfigSetter setViewTypeGenerator(ViewTypeGenerator viewTypeGenerator) {
        if (viewTypeGenerator == null) {
            this.viewTypeGenerator = new HashCodeViewTypeGenerator();
        } else {
            this.viewTypeGenerator = viewTypeGenerator;
        }
        return this;
    }

    public BlockConfigSetter addSizeParser(SizeParser sizeParser) {
        sizeParsers.add(0, sizeParser);
        return this;
    }

    public BlockConfigSetter registerBlock(String blockName, Block block) {

        if (blockMap.containsKey(blockName)) {
            throw new RuntimeException(blockName + "already registered!");
        }

        blockMap.put(blockName, block);

        return this;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public ViewTypeGenerator getViewTypeGenerator() {
        return viewTypeGenerator;
    }

    public List<SizeParser> getSizeParsers() {
        return sizeParsers;
    }

    public BlockConfig.ImageLoaderAdapter getImageLoaderAdapter() {
        return imageLoaderAdapter;
    }

    public Map<String, Block> getBlockMap() {
        return blockMap;
    }

}

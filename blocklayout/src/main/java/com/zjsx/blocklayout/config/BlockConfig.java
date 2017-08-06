package com.zjsx.blocklayout.config;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zjsx.blocklayout.generator.HashCodeViewTypeGenerator;
import com.zjsx.blocklayout.generator.ViewTypeGenerator;
import com.zjsx.blocklayout.holder.BlockHolder;
import com.zjsx.blocklayout.module.Block;
import com.zjsx.blocklayout.parser.SizeParser;
import com.zjsx.blocklayout.tools.BlockUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.zjsx.blocklayout.config.BlockDataConfig.SPACING_ITEM_ALL;
import static com.zjsx.blocklayout.config.BlockDataConfig.SPACING_ITEM_DECENTRALIZED;
import static com.zjsx.blocklayout.config.BlockDataConfig.SPACING_MODE_AROUND;
import static com.zjsx.blocklayout.config.BlockDataConfig.SPACING_MODE_MIDDLE;

/**
 * Created by eddie on 2017-08-06.
 */

public class BlockConfig {

    private static volatile BlockConfig instance;

    private Context mContext;

    public static BlockConfig getInstance() {
        if (instance == null) {
            synchronized (BlockConfig.class) {
                if (instance == null) {
                    instance = new BlockConfig();
                }
            }
        }

        return instance;
    }

    /**
     * 通过viewType拿到某个Block的实例，拿到实例之后就可以创建某个Holder
     */
    private Map<Integer, Block> viewTypeObjectMap = new HashMap<>();


    /**
     * 通过viewType拿到某个Block的Class
     */
    private Map<Class, Integer> clsViewTypeMap = new HashMap<>();

    /**
     * 通过某个Block的Type拿到它的Class
     */
    private Map<String, Class> typeClsMap = new HashMap<>();


    /**
     * 提供的默认的解析Size的Parser
     */
    private List<SizeParser> globalSizeParsers = new ArrayList<>();

    private ViewTypeGenerator viewTypeGenerator;

    /**
     * 图片，背景加载器，方便支持多种图片加载框架
     */
    public interface ImageLoaderAdapter {
        void loadImg(Context context, String url, final ImageView imageView);

        void setBackgroundImage(View target, String url);

    }

    private ImageLoaderAdapter imageLoaderAdapter;

    public BlockConfig init(BlockConfigSetter configSetter) {
        if (configSetter == null) {
            throw new RuntimeException("configSetter can not be null!");
        }

        this.mContext = configSetter.getContext();
        this.imageLoaderAdapter = configSetter.getImageLoaderAdapter();
        this.viewTypeGenerator = configSetter.getViewTypeGenerator();

        for (String name : configSetter.getBlockMap().keySet()) {
            regesterBlock(name, configSetter.getBlockMap().get(name));
        }

        for (SizeParser parser : configSetter.getSizeParsers()) {
            addGlobalSizeParser(parser);
        }

        return this;
    }

    public ImageLoaderAdapter getImageLoader() {
        return imageLoaderAdapter;
    }


    /**
     * 注册自定义模块的入口
     *
     * @param type  Block的名字
     * @param block Block的一个实例
     */
    private BlockConfig regesterBlock(String type, Block block) {
        if (type == null) {
            throw new NullPointerException();
        }

        if (typeClsMap.containsKey(type)) {
            throw new RuntimeException("type:" + type + "already regesterd with " + typeClsMap.get(type).getName() + "!");
        }

        if (viewTypeGenerator == null) {
            viewTypeGenerator = new HashCodeViewTypeGenerator();
        }

        int viewType = viewTypeGenerator.generate(type);

        viewTypeObjectMap.put(viewType, block);
        clsViewTypeMap.put(block.getClass(), viewType);
        typeClsMap.put(type, block.getClass());

        return this;
    }

    private BlockConfig addGlobalSizeParser(SizeParser parser) {
        globalSizeParsers.add(parser);
        return this;
    }


    private SizeParser getSizeParser(List<SizeParser> parsers, final String suffix) {
        for (SizeParser parser : parsers) {
            if (parser.accept(suffix)) return parser;
        }
        return null;
    }

    public int getSize(String size, List<SizeParser> customParsers) {

        String suffix = null;
        float perfix = -1;

        if (!BlockUtil.isEmpty(size)) {
            size = size.trim();
            int index = 0;
            for (; index < size.length(); index++) {
                char c = size.charAt(index);
                if ((c >= '0' && c <= '9') || c == '.' || c == '-') continue;
                break;
            }
            if (index != 0) {
                perfix = Float.valueOf(size.substring(0, index));
            }
            suffix = size.substring(index);
        }

        SizeParser parser = null;


        //先找自定义的SizeParser
        if (customParsers != null && customParsers.size() > 0) {
            parser = getSizeParser(customParsers, suffix);
        }

        //后找预定义的SizeParser
        if (parser == null) {
            parser = getSizeParser(globalSizeParsers, suffix);
        }

        if (parser == null) {
            throw new RuntimeException("unrecognized size format:" + size);
        }

        return Math.round(parser.getSize(perfix));
    }

    public int getSize(String size, final SizeParser sizeParser) {
        return getSize(size, new ArrayList<SizeParser>() {{
            add(sizeParser);
        }});
    }

    public int getSize(String size) {
        return getSize(size, new ArrayList<SizeParser>());
    }

    /**
     * 通过Block的实例获取viewType
     *
     * @param block 的实例
     * @return
     */
    public int getViewType(Block block) {
        return clsViewTypeMap.get(block.getClass());
    }

    /**
     * 通过Block的Class获取viewType
     *
     * @param blockClass
     * @return
     */
    public int getViewType(Class blockClass) {
        return clsViewTypeMap.get(blockClass);
    }

    /**
     * 通过Block的type获取class
     *
     * @param type
     * @return
     */
    public Class getClass(String type) {
        return typeClsMap.get(type);
    }


    /**
     * 通过ViewType获取Block对应的Holder
     *
     * @param parent
     * @param viewType
     * @return
     */
    public BlockHolder getHolder(ViewGroup parent, int viewType, BlockContext blockContext) {
        return viewTypeObjectMap.get(viewType).getHolder(blockContext, parent);
    }


    /**
     * 获取文字的样式
     * textView.setTypeface(null, Typeface.BOLD_ITALIC);
     * textView.setTypeface(null, Typeface.BOLD);
     * textView.setTypeface(null, Typeface.ITALIC);
     * textView.setTypeface(null, Typeface.NORMAL);
     *
     * @param style
     * @return
     */
    public int getTextStyle(String style) {
        switch (String.valueOf(style)) {
            case "bold":
                return Typeface.BOLD;
            case "boldItalic":
                return Typeface.BOLD_ITALIC;
            case "italic":
                return Typeface.ITALIC;
            default:
                return Typeface.NORMAL;
        }
    }

    /**
     * 获取Gravity
     *
     * @param gravityStr
     * @return
     */
    public int getViewGravity(String gravityStr) {
        int viewGravity = Gravity.NO_GRAVITY;
        if (!BlockUtil.isEmpty(gravityStr)) {
            String[] gs = gravityStr.split("\\|");
            for (String g : gs) {
                int gg = Gravity.NO_GRAVITY;
                switch (g) {
                    case "center":
                        gg = Gravity.CENTER;
                        break;
                    case "left":
                        gg = Gravity.LEFT;
                        break;
                    case "right":
                        gg = Gravity.RIGHT;
                        break;
                    case "bottom":
                        gg = Gravity.BOTTOM;
                        break;
                    case "top":
                        gg = Gravity.TOP;
                        break;
                    case "centerVertical":
                        gg = Gravity.CENTER_VERTICAL;
                        break;
                    case "centerHorizontal":
                        gg = Gravity.CENTER_HORIZONTAL;
                        break;
                }
                viewGravity |= gg;
            }
        }
        return viewGravity;
    }

    /**
     * 获取spacing的模式，
     * SPACING_MODE_MIDDLE：只有item之间有间隔
     * SPACING_MODE_AROUND：除了item之间有间隔，item和父控件之间也有间隔
     *
     * @param spacingMode
     * @return
     */
    public int getSpacingMode(String spacingMode) {
        switch (String.valueOf(spacingMode)) {
            case "around":
                return SPACING_MODE_AROUND;
            default:
                return SPACING_MODE_MIDDLE;
        }
    }

    /**
     * RecyclerViewItem之间的间隔方式
     * SPACING_ITEM_ALL：所有的Item之间有间隔
     * SPACING_ITEM_DECENTRALIZED：散列的Item之间有间隔，跨整列的item之间没有间隔
     *
     * @param spacingItem
     * @return
     */
    public int getSpacingItem(String spacingItem) {
        switch (String.valueOf(spacingItem)) {
            case "all":
                return SPACING_ITEM_ALL;
            default:
                return SPACING_ITEM_DECENTRALIZED;
        }
    }

    public ImageView.ScaleType getScaleType(String scaleType) {
        switch (String.valueOf(scaleType)) {
            case "center":
                return ImageView.ScaleType.CENTER;
            case "centerCrop":
                return ImageView.ScaleType.CENTER_CROP;
            case "centerInside":
                return ImageView.ScaleType.CENTER_INSIDE;
            case "fitCenter":
                return ImageView.ScaleType.FIT_CENTER;
            case "fitEnd":
                return ImageView.ScaleType.FIT_END;
            case "fitStart":
                return ImageView.ScaleType.FIT_START;
            case "matrix":
                return ImageView.ScaleType.MATRIX;
            default:
                return ImageView.ScaleType.FIT_XY;
        }
    }

    /**
     * 用于文字颜色
     *
     * @param textView
     * @param color
     */
    public void setTextColor(TextView textView, String color) {
        if (BlockUtil.isColor(color)) {
            textView.setTextColor(Color.parseColor(color));
        }
    }

    /**
     * 用于控件背景颜色
     *
     * @param color
     * @return
     */
    public int getBackColor(String color) {
        if (BlockUtil.isColor(color)) {
            return Color.parseColor(color);
        }
        return Color.TRANSPARENT;
    }

}

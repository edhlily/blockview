package com.zjsx.blocklayout.config;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zjsx.blocklayout.holder.BlockHolder;
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
import com.zjsx.blocklayout.parser.SWParser;
import com.zjsx.blocklayout.parser.SizeParser;
import com.zjsx.blocklayout.parser.WrapParser;
import com.zjsx.blocklayout.tools.BlockUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.zjsx.blocklayout.config.BlockConfig.SPACING_ITEM_ALL;
import static com.zjsx.blocklayout.config.BlockConfig.SPACING_ITEM_DECENTRALIZED;
import static com.zjsx.blocklayout.config.BlockConfig.SPACING_MODE_AROUND;
import static com.zjsx.blocklayout.config.BlockConfig.SPACING_MODE_MIDDLE;

public abstract class BlockManager {

    /**
     * 当前的布局类型
     */
    private int curentLayoutType = 0;

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

    protected Context mContext;

    public BlockManager(Context context) {
        this.mContext = context;

        BlockUtil.init(context);


        //本库默认支持的Block
        regesterBlock("gallery", new GalleryContainer());
        regesterBlock("table", new TableContainer());
        regesterBlock("grid", new GridContainer());
        regesterBlock("tab", new TabBar());
        regesterBlock("linear", new Linear());
        regesterBlock("frame", new Frame());

        regesterBlock("text", new TextItem());
        regesterBlock("image", new ImageItem());
        regesterBlock("verticalBanner", new VerticalBanner());
        regesterBlock("horizontalBanner", new HorizontalBanner());

        //本库默认支持的sizeParser
        globalSizeParsers.add(new EmptyNullParser());
        globalSizeParsers.add(new PXParser());
        globalSizeParsers.add(new DPParser(context));
        globalSizeParsers.add(new WrapParser());
        globalSizeParsers.add(new FillParser());
        globalSizeParsers.add(new SWParser());
        globalSizeParsers.add(new SHParser());
    }

    /**
     * 为某种Block生成一个唯一的viewType,目前采用hash值的方式
     *
     * @param type Block的名字
     * @return
     */
    private int createViewType(String type) {
        return type.hashCode();
    }

    /**
     * 注册自定义模块的入口
     *
     * @param type  Block的名字
     * @param block Block的一个实例
     */
    public void regesterBlock(String type, Block block) {
        if (type == null) {
            throw new NullPointerException();
        }

        if (typeClsMap.containsKey(type)) {
            throw new RuntimeException("type:" + type + "already regesterd with " + typeClsMap.get(type).getName() + "!");
        }

        int viewType = createViewType(type);

        viewTypeObjectMap.put(viewType, block);
        clsViewTypeMap.put(block.getClass(), viewType);
        typeClsMap.put(type, block.getClass());
    }

    public void addGlobalSizeParser(SizeParser parser) {
        globalSizeParsers.add(parser);
    }

    public void setCurentLayoutType(int curentLayoutType) {
        this.curentLayoutType = curentLayoutType;
    }

    public int getCurentLayoutType() {
        return curentLayoutType;
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
    public BlockHolder getHolder(ViewGroup parent, int viewType) {
        return viewTypeObjectMap.get(viewType).getHolder(this, parent);
    }

    /**
     * 用于自绘制组件的字体大小
     *
     * @param context
     * @param size
     * @return
     */
    public static float getFontPx(Context context, float size) {
        return BlockUtil.dip2px(context, size);
    }

    /**
     * 用户系统控件的字体大小
     *
     * @param textView
     * @param size
     */
    public static void setTextSize(TextView textView, float size) {
        if (size > 0) {
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
        }
    }

    /**
     * 用于文字颜色
     *
     * @param textView
     * @param color
     */
    public static void setTextColor(TextView textView, String color) {
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
    public static int getBackColor(String color) {
        if (BlockUtil.isColor(color)) {
            return Color.parseColor(color);
        }
        return Color.TRANSPARENT;
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

    public int getSpacingMode(String spacingMode) {
        switch (String.valueOf(spacingMode)) {
            case "around":
                return SPACING_MODE_AROUND;
            default:
                return SPACING_MODE_MIDDLE;
        }
    }

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
     * 这个模块是否可以点击
     * 判断一个block是否可以点击，先判断block的clickable是否为true
     * 如果clickable为true，再调用这个方法进一步自定义是否可以点击。
     *
     * @param block
     */
    public abstract boolean isClickable(Block block);

    /**
     * 处理点击事件
     *
     * @param block
     */
    public abstract void onItemClick(Block block);

    /**
     * 加载图片
     *
     * @param context
     * @param url
     * @param imageView
     */
    public abstract void loadImg(Context context, String url, final ImageView imageView);

    /**
     * 设置背景
     *
     * @param target
     * @param url
     * @param errorBgColor
     */
    public abstract void setBackgroundImage(View target, String url, int errorBgColor);
}

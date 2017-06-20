package com.zjsx.blocklayout.tools;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

public class BlockUtil {
    public static float SCREEN_WIDTH_DP;
    public static float SCREEN_HEIGHT_DP;
    public static int SCREEN_WIDTH_PX;
    public static int SCREEN_HEIGHT_PX;

    public static void init(Context context) {
        DisplayMetrics dm = getScreen(context);
        SCREEN_WIDTH_PX = dm.widthPixels;
        SCREEN_HEIGHT_PX = dm.heightPixels;
        SCREEN_WIDTH_DP = px2dip(context, SCREEN_WIDTH_PX);
        SCREEN_HEIGHT_DP = px2dip(context, SCREEN_HEIGHT_PX);
    }

    /**
     * 获取屏幕的大小0：宽度 1：高度
     */
    public static DisplayMetrics getScreen(Context context) {
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        int width = outMetrics.widthPixels;
        int height = outMetrics.heightPixels;
        return outMetrics;
    }

    /**
     * This method converts dp unit to equivalent pixels, depending on device density.
     *
     * @param dp      A value in dp (density independent pixels) unit. Which we need to convert into pixels
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent px equivalent to dp depending on device density
     */
    public static float dip2px(Context context, float dp) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
    }

    /**
     * This method converts device specific pixels to density independent pixels.
     *
     * @param px      A value in px (pixels) unit. Which we need to convert into db
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent dp equivalent to px value
     */
    public static float px2dip(Context context, float px) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return dp;
    }

    /**
     * 描述：判断一个字符串是否为null或空值.
     *
     * @param str 指定的字符串
     * @return true or false
     */
    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0 || "null".equals(str);
    }

    /**
     * 判断一个字符串是否是颜色#FFFFFF或者#00FFFFFF
     * @param color
     * @return
     */
    public static boolean isColor(String color) {
        if (!isEmpty(color)) {
            return color.matches("^#(([a-f]|[A-F]|[0-9]){2})?([a-f]|[A-F]|[0-9]){6}$");
        }
        return false;
    }
}

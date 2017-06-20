package com.zjsx.blocklayout.widget.roundview;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.example.blocklayout.R;


public class RoundViewDelegate {
    private View view;
    private Context context;
    private GradientDrawable gd_background = new GradientDrawable();
    private GradientDrawable gd_background_press = new GradientDrawable();
    private GradientDrawable gd_background_selected = new GradientDrawable();
    private GradientDrawable gd_background_enable = new GradientDrawable();
    private int backgroundColor;
    private int backgroundPressColor;
    private int backgroundSelectedColor;
    private int backgroundEnableColor;
    private int cornerRadius;
    private int cornerRadius_TL;
    private int cornerRadius_TR;
    private int cornerRadius_BL;
    private int cornerRadius_BR;
    private int strokeWidth;
    private int strokeColor;
    private int strokePressColor;
    private int strokeSelectedColor;
    private int strokeEnableColor;
    private int textPressColor;
    private int textSelectedColor;
    private int textEnableColor;
    private boolean isRadiusHalfHeight;
    private boolean isWidthHeightEqual;
    private boolean isRippleEnable;
    private float[] radiusArr = new float[8];

    public RoundViewDelegate(View view, Context context, AttributeSet attrs) {
        this.view = view;
        this.context = context;
        obtainAttributes(context, attrs);
    }

    private void obtainAttributes(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RoundTextView);
        backgroundColor = ta.getColor(R.styleable.RoundTextView_rv_backgroundColor, Color.TRANSPARENT);
        backgroundPressColor = ta.getColor(R.styleable.RoundTextView_rv_backgroundPressColor, Integer.MAX_VALUE);
        backgroundSelectedColor = ta.getColor(R.styleable.RoundTextView_rv_backgroundSelectedColor, Integer.MAX_VALUE);
        backgroundEnableColor = ta.getColor(R.styleable.RoundTextView_rv_backgroundEnableColor, Integer.MAX_VALUE);
        cornerRadius = ta.getDimensionPixelSize(R.styleable.RoundTextView_rv_cornerRadius, 0);
        strokeWidth = ta.getDimensionPixelSize(R.styleable.RoundTextView_rv_strokeWidth, 0);
        strokeColor = ta.getColor(R.styleable.RoundTextView_rv_strokeColor, Color.TRANSPARENT);
        strokePressColor = ta.getColor(R.styleable.RoundTextView_rv_strokePressColor, Integer.MAX_VALUE);
        strokeEnableColor = ta.getColor(R.styleable.RoundTextView_rv_strokeEnableColor, Integer.MAX_VALUE);

        strokeSelectedColor = ta.getColor(R.styleable.RoundTextView_rv_strokeSelectedColor, Integer.MAX_VALUE);
        textPressColor = ta.getColor(R.styleable.RoundTextView_rv_textPressColor, Integer.MAX_VALUE);
        textSelectedColor = ta.getColor(R.styleable.RoundTextView_rv_textSelectedColor, Integer.MAX_VALUE);
        textEnableColor = ta.getColor(R.styleable.RoundTextView_rv_textEnableColor, Integer.MAX_VALUE);
        isRadiusHalfHeight = ta.getBoolean(R.styleable.RoundTextView_rv_isRadiusHalfHeight, false);
        isWidthHeightEqual = ta.getBoolean(R.styleable.RoundTextView_rv_isWidthHeightEqual, false);
        cornerRadius_TL = ta.getDimensionPixelSize(R.styleable.RoundTextView_rv_cornerRadius_TL, 0);
        cornerRadius_TR = ta.getDimensionPixelSize(R.styleable.RoundTextView_rv_cornerRadius_TR, 0);
        cornerRadius_BL = ta.getDimensionPixelSize(R.styleable.RoundTextView_rv_cornerRadius_BL, 0);
        cornerRadius_BR = ta.getDimensionPixelSize(R.styleable.RoundTextView_rv_cornerRadius_BR, 0);
        isRippleEnable = ta.getBoolean(R.styleable.RoundTextView_rv_isRippleEnable, true);

        ta.recycle();
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
        setBgSelector();
    }

    public void setBackgroundPressColor(int backgroundPressColor) {
        this.backgroundPressColor = backgroundPressColor;
        setBgSelector();
    }

    public void setBackgroundSelectedColor(int backgroundSelectedColor) {
        this.backgroundSelectedColor = backgroundSelectedColor;
        setBgSelector();
    }

    public void setDisableBackGroundColor(int backgroundSelectedColor) {
        this.backgroundSelectedColor = backgroundSelectedColor;
        setBgSelector();
    }

    public int getBackgroundEnableColor() {
        return backgroundEnableColor;
    }

    public void setBackgroundEnableColor(int backgroundEnableColor) {
        this.backgroundEnableColor = backgroundEnableColor;
    }

    public int getStrokeEnableColor() {
        return strokeEnableColor;
    }

    public void setStrokeEnableColor(int strokeEnableColor) {
        this.strokeEnableColor = strokeEnableColor;
    }

    public void setCornerRadius(int cornerRadius) {
        this.cornerRadius = dp2px(cornerRadius);
        setBgSelector();
    }

    public void setStrokeWidthPx(int strokeWidth) {
        this.strokeWidth = strokeWidth;
        setBgSelector();
    }

    public void setStrokeWidth(float strokeWidth) {
        this.strokeWidth = dp2px(strokeWidth);
        setBgSelector();
    }

    public void setStrokeColor(int strokeColor) {
        this.strokeColor = strokeColor;
        setBgSelector();
    }

    public void setStrokePressColor(int strokePressColor) {
        this.strokePressColor = strokePressColor;
        setBgSelector();
    }

    public void setStrokeSelectedColor(int strokeSelectedColor) {
        this.strokeSelectedColor = strokeSelectedColor;
        setBgSelector();
    }

    public void setTextPressColor(int textPressColor) {
        this.textPressColor = textPressColor;
        setBgSelector();
    }

    public void setTextSelectedColor(int textSelectedColor) {
        this.textSelectedColor = textSelectedColor;
        setBgSelector();
    }

    public void setIsRadiusHalfHeight(boolean isRadiusHalfHeight) {
        this.isRadiusHalfHeight = isRadiusHalfHeight;
        setBgSelector();
    }

    public void setIsWidthHeightEqual(boolean isWidthHeightEqual) {
        this.isWidthHeightEqual = isWidthHeightEqual;
        setBgSelector();
    }

    public void setCornerRadius_TL(int cornerRadius_TL) {
        this.cornerRadius_TL = cornerRadius_TL;
        setBgSelector();
    }

    public void setCornerRadius_TR(int cornerRadius_TR) {
        this.cornerRadius_TR = cornerRadius_TR;
        setBgSelector();
    }

    public void setCornerRadius_BL(int cornerRadius_BL) {
        this.cornerRadius_BL = cornerRadius_BL;
        setBgSelector();
    }

    public void setCornerRadius_BR(int cornerRadius_BR) {
        this.cornerRadius_BR = cornerRadius_BR;
        setBgSelector();
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public int getBackgroundPressColor() {
        return backgroundPressColor;
    }

    public int getBackgroundSelectedColor() {
        return backgroundSelectedColor;
    }

    public int getCornerRadius() {
        return cornerRadius;
    }

    public int getStrokeWidth() {
        return strokeWidth;
    }

    public int getStrokeColor() {
        return strokeColor;
    }

    public int getStrokePressColor() {
        return strokePressColor;
    }

    public int getStrokeSelectedColor() {
        return strokeSelectedColor;
    }

    public int getTextPressColor() {
        return textPressColor;
    }

    public int getTextSelectedColor() {
        return textSelectedColor;
    }

    public boolean isRadiusHalfHeight() {
        return isRadiusHalfHeight;
    }

    public boolean isWidthHeightEqual() {
        return isWidthHeightEqual;
    }

    public int getCornerRadius_TL() {
        return cornerRadius_TL;
    }

    public int getCornerRadius_TR() {
        return cornerRadius_TR;
    }

    public int getCornerRadius_BL() {
        return cornerRadius_BL;
    }

    public int getCornerRadius_BR() {
        return cornerRadius_BR;
    }

    protected int dp2px(float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    protected int sp2px(float sp) {
        final float scale = this.context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (sp * scale + 0.5f);
    }

    private void setDrawable(GradientDrawable gd, int color, int strokeColor) {
        gd.setColor(color);

        if (cornerRadius_TL > 0 || cornerRadius_TR > 0 || cornerRadius_BR > 0 || cornerRadius_BL > 0) {
            /**The corners are ordered top-left, top-right, bottom-right, bottom-left*/
            radiusArr[0] = cornerRadius_TL;
            radiusArr[1] = cornerRadius_TL;
            radiusArr[2] = cornerRadius_TR;
            radiusArr[3] = cornerRadius_TR;
            radiusArr[4] = cornerRadius_BR;
            radiusArr[5] = cornerRadius_BR;
            radiusArr[6] = cornerRadius_BL;
            radiusArr[7] = cornerRadius_BL;
            gd.setCornerRadii(radiusArr);
        } else {
            gd.setCornerRadius(cornerRadius);
        }

        gd.setStroke(strokeWidth, strokeColor);
    }

    public void setBgSelector() {
        StateListDrawable bg = new StateListDrawable();
        if (backgroundPressColor != Integer.MAX_VALUE || strokePressColor != Integer.MAX_VALUE) {
            setDrawable(gd_background_press, backgroundPressColor == Integer.MAX_VALUE ? backgroundColor : backgroundPressColor,
                    strokePressColor == Integer.MAX_VALUE ? strokeColor : strokePressColor);
            bg.addState(new int[]{android.R.attr.state_pressed}, gd_background_press);
        }
        if (backgroundSelectedColor != Integer.MAX_VALUE || strokeSelectedColor != Integer.MAX_VALUE) {
            setDrawable(gd_background_selected, backgroundSelectedColor == Integer.MAX_VALUE ? backgroundColor : backgroundSelectedColor,
                    strokeSelectedColor == Integer.MAX_VALUE ? strokeColor : strokeSelectedColor);
            bg.addState(new int[]{android.R.attr.state_selected}, gd_background_selected);
        }
        if (backgroundEnableColor != Integer.MAX_VALUE || strokeEnableColor != Integer.MAX_VALUE) {
            setDrawable(gd_background_enable, backgroundEnableColor == Integer.MAX_VALUE ? backgroundColor : backgroundEnableColor,
                    strokeEnableColor == Integer.MAX_VALUE ? strokeColor : strokeEnableColor);
            bg.addState(new int[]{android.R.attr.state_enabled}, gd_background_enable);
        }

        setDrawable(gd_background, backgroundColor, strokeColor);
        bg.addState(new int[]{}, gd_background);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {//16
            view.setBackground(bg);
        } else {
            //noinspection deprecation
            view.setBackgroundDrawable(bg);
        }

        if (view instanceof TextView) {
            ColorStateList textColors = ((TextView) view).getTextColors();
//              Log.d("AAA", textColors.getColorForState(new int[]{-android.R.attr.state_pressed}, -1) + "");
            ColorStateList colorStateList = new ColorStateList(
                    new int[][]{
                            new int[]{android.R.attr.state_pressed},
                            new int[]{android.R.attr.state_selected},
                            new int[]{android.R.attr.state_enabled},
                            new int[]{}},
                    new int[]{
                            textPressColor == Integer.MAX_VALUE ? textColors.getDefaultColor() : textPressColor,
                            textSelectedColor == Integer.MAX_VALUE ? textColors.getDefaultColor() : textSelectedColor,
                            textEnableColor == Integer.MAX_VALUE ? textColors.getDefaultColor() : textEnableColor,
                            textColors.getDefaultColor(),
                    });
            ((TextView) view).setTextColor(colorStateList);
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private ColorStateList getPressedColorSelector(int normalColor, int pressedColor, int backgroundSelectedColor) {
        return new ColorStateList(
                new int[][]{
                        new int[]{android.R.attr.state_pressed},
                        new int[]{android.R.attr.state_selected},
                        new int[]{android.R.attr.state_focused},
                        new int[]{android.R.attr.state_activated},
                        new int[]{}
                },
                new int[]{
                        pressedColor,
                        backgroundSelectedColor,
                        pressedColor,
                        pressedColor,
                        normalColor
                }
        );
    }
}

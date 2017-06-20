package com.zjsx.blocklayout.widget.roundview;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;

/** 用于需要圆角矩形框背景的TextView的情况,减少直接使用TextView时引入的shape资源文件 */
public class RoundEditText extends AppCompatEditText {
    private RoundViewDelegate delegate;

    public RoundEditText(Context context) {
        this(context, null);
    }

    public RoundEditText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        delegate = new RoundViewDelegate(this, context, attrs);
        setFocusable(true);
        setFocusableInTouchMode(true);
    }

    /** use delegate to set attr */
    public RoundViewDelegate getDelegate() {
        return delegate;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (delegate.isWidthHeightEqual() && getWidth() > 0 && getHeight() > 0) {
            int max = Math.max(getWidth(), getHeight());
            int measureSpec = MeasureSpec.makeMeasureSpec(max, MeasureSpec.EXACTLY);
            super.onMeasure(measureSpec, measureSpec);
            return;
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (delegate.isRadiusHalfHeight()) {
            delegate.setCornerRadius(getHeight() / 2);
        } else {
            delegate.setBgSelector();
        }
    }
}

package com.zjsx.blocklayout.widget.banner;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class HorizontalViewPager extends BaseViewPager {
    private static final String TAG = HorizontalViewPager.class.getName();

    public HorizontalViewPager(Context context) {
        this(context, null);
    }

    public HorizontalViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public MotionEvent swapTouchEvent(MotionEvent event) {
        return event;
    }
}

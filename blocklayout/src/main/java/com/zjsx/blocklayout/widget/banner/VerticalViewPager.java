package com.zjsx.blocklayout.widget.banner;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class VerticalViewPager extends BaseViewPager {
    private static final String TAG = VerticalViewPager.class.getName();

    public VerticalViewPager(Context context) {
        this(context, null);
    }

    public VerticalViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        setPageTransformer(false, new VerticalTransformer());
    }

    @Override
    public MotionEvent swapTouchEvent(MotionEvent event) {
        float width = getWidth();
        float height = getHeight();

        float swappedX = (event.getY() / height) * width;
        float swappedY = (event.getX() / width) * height;

        event.setLocation(swappedX, swappedY);

        return event;
    }
}

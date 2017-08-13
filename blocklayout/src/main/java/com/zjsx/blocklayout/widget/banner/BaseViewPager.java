package com.zjsx.blocklayout.widget.banner;

/**
 * Copyright (C) 2015 Kaelaela
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.IntDef;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Interpolator;

import java.lang.reflect.Field;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public abstract class BaseViewPager extends ViewPager {
    private static final String TAG = BaseViewPager.class.getName();

    boolean autoScroll = false;

    public BaseViewPager(Context context) {
        this(context, null);
    }

    int mScrollState;

    public int direction = 1;

    public BaseViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        postInitViewPager();
        addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrollStateChanged(int state) {
                mScrollState = state;
                if (state == SCROLL_STATE_DRAGGING) {
                    pauseScroll();
                } else if (state == SCROLL_STATE_IDLE) {
                    goOnScroll();
                }
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }
        });
    }

    public abstract MotionEvent swapTouchEvent(MotionEvent event);

    boolean manual = true;

    public void setManual(boolean manual) {
        this.manual = manual;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (manual) {
            boolean intercept = super.onInterceptTouchEvent(swapTouchEvent(event));
            swapTouchEvent(event);
            return intercept;
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (manual) {
            return super.onTouchEvent(swapTouchEvent(ev));
        }
        return false;
    }

    private ScrollerCustomDuration mScroller = null;
    private Interpolator mInterpolator = new Interpolator() {
        @Override
        public float getInterpolation(float t) {
            t -= 1.0f;
            return t * t * t * t * t + 1.0f;
        }
    };

    /**
     * Override the Scroller instance with our own class so we can change the
     * duration
     */
    private void postInitViewPager() {
        try {
            Class<?> viewpager = ViewPager.class;
            Field scroller = viewpager.getDeclaredField("mScroller");
            scroller.setAccessible(true);
            Field interpolator = viewpager.getDeclaredField("sInterpolator");
            interpolator.setAccessible(true);

            mScroller = new ScrollerCustomDuration(getContext(),mInterpolator);
            scroller.set(this, mScroller);
        } catch (Exception e) {
        }
    }

    int mDuration = 1000;
    int mDelay = 1000;

    /**
     * Set the factor by which the duration will change
     */
    public void setScrollDuration(int duration) {
        mDuration = duration;
        mScroller.setDuration(duration);
    }

    public void setScrollDelay(int delay) {
        mDelay = delay;
    }

    public void autoScroll() {
        autoScroll = true;
        goOnScroll();
    }

    public void stopScroll() {
        autoScroll = false;
        pauseScroll();
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (autoScroll) {
                int nextItem;
                if (getCurrentItem() == 0 && direction < 0) {
                    nextItem = getAdapter().getCount();
                } else if (getCurrentItem() == getAdapter().getCount() - 1 && direction > 0) {
                    nextItem = 0;
                } else {
                    nextItem = getCurrentItem() + direction;
                }
                setCurrentItem(nextItem);
            }
        }
    };
    /**
     * 单线程池定时任务
     */
    private ScheduledExecutorService mStse;

    /**
     * 继续滚动(for LoopViewPager)
     */
    public void goOnScroll() {
        pauseScroll();
        if (autoScroll) {
            mStse = Executors.newSingleThreadScheduledExecutor();
            mStse.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                    mHandler.obtainMessage(0).sendToTarget();
                }
            }, mDelay, mDuration + mDelay, TimeUnit.MILLISECONDS);
        }
    }

    /**
     * 停止滚动(for LoopViewPager)
     */
    public void pauseScroll() {
        if (mStse != null) {
            mStse.shutdown();
            mStse = null;
        }
        mHandler.removeMessages(0);
    }

    @Override
    protected void onAttachedToWindow() {
        goOnScroll();
        super.onAttachedToWindow();
    }

    @Override
    protected void onDetachedFromWindow() {
        setCurrentItem(getCurrentItem());
        pauseScroll();
        super.onDetachedFromWindow();
    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        if (visibility == GONE) {
            pauseScroll();
        } else {
            goOnScroll();
        }
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        if (Math.abs(direction) != 1) {
            throw new RuntimeException("direction must be 1 or -1!");
        }
        this.direction = direction;
    }
}

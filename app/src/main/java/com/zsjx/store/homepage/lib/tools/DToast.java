package com.zsjx.store.homepage.lib.tools;


import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Toast统一管理类
 *
 * @author eddie.zhang
 */
public class DToast {
    // Toast
    private static Toast toast;
    private static Context mContext;
    private static Handler handler;

    public static void init(Context context) {
        mContext = context;
        handler = new Handler();
    }

    private static void show(Runnable runnable) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            runnable.run();
        } else if (handler != null) {
            handler.post(runnable);
        }
    }

    public static void show(final CharSequence msg, final int length) {
        show(new Runnable() {
            @Override
            public void run() {
                hideToast();
                toast = Toast.makeText(mContext, msg, length);
                toast.show();
            }
        });
    }

    public static void show(final int resId, final int length) {
        show(new Runnable() {
            @Override
            public void run() {
                hideToast();
                toast = Toast.makeText(mContext, resId, length);
                toast.show();
            }
        });
    }

    public static void showShort(CharSequence message) {
        show(message, Toast.LENGTH_SHORT);
    }

    public static void showShort(int message) {
        show(message, Toast.LENGTH_SHORT);
    }

    public static void showLong(CharSequence message) {
        show(message, Toast.LENGTH_LONG);
    }

    public static void showLong(int message) {
        show(message, Toast.LENGTH_LONG);
    }


    public static void center(final int message, final int length) {
        show(new Runnable() {
            @Override
            public void run() {
                hideToast();
                toast = Toast.makeText(mContext, message, length);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        });
    }

    public static void center(final String message, final int length) {
        show(new Runnable() {
            @Override
            public void run() {
                hideToast();
                toast = Toast.makeText(mContext, message, length);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        });
    }

    public static void centerShort(int message) {
        center(message, Toast.LENGTH_SHORT);
    }

    public static void centerShort(String message) {
        center(message, Toast.LENGTH_SHORT);
    }

    public static void centerLong(int message) {
        center(message, Toast.LENGTH_LONG);
    }

    public static void centerLong(String message) {
        center(message, Toast.LENGTH_LONG);
    }


    public static void debugShort(String message, int color) {
        if (StrUtil.isEmpty(message)) return;
        debug(message, color, Toast.LENGTH_LONG);
    }

    public static void debug(final String message, final int color, final int duration) {
        show(new Runnable() {
            @Override
            public void run() {
                hideToast();
                toast = new Toast(mContext);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.setDuration(duration);
                TextView tv = new TextView(mContext);
                tv.setText(message);
                tv.setGravity(Gravity.CENTER);
                tv.setTextColor(Color.WHITE);
                tv.setPadding(10, 10, 10, 10);
                tv.setTextColor(Color.WHITE);
                tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                tv.setBackgroundColor(color);
                toast.setView(tv);
                toast.show();
            }
        });
    }

    public static void hideToast() {
        if (null != toast) {
            toast.cancel();
        }
    }
}

package com.zsjx.store.homepage.tools;

import com.zjsx.blocklayout.tools.BlockUtil;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class TimeUtil {

    public static String getTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        return str;
    }

    public static String getPlayTime(long time) {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));
        String totalTime = formatter.format(time);
        return totalTime;
    }


    public static String getEasyTime(long time) {
        if (time <= 0) {
            return null;
        }
        long currentSeconds = System.currentTimeMillis();
        long timeGap = currentSeconds - time;// 与现在时间差
        String timeStr = null;
        if (timeGap > 24 * 3 * 60 * 60 * 1000) {
            timeStr = getMiniTime(time);
        } else if (timeGap > 24 * 2 * 60 * 60 * 1000) {
            timeStr = "前天 " + getFormatTime(time, "HH点mm分ss秒");
        } else if (timeGap > 24 * 60 * 60 * 1000) {
            timeStr = "昨天 " + getFormatTime(time, "HH点mm分ss秒");
        } else {
            timeStr = "今天 " + getFormatTime(time, "HH点mm分ss秒");
        }
        return timeStr;
    }

    public static String getEasyWeekTime(long time) {
        if (time <= 0) {
            return null;
        }
        long currentSeconds = System.currentTimeMillis();
        long timeGap = currentSeconds - time;// 与现在时间差
        String timeStr = null;
        if (timeGap > 24 * 3 * 60 * 60 * 1000) {
            timeStr = getMiniTime(time);
        } else if (timeGap > 24 * 2 * 60 * 60 * 1000) {
            timeStr = "前天 " + getFormatTime(time, "HH点mm分ss秒");
        } else if (timeGap > 24 * 60 * 60 * 1000) {
            timeStr = "昨天 " + getFormatTime(time, "HH点mm分ss秒");
        } else {
            timeStr = "今天 " + getFormatTime(time, "HH点mm分ss秒");
        }
        return timeStr;
    }

    /**
     * 根据日期获得星期
     *
     * @param time
     * @return
     */
    public static String getWeekOfDate(long time) {
        String[] weekDaysName = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        String[] weekDaysCode = {"0", "1", "2", "3", "4", "5", "6"};
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        int intWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        return weekDaysCode[intWeek];
    }

    public static String getFormatTime(long time, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
        return sdf.format(new Date(time));
    }

    public static String getFormatTime(String formatStr) {
        SimpleDateFormat format = new SimpleDateFormat(formatStr, Locale.getDefault());
        return format.format(new Date().getTime());
    }

    public static String getDay(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return format.format(new Date(time));
    }

    public static String getMiniTime(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return format.format(new Date(time));
    }

    public static long getMilisFromStr(String timeStr, String pattern) {
        long time = 0;
        if (!BlockUtil.isEmpty(timeStr)) {
            SimpleDateFormat format = new SimpleDateFormat(pattern, Locale.getDefault());
            try {
                Date date = format.parse(timeStr);
                time = date.getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return time;
    }

    public static String getDay() {
        return getDay(new Date().getTime());
    }

    public static String getCurTime() {
        return getMiniTime(new Date().getTime());
    }
}

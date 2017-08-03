package com.zsjx.store.homepage.module;

import android.view.ViewGroup;

import com.zjsx.blocklayout.config.BlockManager;
import com.zsjx.store.homepage.holder.CountdownClockHolder;
import com.zjsx.blocklayout.module.Block;
import com.zsjx.store.homepage.tools.TimeUtil;

import java.util.Date;

public class CountdownClock extends Block<CountdownClock> {
    private String textColor;
    private String clockEndTime;
    private String textBackColor;
    private String textSize;

    public String getTextColor() {
        return textColor;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }

    public String getClockEndTime() {
        return clockEndTime;
    }

    public void setClockEndTime(String clockEndTime) {
        this.clockEndTime = clockEndTime;
    }

    public String getTextBackColor() {
        return textBackColor;
    }

    public void setTextBackColor(String textBackColor) {
        this.textBackColor = textBackColor;
    }

    public String getTextSize() {
        return textSize;
    }

    public void setTextSize(String textSize) {
        this.textSize = textSize;
    }

    public boolean showTime() {
        return getTargetTime() - new Date().getTime() > 0;
    }

    public long getTargetTime() {
        try {
            return TimeUtil.getMilisFromStr(clockEndTime, "yyyy-MM-dd HH:mm:ss");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public CountdownClockHolder newHolder(BlockManager config, ViewGroup parent) {
        return new CountdownClockHolder(config, parent);
    }
}

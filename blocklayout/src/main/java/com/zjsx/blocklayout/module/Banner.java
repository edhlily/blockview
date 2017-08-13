package com.zjsx.blocklayout.module;

import java.util.ArrayList;

public abstract class Banner<T extends Banner<T>> extends Block<T> {
    private int duration;
    private int delay;
    private boolean manual;
    private boolean reverse;
    private boolean autoScroll;
    private ArrayList<Block> items;

    public Banner() {
        autoScroll = true;
        manual = true;
    }

    public ArrayList<Block> getItems() {
        return items;
    }

    public void setItems(ArrayList<Block> items) {
        this.items = items;
    }

    public int getRealDelay() {
        return delay < 0 ? 0 : delay;
    }

    public int getRealDuration() {
        return duration <= 0 ? 1000 : duration;
    }

    public long getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public boolean isManual() {
        return manual;
    }

    public void setManual(boolean manual) {
        this.manual = manual;
    }

    public boolean isAutoScroll() {
        return autoScroll;
    }

    public void setAutoScroll(boolean autoScroll) {
        this.autoScroll = autoScroll;
    }

    public boolean isReverse() {
        return reverse;
    }

    public void setReverse(boolean reverse) {
        this.reverse = reverse;
    }
}

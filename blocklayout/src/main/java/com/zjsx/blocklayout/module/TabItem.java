package com.zjsx.blocklayout.module;

import android.view.ViewGroup;

import com.zjsx.blocklayout.config.BlockContext;
import com.zjsx.blocklayout.holder.BlockHolder;

public class TabItem extends Block<TabItem> {
    private int tabIndex;
    private String text;
    private String textColor;
    private String selectedTextColor;
    private String backColor;
    private String selectedBackColor;
    private String backImage;
    private String selectedBackImage;
    private String textSize;
    private Block item;

    public int getTabIndex() {
        return tabIndex;
    }

    public void setTabIndex(int tabIndex) {
        this.tabIndex = tabIndex;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTextColor() {
        return textColor;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }

    public String getSelectedTextColor() {
        return selectedTextColor;
    }

    public void setSelectedTextColor(String selectedTextColor) {
        this.selectedTextColor = selectedTextColor;
    }

    public String getBackColor() {
        return backColor;
    }

    public void setBackColor(String backColor) {
        this.backColor = backColor;
    }

    public String getSelectedBackColor() {
        return selectedBackColor;
    }

    public void setSelectedBackColor(String selectedBackColor) {
        this.selectedBackColor = selectedBackColor;
    }

    public String getBackImage() {
        return backImage;
    }

    public void setBackImage(String backImage) {
        this.backImage = backImage;
    }

    public String getSelectedBackImage() {
        return selectedBackImage;
    }

    public void setSelectedBackImage(String selectedBackImage) {
        this.selectedBackImage = selectedBackImage;
    }

    public String getTextSize() {
        return textSize;
    }

    public void setTextSize(String textSize) {
        this.textSize = textSize;
    }

    public Block getItem() {
        return item;
    }

    public void setItem(Block item) {
        this.item = item;
    }

    @Override
    protected BlockHolder<TabItem> newHolder(BlockContext config, ViewGroup parent) {
        return null;
    }
}

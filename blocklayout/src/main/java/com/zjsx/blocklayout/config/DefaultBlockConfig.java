package com.zjsx.blocklayout.config;

import com.zjsx.blocklayout.module.Block;
import com.zjsx.blocklayout.tools.BlockUtil;

import java.io.Serializable;
import java.util.ArrayList;

public class DefaultBlockConfig implements BlockConfig {
    private String layout;
    private int cellCount;
    private String spacing;
    private String spacingMode;
    private String spacingItem;
    private String backColor;
    private String backImage;
    private ArrayList<Block> modules = new ArrayList<>();

    public String getBackColor() {
        return BlockUtil.isColor(backColor) ? backColor : "#00000000";
    }

    public void setBackColor(String backColor) {
        this.backColor = backColor;
    }

    public String getBackImage() {
        return backImage;
    }

    public void setBackImage(String backImage) {
        this.backImage = backImage;
    }

    public ArrayList<Block> getModules() {
        return modules;
    }

    public void setModules(ArrayList<Block> modules) {
        this.modules = modules;
    }

    public int getCellCount() {
        return cellCount;
    }

    public void setCellCount(int cellCount) {
        this.cellCount = cellCount;
    }

    public String getLayout() {
        return layout;
    }

    public void setLayout(String layout) {
        this.layout = layout;
    }

    public String getSpacing() {
        return spacing;
    }

    public void setSpacing(String spacing) {
        this.spacing = spacing;
    }

    public String getSpacingMode() {
        return spacingMode;
    }

    public void setSpacingMode(String spacingMode) {
        this.spacingMode = spacingMode;
    }

    public String getSpacingItem() {
        return spacingItem;
    }

    public void setSpacingItem(String spacingItem) {
        this.spacingItem = spacingItem;
    }


    @Override
    public String toString() {
        return "IndexManager{" +
                ", backColor='" + backColor + '\'' +
                ", backImage='" + backImage + '\'' +
                ", modules=" + modules +
                '}';
    }
}

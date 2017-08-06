package com.zjsx.blocklayout.module;

import android.view.ViewGroup;
import android.widget.GridLayout;

import com.zjsx.blocklayout.config.BlockContext;
import com.zjsx.blocklayout.holder.BlockHolder;

import java.io.Serializable;
import java.util.Map;
import java.util.UUID;

public abstract class Block<T extends Block<T>> implements Serializable {
    private UUID uuid;
    private String type;
    private String name;
    private String backColor;
    private String backImage;
    private String marginBottom;
    private String marginTop;
    private String marginLeft;
    private String marginRight;
    private String paddingBottom;
    private String paddingTop;
    private String paddingLeft;
    private String paddingRight;
    private String roundTopLeft;
    private String roundTopRight;
    private String roundBottomLeft;
    private String roundBottomRight;
    private String width;
    private String height;
    private int weight;
    private String link;
    private boolean clickable = true;
    private int rowStart;
    private int colStart;
    private int rowSpan;
    private int colSpan;
    private int itemWidth;
    private int itemHeight;
    private String layoutGravity;
    private Map<String,Object> extra;

    public String getLayoutGravity() {
        return layoutGravity;
    }

    public void setLayoutGravity(String layoutGravity) {
        this.layoutGravity = layoutGravity;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public int getItemHeight() {
        return itemHeight;
    }

    public void setItemHeight(int itemHeight) {
        this.itemHeight = itemHeight;
    }

    public int getItemWidth() {
        return itemWidth;
    }

    public void setItemWidth(int itemWidth) {
        this.itemWidth = itemWidth;
    }

    public int getRowStart() {
        return rowStart;
    }

    public void setRowStart(int rowStart) {
        this.rowStart = rowStart;
    }

    public int getColStart() {
        return colStart;
    }

    public void setColStart(int colStart) {
        this.colStart = colStart;
    }

    public void setRowSpan(int rowSpan) {
        this.rowSpan = rowSpan;
    }

    public void setColSpan(int colSpan) {
        this.colSpan = colSpan;
    }

    public int getRowSpan() {
        return rowSpan;
    }

    public int getColSpan() {
        return colSpan;
    }

    public int getRowSpec() {
        return getRowSpan() > 0 ? getRowSpan() : 1;
    }

    public int getColumnSpec() {
        return getColSpan() > 0 ? getColSpan() : 1;
    }

    public int getColumn() {
        return getColStart() > 0 ? getColStart() - 1 : GridLayout.UNDEFINED;
    }

    public int getRow() {
        return getRowStart() > 0 ? getRowStart() - 1 : GridLayout.UNDEFINED;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBackColor() {
        return backColor;
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

    public String getMarginBottom() {
        return marginBottom;
    }

    public void setMarginBottom(String marginBottom) {
        this.marginBottom = marginBottom;
    }

    public String getMarginTop() {
        return marginTop;
    }

    public void setMarginTop(String marginTop) {
        this.marginTop = marginTop;
    }

    public String getMarginLeft() {
        return marginLeft;
    }

    public void setMarginLeft(String marginLeft) {
        this.marginLeft = marginLeft;
    }

    public String getMarginRight() {
        return marginRight;
    }

    public void setMarginRight(String marginRight) {
        this.marginRight = marginRight;
    }

    public String getPaddingBottom() {
        return paddingBottom;
    }

    public void setPaddingBottom(String paddingBottom) {
        this.paddingBottom = paddingBottom;
    }

    public String getPaddingTop() {
        return paddingTop;
    }

    public void setPaddingTop(String paddingTop) {
        this.paddingTop = paddingTop;
    }

    public String getPaddingLeft() {
        return paddingLeft;
    }

    public void setPaddingLeft(String paddingLeft) {
        this.paddingLeft = paddingLeft;
    }

    public String getPaddingRight() {
        return paddingRight;
    }

    public void setPaddingRight(String paddingRight) {
        this.paddingRight = paddingRight;
    }

    public String getRoundTopLeft() {
        return roundTopLeft;
    }

    public void setRoundTopLeft(String roundTopLeft) {
        this.roundTopLeft = roundTopLeft;
    }

    public String getRoundTopRight() {
        return roundTopRight;
    }

    public void setRoundTopRight(String roundTopRight) {
        this.roundTopRight = roundTopRight;
    }

    public String getRoundBottomLeft() {
        return roundBottomLeft;
    }

    public void setRoundBottomLeft(String roundBottomLeft) {
        this.roundBottomLeft = roundBottomLeft;
    }

    public String getRoundBottomRight() {
        return roundBottomRight;
    }

    public void setRoundBottomRight(String roundBottomRight) {
        this.roundBottomRight = roundBottomRight;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public boolean isClickable() {
        return clickable;
    }

    public void setClickable(boolean clickable) {
        this.clickable = clickable;
    }

    public Map<String, Object> getExtra() {
        return extra;
    }

    public void setExtra(Map<String, Object> extra) {
        this.extra = extra;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Block<?> block = (Block<?>) o;

        return uuid.equals(block.uuid);

    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }

    /**
     * 通过block创建该blockType对应的Holder
     *
     * @param config
     * @param parent
     * @param <S>
     * @return
     */
    public final <S extends BlockHolder<T>> S getHolder(BlockContext config, ViewGroup parent) {
        return newHolder(config, parent);
    }

    protected abstract <S extends BlockHolder<T>> S newHolder(BlockContext config, ViewGroup parent);
}

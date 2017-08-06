package com.zjsx.blocklayout.module;

import android.view.ViewGroup;

import com.zjsx.blocklayout.config.BlockContext;
import com.zjsx.blocklayout.holder.TabsHolder;

import java.util.ArrayList;

public class TabBar extends Block<TabBar> {
    private int tabCount;
    private ArrayList<TabItem> tabItems = new ArrayList<>();
    private int selectedTab;

    public int getTabCount() {
        return tabCount;
    }

    public void setTabCount(int tabCount) {
        this.tabCount = tabCount;
    }

    public ArrayList<TabItem> getTabItems() {
        return tabItems;
    }

    public void setTabItems(ArrayList<TabItem> tabItems) {
        this.tabItems = tabItems;
    }

    public int getSelectedTab() {
        return selectedTab;
    }

    public void setSelectedTab(int selectedTab) {
        this.selectedTab = selectedTab;
    }

    @Override
    protected TabsHolder newHolder(BlockContext config, ViewGroup parent) {
        return new TabsHolder(config, parent);
    }

    @Override
    public String toString() {
        return "TabBar{" +
                "tabCount=" + tabCount +
                ", tabItems=" + tabItems +
                ", selectedTab=" + selectedTab +
                '}';
    }
}

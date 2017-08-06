package com.zjsx.blocklayout.holder;

import android.graphics.drawable.StateListDrawable;
import android.support.v7.widget.AppCompatRadioButton;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.zjsx.blocklayout.config.BlockConfig;
import com.zjsx.blocklayout.config.BlockContext;
import com.zjsx.blocklayout.module.Block;
import com.zjsx.blocklayout.module.TabBar;
import com.zjsx.blocklayout.module.TabItem;

public class TabsHolder extends BlockHolder<TabBar> {
    public RadioGroup tabHead;

    public interface ContainerReplacer {
        void replace(int position, Block block);
    }

    ContainerReplacer containerReplacer;

    public TabsHolder(BlockContext blockContext, ViewGroup parent) {
        super(blockContext, new RadioGroup(parent.getContext()), BlockConfig.getInstance().getViewType(TabBar.class));
        tabHead = (RadioGroup) itemView;
        tabHead.setOrientation(RadioGroup.HORIZONTAL);
    }

    @Override
    public void bindData(final TabBar tabBar) {
        if (tabHead.getChildCount() >= tabBar.getTabItems().size()) {
            int removeSize = tabHead.getChildCount() - tabBar.getTabItems().size();
            for (int i = 0; i < removeSize; i++) {
                tabHead.removeViewAt(0);
            }
        } else {
            int addSize = tabBar.getTabItems().size() - tabHead.getChildCount();
            for (int i = 0; i < addSize; i++) {
                RadioButton rb = new AppCompatRadioButton(itemView.getContext());
                rb.setButtonDrawable(new StateListDrawable());
                rb.setGravity(Gravity.CENTER);
                rb.setMaxLines(2);
                RadioGroup.LayoutParams rglp = new RadioGroup.LayoutParams(0, RadioGroup.LayoutParams.MATCH_PARENT, 1);
                tabHead.addView(rb, rglp);
            }
        }
        for (int i = 0; i < tabBar.getTabItems().size(); i++) {
            final RadioButton rb = (RadioButton) tabHead.getChildAt(i);
            final TabItem tabItem = tabBar.getTabItems().get(i);
            if (rb.isChecked()) {
                rb.setBackgroundColor(BlockConfig.getInstance().getBackColor(tabBar.getTabItems().get(i).getSelectedBackColor()));
            } else {
                rb.setBackgroundColor(BlockConfig.getInstance().getBackColor(tabBar.getTabItems().get(i).getBackColor()));
            }
            rb.setTextSize(TypedValue.COMPLEX_UNIT_PX, BlockConfig.getInstance().getSize(tabBar.getTabItems().get(i).getTextSize()));
            BlockConfig.getInstance().setTextColor(rb, tabBar.getTabItems().get(i).getTextColor());
            rb.setText(tabItem.getText());
            rb.setTag(i);
            if (i == tabBar.getSelectedTab()) {
                rb.setChecked(true);
                rb.setBackgroundColor(BlockConfig.getInstance().getBackColor(tabItem.getSelectedBackColor()));
                BlockConfig.getInstance().setTextColor(rb, tabItem.getSelectedTextColor());
            }
            rb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        buttonView.setBackgroundColor(BlockConfig.getInstance().getBackColor(tabItem.getSelectedBackColor()));
                        BlockConfig.getInstance().setTextColor(buttonView, tabItem.getSelectedTextColor());
                        tabBar.setSelectedTab((int) buttonView.getTag());
                        tabBar.getTabItems().get(tabBar.getSelectedTab()).getItem().setName(tabBar.getName());
                        //containerReplacer.replace(position + 1, tabBar.getTabItems().get(tabBar.getSelectedTab()).getContainer());
                    } else {
                        buttonView.setBackgroundColor(BlockConfig.getInstance().getBackColor(tabItem.getBackColor()));
                        BlockConfig.getInstance().setTextColor(buttonView, tabItem.getTextColor());
                    }

                }
            });
        }
    }
}

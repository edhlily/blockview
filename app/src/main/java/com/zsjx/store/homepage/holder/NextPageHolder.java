package com.zsjx.store.homepage.holder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zjsx.blocklayout.config.BlockConfig;
import com.zjsx.blocklayout.config.BlockContext;
import com.zjsx.blocklayout.holder.BlockHolder;
import com.zsjx.store.homepage.R;
import com.zsjx.store.homepage.module.NextPageItem;


public class NextPageHolder extends BlockHolder<NextPageItem> {
    TextView nextPageText;
    ProgressBar nextPageProgress;

    public NextPageHolder(BlockContext config, ViewGroup parent) {
        super(config, LayoutInflater.from(parent.getContext()).inflate(R.layout.index_template_next_page, null, false), BlockConfig.getInstance().getViewType(NextPageItem.class));
        nextPageText = (TextView) itemView.findViewById(R.id.nextPageText);
        nextPageProgress = (ProgressBar) itemView.findViewById(R.id.nextPageProgress);
    }

    public void bindData(NextPageItem pageBar) {
        nextPageText.setText(pageBar.getText());
        nextPageProgress.setVisibility(pageBar.getStatus() == NextPageItem.STATUS_LOADING ? View.VISIBLE : View.GONE);
    }
}
package com.zsjx.store.homepage.holder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zjsx.blocklayout.config.BlockManager;
import com.zjsx.blocklayout.holder.BlockHolder;
import com.zjsx.blocklayout.widget.CountdownView;
import com.zsjx.store.homepage.R;
import com.zsjx.store.homepage.module.CountdownClock;

import java.util.Date;

public class CountdownClockHolder extends BlockHolder<CountdownClock> {
    private CountdownView mCountdownView;

    public CountdownClockHolder(BlockManager config, ViewGroup parent) {
        super(config, LayoutInflater.from(parent.getContext()).inflate(R.layout.index_template_clock, null, false), config.getViewType(CountdownClock.class));
        mCountdownView = (CountdownView) itemView.findViewById(R.id.barClock);
    }

    @Override
    public void bindData(final CountdownClock barClock) {
        if (barClock != null && barClock.showTime()) {
            mCountdownView.setVisibility(View.VISIBLE);
            if (barClock.getTargetTime() - new Date().getTime() < 24 * 60 * 60 * 1000) {
                mCountdownView.setShowDay(false);
            }
            mCountdownView.setTimeBgColor(BlockManager.getBackColor(barClock.getTextBackColor()));
            mCountdownView.setTimeTextColor(BlockManager.getBackColor(barClock.getTextColor()));
            float fontSize = BlockManager.getFontPx(itemView.getContext(), barClock.getTextSize());
            if (fontSize != 0) {
                mCountdownView.setTimeTextSize(fontSize);
            }
            mCountdownView.start(barClock.getTargetTime());
            mCountdownView.setOnCountdownEndListener(new CountdownView.OnCountdownEndListener() {
                @Override
                public void onEnd(CountdownView cv) {
                    mCountdownView.setVisibility(View.GONE);
                }
            });
        } else {
            mCountdownView.setVisibility(View.GONE);
        }
    }
}
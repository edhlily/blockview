package com.zsjx.store.homepage.holder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zjsx.blocklayout.config.BlockConfig;
import com.zjsx.blocklayout.config.BlockContext;
import com.zjsx.blocklayout.holder.BlockHolder;
import com.zsjx.store.homepage.widget.CountdownView;
import com.zsjx.store.homepage.R;
import com.zsjx.store.homepage.module.CountdownClock;

import java.util.Date;

public class CountdownClockHolder extends BlockHolder<CountdownClock> {
    private CountdownView mCountdownView;

    public CountdownClockHolder(BlockContext config, ViewGroup parent) {
        super(config, LayoutInflater.from(parent.getContext()).inflate(R.layout.index_template_clock, null, false), BlockConfig.getInstance().getViewType(CountdownClock.class));
        mCountdownView = (CountdownView) itemView.findViewById(R.id.barClock);
    }

    @Override
    public void bindData(final CountdownClock barClock) {
        if (barClock != null && barClock.showTime()) {
            mCountdownView.setVisibility(View.VISIBLE);
            if (barClock.getTargetTime() - new Date().getTime() < 24 * 60 * 60 * 1000) {
                mCountdownView.setShowDay(false);
            }
            mCountdownView.setTimeBgColor(BlockConfig.getInstance().getBackColor(barClock.getTextBackColor()));
            mCountdownView.setTimeTextColor(BlockConfig.getInstance().getBackColor(barClock.getTextColor()));
            float fontSize = BlockConfig.getInstance().getSize(barClock.getTextSize());
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
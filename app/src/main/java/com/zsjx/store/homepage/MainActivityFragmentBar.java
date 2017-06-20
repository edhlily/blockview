package com.zsjx.store.homepage;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.zjsx.blocklayout.tools.BlockUtil;
import com.zjsx.blocklayout.widget.roundview.RoundEditText;

public class MainActivityFragmentBar extends RecyclerView.OnScrollListener {
    MainActivityFragment mainFragment;
    Drawable searchDrawableWhite, searchDrawableGray;
    View appBarShadow, llBarLeft, llBarRight;
    RelativeLayout indexActionBar;
    View viewTop;
    RoundEditText etSearch;
    ImageView ivScan, ivLogin;

    public MainActivityFragmentBar(MainActivityFragment mainFragment) {
        this.mainFragment = mainFragment;
        indexActionBar = (RelativeLayout) mainFragment.getActivity().findViewById(R.id.titleBar);
        viewTop = mainFragment.getActivity().findViewById(R.id.viewTop);
        etSearch = (RoundEditText) mainFragment.getActivity().findViewById(R.id.etSearch);
        etSearch.setFocusable(false);
        llBarLeft = mainFragment.getActivity().findViewById(R.id.llBarLeft);
        llBarRight = mainFragment.getActivity().findViewById(R.id.llBarRight);
        appBarShadow = mainFragment.getActivity().findViewById(R.id.appBarShadow);
        ivScan = (ImageView) mainFragment.getActivity().findViewById(R.id.ivBarLeftBtn);
        ivLogin = (ImageView) mainFragment.getActivity().findViewById(R.id.ivBarRightBtn);
        etSearch.setOnClickListener(mClickListener);
        llBarRight.setOnClickListener(mClickListener);
        llBarLeft.setOnClickListener(mClickListener);
        searchDrawableWhite = ResourcesCompat.getDrawable(mainFragment.getResources(), R.mipmap.icon_search_1, null);
        searchDrawableWhite.setBounds(0, 0, searchDrawableWhite.getMinimumWidth(), searchDrawableWhite.getMinimumHeight());
        searchDrawableGray = ResourcesCompat.getDrawable(mainFragment.getResources(), R.mipmap.icon_search_2, null);
        searchDrawableGray.setBounds(0, 0, searchDrawableGray.getMinimumWidth(), searchDrawableGray.getMinimumHeight());
        offsetBound = BlockUtil.dip2px(mainFragment.getActivity(), 100);
        etSearch.setText("数码产品5折特卖");
    }

    void setBar() {
        setBarByOffset();
        setLoginButton(false);
        setScanButton();
        setSearchIcon();
        setAppBarShadow();
    }

    float offsetPercent;
    int curLoginTag = -1;

    void setLoginButton(boolean force) {
        if (offsetPercent != curLoginTag || force) {
            curLoginTag = (int) (offsetPercent + 0.5f);
            ivLogin.setImageResource(curLoginTag == 0 ? R.drawable.actionbar_login_1 : R.drawable.actionbar_login_2);
        }
    }

    int curScanTag = -1;

    void setScanButton() {
        if (offsetPercent != curScanTag) {
            curScanTag = (int) (offsetPercent + 0.5);
            ivScan.setImageResource(curScanTag == 0 ? R.drawable.actionbar_scan_1 : R.drawable.actionbar_scan_2);
        }
    }


    int curEtSearchTag = -1;

    void setSearchIcon() {
        if (offsetPercent != curEtSearchTag) {
            curEtSearchTag = (int) (offsetPercent + 0.5);
            if (curEtSearchTag == 0) {
                etSearch.setCompoundDrawables(searchDrawableWhite, null, null, null);
            } else {
                etSearch.setCompoundDrawables(searchDrawableGray, null, null, null);
            }
            ivScan.setImageResource(curEtSearchTag == 0 ? R.drawable.actionbar_scan_1 : R.drawable.actionbar_scan_2);
        }
    }

    void setAppBarShadow() {
        if (offsetPercent >= 0.5f && appBarShadow.getVisibility() != View.VISIBLE) {
            appBarShadow.setVisibility(View.VISIBLE);
        } else if (offsetPercent < 0.5f && appBarShadow.getVisibility() != View.GONE) {
            appBarShadow.setVisibility(View.GONE);
        }
    }

    int alpha;
    int color;

    void setBarByOffset() {
        alpha = (int) (100 + 155f * offsetPercent);
        //ActionBar 透明度 0-200
        alpha = (int) (240f * offsetPercent);
        indexActionBar.setBackgroundColor(Color.argb(alpha, 255, 255, 255));
        //etSearch 透明度 119-170 颜色：255-224
        alpha = (int) (119 + (51f * offsetPercent));
        color = (int) (255 - (32f * offsetPercent));
        etSearch.getDelegate().setBackgroundColor(Color.argb(alpha, color, color, color));
        //etSearch textColor ff-99 255-153
        alpha = 255;
        color = (int) (255 - (102f * offsetPercent));
        etSearch.setTextColor(Color.argb(alpha, color, color, color));
    }

    float offsetBound;
    float offset1;
    float offset;


    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int scrollState) {

    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int i, int i2) {
        offset = offset + i2;
        offset1 = offset < 0 ? 0 : offset > offsetBound ? offsetBound : offset;
        if (offsetPercent == offset1 / offsetBound) return;
        offsetPercent = offset1 / offsetBound;
        setBar();
    }

    View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.etSearch:
                    Toast.makeText(mainFragment.getActivity(), "搜索", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.llBarLeft:
                    mainFragment.loadJinDong();
                    break;
                case R.id.llBarRight:
                    mainFragment.loadTaoBao();
                    break;
            }
        }
    };
}

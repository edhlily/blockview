package com.zsjx.store.homepage;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zjsx.blocklayout.config.BlockContext;
import com.zjsx.blocklayout.module.Block;
import com.zjsx.blocklayout.tools.BlockUtil;
import com.zjsx.blocklayout.widget.BlockView;
import com.zsjx.store.homepage.data.IndexLoader;
import com.zsjx.store.homepage.lib.tools.DToast;
import com.zsjx.store.homepage.lib.tools.Logger;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import rx.Subscriber;

public class MainActivityFragment extends Fragment implements BlockView.BlockClickInterceptor, BlockView.OnBlockClickListener {

    BlockView blockView;
    MainActivityFragmentBar mainFragmentBar;
    IndexLoader indexLoader;

    boolean isLoadingNewIndex;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mainFragmentBar = new MainActivityFragmentBar(this);
        mainFragmentBar.setBar();
        initView();

        String demoConfig = getActivity().getIntent().getStringExtra("demoConfig");

        if (demoConfig == null) {
            demoConfig = "start%2findexconfig.json";
        }

        load(demoConfig);
    }

    void initView() {

        blockView = (BlockView) getActivity().findViewById(R.id.rvMain);
        blockView.setOnBlockClickListener(this);
        blockView.setBlockClickInterceptor(this);
        blockView.addOnScrollListener(mainFragmentBar);
    }

    void loadNewIndex() {
        indexLoader
                .getIndexConfig()
                .subscribe(new NewIndexSubscriber());
    }


    /**
     * 最新首页数据的订阅者
     */
    class NewIndexSubscriber extends Subscriber<IndexBlockDataConfig> {

        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            e.printStackTrace();
            isLoadingNewIndex = false;
            if (e instanceof IOException) {
                DToast.showShort(R.string.error_check_net);
            } else {
                DToast.showShort(R.string.data_error);
            }
        }

        @Override
        public void onNext(IndexBlockDataConfig config) {
            Logger.d("indexConfig:" + config);

            //加载布局文件
            blockView.setConfig(config);
        }
    }

    public void load(String demoConfig) {
        try {
            demoConfig = URLDecoder.decode(demoConfig, "UTF-8");
            indexLoader = new IndexLoader(getActivity(), demoConfig);
            loadNewIndex();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isClickable(Block block) {
        //link不为空才可以点击
        return !BlockUtil.isEmpty(block.getLink());
    }

    @Override
    public void onBlockClick(Block block) {
        String link = block.getLink();

        Uri uri = Uri.parse(link);

        if (uri == null) return;

        if (uri.getScheme().equalsIgnoreCase("window")) {
            if (uri.getHost().equalsIgnoreCase("demo")) {
                Intent intent = new Intent(getActivity(), MainActivity.class);

                for (String paraName : uri.getQueryParameterNames()) {
                    intent.putExtra(paraName, uri.getQueryParameter(paraName));
                }

                startActivity(intent);
            }
        } else if (uri.getScheme().startsWith("http")) {
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        } else if (uri.getScheme().equalsIgnoreCase("toast")) {
            if (uri.getHost() == null) return;
            if (uri.getHost().equalsIgnoreCase("short")) {
                DToast.showShort(uri.getQueryParameter("message"));
            } else {
                DToast.showLong(uri.getQueryParameter("message"));
            }
        }
    }
}
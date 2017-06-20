package com.zsjx.store.homepage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zjsx.blocklayout.config.BlockManager;
import com.zjsx.blocklayout.module.Block;
import com.zjsx.blocklayout.config.BlockConfig;
import com.zjsx.blocklayout.widget.BlockView;
import com.zsjx.store.homepage.data.IndexLoader;
import com.zsjx.store.homepage.lib.tools.DToast;
import com.zsjx.store.homepage.lib.tools.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

public class MainActivityFragment extends Fragment {

    BlockView blockView;
    MainActivityFragmentBar mainFragmentBar;
    BlockManager blockManager;
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
        loadTaoBao();
    }

    void initView() {

        blockView = (BlockView) getActivity().findViewById(R.id.rvMain);
        blockManager = new IndexManager(getActivity());
        blockView.setBlockManager(blockManager);
        blockView.addOnScrollListener(mainFragmentBar);
    }

    void loadNewIndex() {
        indexLoader
                .getIndexConfig(blockManager)
                .subscribe(new NewIndexSubscriber());
    }


    /**
     * 最新首页数据的订阅者
     */
    class NewIndexSubscriber extends Subscriber<IndexBlockConfig> {

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
        public void onNext(IndexBlockConfig config) {
            Logger.d("indexConfig:" + config);

            //加载布局文件
            blockView.setConfig(config);
        }
    }

    public void loadJinDong() {
        indexLoader = new IndexLoader(getActivity(), blockManager, "jd01/indexconfig.json");
        loadNewIndex();

    }

    public void loadTaoBao() {
        indexLoader = new IndexLoader(getActivity(), blockManager, "taobao01/indexconfig.json");
        loadNewIndex();
    }

}
package com.lulu.player.main.free.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.lulu.player.R;
import com.lulu.player.adapter.GridViewAdapter;
import com.lulu.player.common.Constants;
import com.lulu.player.main.free.banner.NetWorkImageHolderView;
import com.lulu.player.main.free.presenter.FreePresenter;
import com.lulu.player.model.Intro;
import com.lulu.player.model.RequestVideo;
import com.lulu.player.model.Video;
import com.lulu.player.mvp.MvpFragment;
import com.lulu.player.video.VideoActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * @author zxc
 * @time 2016/9/26 0026上午 11:39
 */
public class FreeFragment extends MvpFragment<FreePresenter> implements FreeView, com.bigkoo.convenientbanner.listener.OnItemClickListener {

    @Bind(R.id.banner)
    ConvenientBanner banner;

    private GridView freeGV;

    private GridViewAdapter mAdapter;

    // list vedio
    private List<Video> mVideos;

    // banner list
    private List<Video> mBanners;

    public static FreeFragment newInstance(String title, int index) {
        FreeFragment fragment = new FreeFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.TITLE, title);
        bundle.putInt(Constants.INDEX, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_free;
    }

    @Override
    protected void initDatas() {

        mBanners = new ArrayList<>();

        mVideos = new ArrayList<>();
    }

    @Override
    protected void initViews() {

        freeGV = (GridView) view.findViewById(R.id.free_gridView);

    }

    @Override
    protected void initListeners() {
        freeGV.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), VideoActivity.class);
                intent.putExtra(Constants.VIDEO_URL, mVideos.get(position).getVideoUrl());
                startActivity(intent);
            }
        });

    }

    @Override
    protected void initAdapters() {

        mAdapter = new GridViewAdapter(getActivity(), mVideos);
        freeGV.setAdapter(mAdapter);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RequestVideo request = new RequestVideo(2);
        presenter.getVideo(request);
    }

    @Override
    public void onResume() {
        super.onResume();
        banner.startTurning(5000);
    }

    @Override
    public void onPause() {
        super.onPause();
        banner.stopTurning();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void requestVideoList(Intro<List<Video>> intro) {

        mVideos.addAll(intro.getVideos());
        mAdapter.notifyDataSetChanged();
        mBanners.addAll(intro.getTopVideos());
        banner.setPages(new CBViewHolderCreator<NetWorkImageHolderView>() {

            @Override
            public NetWorkImageHolderView createHolder() {
                return new NetWorkImageHolderView();
            }
        }, mBanners)
                .setPointViewVisible(true)
                .setPageIndicator(new int[]{R.drawable.ic_dot_normal, R.drawable.ic_dot_selected})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT)
                .startTurning(5000)
                .setOnItemClickListener(this);

    }

    @Override
    public void requestFail(String msg) {

    }

    @Override
    protected FreePresenter createPresenter() {
        return new FreePresenter(this);
    }

    //banner onItemClick
    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(getActivity(), VideoActivity.class);
        intent.putExtra(Constants.VIDEO_URL, mBanners.get(position).getVideoUrl());
        startActivity(intent);
    }


}

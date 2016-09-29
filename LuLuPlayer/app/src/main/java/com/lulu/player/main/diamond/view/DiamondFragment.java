package com.lulu.player.main.diamond.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.lulu.player.R;
import com.lulu.player.adapter.GridViewAdapter;
import com.lulu.player.common.Constants;
import com.lulu.player.main.diamond.presenter.DiamondPresenter;
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
 * @time 2016/9/26 0026上午 11:16
 */
public class DiamondFragment extends MvpFragment<DiamondPresenter> implements DiamondView {

    @Bind(R.id.diamond_gridView)
    GridView diamondGV;

    private GridViewAdapter diamondAdapter;

    private List<Video> mVideos;

    public static DiamondFragment newInstance(String title, int index) {
        DiamondFragment fragment = new DiamondFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.TITLE, title);
        bundle.putInt(Constants.INDEX, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_diamond;
    }

    @Override
    protected void initDatas() {
        mVideos = new ArrayList<>();
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initListeners() {
        diamondGV.setOnItemClickListener(new OnItemClickListener() {

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
        diamondAdapter = new GridViewAdapter(getActivity(), mVideos);
        diamondGV.setAdapter(diamondAdapter);

    }

    @Override
    public void onResume() {
        super.onResume();
        RequestVideo requestVideo = new RequestVideo(2);
        presenter.getVideo(requestVideo);
    }


    @Override
    public void requestVideoList(Intro<List<Video>> intro) {
        mVideos.addAll(intro.getVideos());
        diamondAdapter.notifyDataSetChanged();
    }

    @Override
    public void requestFail(String msg) {

    }

    @Override
    protected DiamondPresenter createPresenter() {
        return new DiamondPresenter(this);
    }
}

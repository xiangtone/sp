package com.lulu.player.main.gold.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.lulu.player.R;
import com.lulu.player.adapter.GridViewAdapter;
import com.lulu.player.common.Constants;
import com.lulu.player.main.gold.presenter.GoldPresenter;
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
 * @time 2016/9/26 0026上午 11:15
 */
public class GoldFragment extends MvpFragment<GoldPresenter> implements GoldView {

    @Bind(R.id.gold_gridView)
    GridView goldGV;

    private GridViewAdapter goldAdapter;

    private List<Video> goldVideos;

    public static GoldFragment newInstance(String title, int index) {
        GoldFragment fragment = new GoldFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.TITLE, title);
        bundle.putInt(Constants.INDEX, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_gold;
    }

    @Override
    protected void initDatas() {
        goldVideos = new ArrayList<>();
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initListeners() {
        goldGV.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), VideoActivity.class);
                intent.putExtra(Constants.VIDEO_URL, goldVideos.get(position).getVideoUrl());
                startActivity(intent);
            }
        });

    }

    @Override
    protected void initAdapters() {
        goldAdapter = new GridViewAdapter(getActivity(), goldVideos);
        goldGV.setAdapter(goldAdapter);
    }

    //null point
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        //0 体验 1 黄金 2 钻石
//        RequestVideo requestVideo = new RequestVideo(1);
//        presenter.getVideo(requestVideo);
//    }


    @Override
    public void onResume() {
        super.onResume();
        RequestVideo requestVideo = new RequestVideo(2);
        presenter.getVideo(requestVideo);
    }

    @Override
    public void requestVideoList(Intro<List<Video>> intro) {
        goldVideos.addAll(intro.getVideos());
        goldAdapter.notifyDataSetChanged();
    }

    @Override
    public void requestFail(String msg) {

    }

    @Override
    protected GoldPresenter createPresenter() {
        return new GoldPresenter(this);
    }
}

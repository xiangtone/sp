package com.lulu.player.main.my.view;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.lulu.player.R;
import com.lulu.player.common.Constants;
import com.lulu.player.main.my.presenter.MyPresenter;
import com.lulu.player.model.Levels;
import com.lulu.player.model.RequestUserInfo;
import com.lulu.player.model.UserInfo;
import com.lulu.player.mvp.MvpFragment;
import com.lulu.player.utils.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * @author Administrator
 * @time 2016/9/23 0023 上午 11:25
 */
public class MyFragment extends MvpFragment<MyPresenter> implements MyView {

    @Bind(R.id.my_id_text)
    TextView user;

    @Bind(R.id.my_password_text)
    TextView password;

    @Bind(R.id.my_xianshi)
    TextView show;

    @Bind(R.id.my_level_text)
    TextView level;

    @Bind(R.id.my_special_text)
    TextView special;

    @Bind(R.id.my_upgrade_text)
    TextView upgrade;

    private String user_name, password_text, level_text, special_text;

    private String IMSI, IMEI, mac, androidVersion, androidLevel, model;

    private List<Levels> mLevels;

    public static MyFragment newInstance(String title, int index) {
        MyFragment fragment = new MyFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.TITLE, title);
        bundle.putInt(Constants.INDEX, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_my;
    }

    @Override
    protected void initDatas() {

        mLevels = new ArrayList<>();

        TelephonyManager manager = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
        WifiManager wifiMng = (WifiManager) getActivity().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfor = wifiMng.getConnectionInfo();

        IMSI = "" + manager.getSubscriberId();
        mac = "" + wifiInfor.getMacAddress();
        androidVersion = "" + android.os.Build.VERSION.SDK;
        androidLevel = "" + android.os.Build.VERSION.RELEASE;
        model = "" + android.os.Build.MODEL;
        IMEI = "" + manager.getDeviceId();

        user_name = "" + SharedPreferencesUtil.getInstance(getActivity()).getString(Constants.USER_NAME);
        user.setText(user_name);

        password_text = "" + SharedPreferencesUtil.getInstance(getActivity()).getString(Constants.PASSWORD);
        password.setText("******");

    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initListeners() {
        show.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                show.setVisibility(View.GONE);
                password.setText(password_text);
            }
        });
        upgrade.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                level_text = getResources().getString(R.string.my_level_gold);
                level.setText(level_text);
                special_text = getResources().getString(R.string.my_special_gold);
                special.setText(special_text);
            }
        });
    }

    @Override
    protected void initAdapters() {

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RequestUserInfo info = new RequestUserInfo(IMSI, IMEI, mac, androidVersion, androidLevel, model);
        presenter.getLevels(info);
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    protected MyPresenter createPresenter() {
        return new MyPresenter(this);
    }

    @Override
    public void requestUserInfo(UserInfo<List<Levels>> info) {
        mLevels.addAll(info.getLevels());
        level_text = "" + SharedPreferencesUtil.getInstance(getActivity()).getInt(Constants.LEVEL);

        switch (level_text) {
            case "0":
                level.setText(mLevels.get(0).getRemark());
                break;
            case "1":
                level.setText(mLevels.get(1).getRemark());
                break;
            case "2":
                level.setText(mLevels.get(2).getRemark());
                break;

        }
    }

    @Override
    public void requestFail(String msg) {

    }
}

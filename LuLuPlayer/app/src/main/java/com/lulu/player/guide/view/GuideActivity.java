package com.lulu.player.guide.view;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;

import com.lulu.player.R;
import com.lulu.player.common.Constants;
import com.lulu.player.guide.presenter.GuidePresenter;
import com.lulu.player.main.MainActivity;
import com.lulu.player.model.RequestUserInfo;
import com.lulu.player.model.UserInfo;
import com.lulu.player.mvp.MvpActivity;
import com.lulu.player.utils.SharedPreferencesUtil;
import com.lulu.player.utils.ToastUtils;


/**
 * welcome activity ,request user info
 *
 * @author zxc
 * @time 2016/9/22 0022 下午 5:50
 */
public class GuideActivity extends MvpActivity<GuidePresenter> implements GuideView {

    private String IMSI, IMEI, mac, androidVersion, androidLevel, model;

    @Override
    protected int getFragmentLayout() {
        return R.layout.activity_guide;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initListeners() {

    }

    @Override
    protected void initAdapters() {

    }

    @Override
    protected void initDatas() {

        TelephonyManager manager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        WifiManager wifiMng = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfor = wifiMng.getConnectionInfo();

        IMSI = "" + manager.getSubscriberId();
        mac = "" + wifiInfor.getMacAddress();
        androidVersion = "" + android.os.Build.VERSION.SDK;
        androidLevel = "" + android.os.Build.VERSION.RELEASE;
        model = "" + android.os.Build.MODEL;
        IMEI = "" + manager.getDeviceId();

    }

    @Override
    protected void onResume() {
        super.onResume();
        RequestUserInfo info = new RequestUserInfo(IMSI, IMEI, mac, androidVersion, androidLevel, model);
        presenter.getInfo(info);
//        Intent intent = new Intent(GuideActivity.this, MainActivity.class);
//        startActivity(intent);
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    @Override
    protected GuidePresenter createPresenter() {
        return new GuidePresenter(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void requestUserInfo(UserInfo user) {
        ToastUtils.showShortMessage(this, "加载完成...");
        SharedPreferencesUtil.getInstance(this).save(Constants.USER_NAME, user.getName());
        SharedPreferencesUtil.getInstance(this).save(Constants.PASSWORD, user.getPassword());
        SharedPreferencesUtil.getInstance(this).save(Constants.LEVEL, user.getLevel());
        Intent intent = new Intent(GuideActivity.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void requestFail(String msg) {
        ToastUtils.showShortMessage(this, msg);
    }

    @Override
    public void showProgress() {
        ToastUtils.showShortMessage(this, "加载中...");
    }

    @Override
    public void hideProgress() {
        ToastUtils.showShortMessage(this, "加载完成...");
    }
}

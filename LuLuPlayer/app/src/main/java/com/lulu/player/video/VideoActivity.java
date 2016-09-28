package com.lulu.player.video;

import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.MediaController;
import android.widget.Toast;

import com.lulu.player.R;
import com.lulu.player.common.Constants;
import com.lulu.player.view.FullVideoView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author zxc
 * @time 2016/9/26 0026下午 5:59
 */
public class VideoActivity extends AppCompatActivity implements MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener {

    public static final String KEY_URL = "view_url";

    private static final int PLAY_RETURN = 2 * 1000; // 2 seconds
    private static final String KEY_PLAY_POSITION = "play_position";
    private static final String TOAST_ERROR_URL = "Play url is null, please check parameter:" + KEY_URL;
    private static final String TOAST_ERROR_PLAY = "Play error, please check url exist!";
    private static final String DIALOG_TITLE = "奋力加载中，请稍后...";

    private static String url;

    private ProgressDialog progressDialog;

    private MediaController mc;

    @Bind(R.id.videoView)
    FullVideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);

        url = getIntent().getStringExtra(Constants.VIDEO_URL);
        if (url == null && savedInstanceState != null) {
            url = savedInstanceState.getString(KEY_URL);
        }

        if (url == null) {
            Toast.makeText(getApplicationContext(), TOAST_ERROR_URL, Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        videoView.setVideoURI(Uri.parse(url));
        videoView.requestFocus();
        videoView.setOnPreparedListener(this);
        videoView.setOnErrorListener(this);

        mc = new MediaController(this);
        mc.setAnchorView(videoView);
        mc.setKeepScreenOn(true);

        videoView.setMediaController(mc);

        initDialog();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        int playPosition = videoView.getCurrentPosition();
        if (playPosition > PLAY_RETURN) {
            playPosition -= PLAY_RETURN;
        }
        outState.putInt(KEY_PLAY_POSITION, playPosition);
        outState.putString(KEY_URL, url);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        videoView.seekTo(savedInstanceState.getInt(KEY_PLAY_POSITION));
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        progressDialog.cancel();

        videoView.start();
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        Toast.makeText(getApplicationContext(), TOAST_ERROR_PLAY + "\n" + url, Toast.LENGTH_LONG).show();
        progressDialog.cancel();
        finish();

        return true;
    }

    private void initDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage(DIALOG_TITLE);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }
}

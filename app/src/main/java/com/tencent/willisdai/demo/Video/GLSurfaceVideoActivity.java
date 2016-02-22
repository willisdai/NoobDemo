package com.tencent.willisdai.demo.Video;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;

import com.tencent.willisdai.demo.R;

public class GLSurfaceVideoActivity extends Activity implements View.OnClickListener {

    private GLSurfaceVideoView mVideoView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glsurface_video);

        mVideoView1 = (GLSurfaceVideoView) findViewById(R.id.video);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        ViewGroup.LayoutParams lp = mVideoView1.getLayoutParams();
        lp.width = dm.widthPixels;
        lp.height = (int) ((float)lp.width / 16 * 9);
        mVideoView1.setLayoutParams(lp);

        findViewById(R.id.play).setOnClickListener(this);

        prepareInput();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mVideoView1.release();
    }

    @Override
    protected void onPause() {
        super.onPause();

        mVideoView1.pause();
    }

    private void prepareInput() {
        mVideoView1.getPlayer().setLooping(true);
        mVideoView1.mute();

//        AssetFileDescriptor afd1 = getResources().openRawResourceFd(R.raw.big_buck_bunny_360p_10mb);
        AssetFileDescriptor afd1 = getResources().openRawResourceFd(R.raw.big_buck_bunny_720p_20mb);
        mVideoView1.setDataSource(afd1);
    }

    @Override
    public void onClick(View v) {
        mVideoView1.start();
    }
}

package com.tencent.willisdai.demo.Video;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;

import com.tencent.willisdai.demo.R;

import java.io.IOException;

public class TextureVideoActivity extends Activity implements View.OnClickListener, MediaPlayer.OnPreparedListener, TextureView.SurfaceTextureListener, SurfaceTexture.OnFrameAvailableListener {

    private static TextureVideoActivity mInstance;

    private TextureView mTextureView;

    private MediaPlayer mPlayer;
    private RenderThread mRenderThread;
    private SurfaceTexture mSurfaceTexture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_texture_video);

        mTextureView = (TextureView) findViewById(R.id.textureView);

        DisplayMetrics dm = getResources().getDisplayMetrics();
        ViewGroup.LayoutParams lp =mTextureView.getLayoutParams();
        lp.width = dm.widthPixels;
        lp.height = (int) ((float)lp.width / 16 * 9);
        mTextureView.setLayoutParams(lp);

        mTextureView.setSurfaceTextureListener(this);

        findViewById(R.id.play).setOnClickListener(this);

        mInstance = this;
    }

    private void initVideo() {
        mPlayer = new MediaPlayer();

        mPlayer.setOnPreparedListener(this);

        AssetFileDescriptor afd = getResources().openRawResourceFd(R.raw.big_buck_bunny_360p_10mb);
        if (afd == null) return;

        try {
            mPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            afd.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        play();
    }

    private void play() {
        mPlayer.prepareAsync();
        findViewById(R.id.play).setEnabled(false);
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mPlayer.setLooping(true);
        mPlayer.start();
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        initVideo();

        mRenderThread = new RenderThread(surface);
        mRenderThread.setRegion(width, height);
        mRenderThread.start();
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
        mRenderThread.setRegion(width, height);
    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        synchronized (mRenderThread) {
            mRenderThread.release();
            mRenderThread.notify();

            mSurfaceTexture.release();
            mSurfaceTexture = null;
        }

        mPlayer.release();
        mPlayer = null;

        findViewById(R.id.play).setEnabled(true);
        return true;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

    }

    @Override
    public void onFrameAvailable(SurfaceTexture surfaceTexture) {
        synchronized (mRenderThread) {
            mRenderThread.notify();
        }
    }

    public static TextureVideoActivity getAppInstance() {
        return mInstance;
    }

    public void starVideo(int texture) {
        if (null == mSurfaceTexture){
            mSurfaceTexture = new SurfaceTexture(texture);
            mSurfaceTexture.setOnFrameAvailableListener(this);

            try {
                mPlayer.setSurface(new Surface(mSurfaceTexture));

            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void updateFrame() {
        if (mSurfaceTexture != null) {
            mSurfaceTexture.updateTexImage();
        }
    }
}

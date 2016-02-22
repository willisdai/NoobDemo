package com.tencent.willisdai.demo.Video;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.tencent.willisdai.demo.R;

/**
 * Created by willisdai on 16/2/19.
 */
public class VideoDemoActivity extends Activity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_video_demo);

        findViewById(R.id.textureVideo).setOnClickListener(this);
        findViewById(R.id.glSurfaceVideo).setOnClickListener(this);
        findViewById(R.id.surfaceVideo).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.textureVideo) {
            Intent i = new Intent(this, TextureVideoActivity.class);
            startActivity(i);

        } else if (id == R.id.glSurfaceVideo) {
            Intent i = new Intent(this, GLSurfaceVideoActivity.class);
            startActivity(i);

        } else if (id == R.id.surfaceVideo) {
            Intent i = new Intent(this, GLSurfaceVideoActivity.class);
            startActivity(i);
        }
    }
}

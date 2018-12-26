package awake1.xiaopang.com.videoplaydemo;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    VideoView mVideoView;
    MediaController mMediaController;
    private int index;
    private ArrayList<String> paths;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* 全屏幕 */
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        setContentView(R.layout.activity_main);

        index = 0;



        paths = new ArrayList<>();
        paths.add("/mnt/sdcard/video/ok.mp4");
        paths.add("/mnt/sdcard/video/ok.mp4");
        paths.add("/mnt/sdcard/video/ok.mp4");
        mVideoView = findViewById(R.id.video_view);
//      mMediaController = new MediaController(this);
        mVideoView.setVideoPath(paths.get(index));
//      mVideoView.setMediaController(mMediaController);
        mVideoView.seekTo(0);
        mVideoView.requestFocus();
        mVideoView.start();
        playVideo();
        mVideoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mVideoView.stopPlayback();
            }
        });
    }


    private void playVideo() {
        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if (paths.size()>index+1){
                    index++;
                }else {
                    index=0;
                }
                mVideoView.setVideoPath(paths.get(index));
                mVideoView.start();
            }
        });
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(new ContextWrapper(newBase) {
            @Override
            public Object getSystemService(String name) {
                // 解决 VideoView 中 AudioManager 造成的内存泄漏
                if (Context.AUDIO_SERVICE.equals(name)) {
                    return getApplicationContext().getSystemService(name);
                }
                return super.getSystemService(name);
            }
        });
    }
}

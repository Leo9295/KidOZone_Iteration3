package com.hellofit.kidozone.activityService;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

import com.hellofit.kidozone.R;

public class PuzzleIntroActivity extends AppCompatActivity {

    private VideoView videoView;
    private MediaController mediaController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle_intro);

        initView();

        Button skip = (Button) findViewById(R.id.skip);

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PuzzleIntroActivity.this, Puzzle.class);
                startActivity(intent);
            }
        });
    }

    /**
     * initial the video view and set the complete action: jump to game page
     */
    private void initView() {
        videoView = (VideoView) findViewById(R.id.videoView);
        init();
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Intent intent = new Intent(PuzzleIntroActivity.this, Puzzle.class);
                startActivity(intent);
            }
        });
    }

    private void init() {
        videoView = (VideoView) findViewById(R.id.videoView);
        mediaController = new MediaController(this);
        String uri = "android.resource://" + getPackageName() + "/" + R.raw.puzzle_introduction_video;
        videoView.setVideoURI(Uri.parse(uri));
        videoView.setMediaController(mediaController);
        mediaController.setMediaPlayer(videoView);
        mediaController.setVisibility(View.INVISIBLE);
        videoView.requestFocus();
        videoView.start();

    }
}

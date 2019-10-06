package com.hellofit.kidozone.activityService;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;
import android.net.Uri;
import android.widget.MediaController;

import com.hellofit.kidozone.R;
import com.hellofit.kidozone.puzzleGame.game.PuzzleGame;

import androidx.appcompat.app.AppCompatActivity;

public class PuzzleIntro extends AppCompatActivity {
    private VideoView videoView;
    private MediaController mediaController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.puzzle_intro);
        initView();

        Button skip = (Button) findViewById(R.id.skip);

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PuzzleIntro.this, Puzzle.class);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        videoView = (VideoView) findViewById(R.id.videoView);
        init();
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Intent intent = new Intent(PuzzleIntro.this, Puzzle.class);
                startActivity(intent);
            }
        });
    }

    private void init() {
        videoView = (VideoView) findViewById(R.id.videoView);
        mediaController = new MediaController(this);
        String uri = "android.resource://" + getPackageName() + "/" + R.raw.puzzle;
        videoView.setVideoURI(Uri.parse(uri));
        videoView.setMediaController(mediaController);
        mediaController.setMediaPlayer(videoView);
        mediaController.setVisibility(View.INVISIBLE);
        videoView.requestFocus();
        videoView.start();
    }

}

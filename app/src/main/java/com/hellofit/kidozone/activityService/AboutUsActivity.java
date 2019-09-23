package com.hellofit.kidozone.activityService;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.hellofit.kidozone.R;

public class AboutUsActivity extends AppCompatActivity {

    private MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        mp = MediaPlayer.create(AboutUsActivity.this, R.raw.background_music);
        mp.start();

        Button btn_button = (Button) findViewById(R.id.backButton);

        btn_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp.stop();
                Intent intent = new Intent(AboutUsActivity.this, MainActivity.class);
                startActivityForResult(intent, 1);
            }
        });
    }
}

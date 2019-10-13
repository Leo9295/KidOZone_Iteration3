package com.hellofit.kidozone.activityService;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.hellofit.kidozone.R;

/***
 *  This class is the introduction of the applications
 *
 *  Created by Mingzhe Liu on 09/17/19.
 *  Copyright @ 2019 Mingzhe Liu. All right reserved
 *
 *  @author Mingzhe Liu
 *  @version 3.2
 */

public class AboutUsActivity extends AppCompatActivity {

    private MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        Intent intent = getIntent();
        SharedPreferences sp = getSharedPreferences("SystemSP", MODE_PRIVATE);
        int media_length = sp.getInt("mp_length", 0);
        final boolean isMute = sp.getBoolean("isMute", false);

        if (!isMute) {
            mp = MediaPlayer.create(AboutUsActivity.this, R.raw.background_music);
            mp.seekTo(media_length);
            mp.start();
        }

        Button btn_button = (Button) findViewById(R.id.backButton);

        btn_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isMute)
                    mp.stop();
                Intent intent = new Intent(AboutUsActivity.this, MainActivity.class);
                startActivityForResult(intent, 1);
            }
        });
    }
}

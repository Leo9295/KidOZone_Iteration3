package com.hellofit.kidozone.activityService;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.hellofit.kidozone.R;

public class LunchBoxIntroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lunch_box_intro);

        Button button = (Button) findViewById(R.id.btn_lunchbox_intro);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LunchBoxIntroActivity.this, LunchBoxMatchActivity.class);
                startActivity(intent);
            }
        });
    }
}

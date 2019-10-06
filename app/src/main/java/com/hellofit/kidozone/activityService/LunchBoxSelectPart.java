package com.hellofit.kidozone.activityService;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.hellofit.kidozone.R;

public class LunchBoxSelectPart extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lunch_box_select_part);

        Button button = (Button) findViewById(R.id.btn_lunchbox_result);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view){
                Intent intent = new Intent(LunchBoxSelectPart.this, LunchBoxResult.class);
                startActivity(intent);
            }
        });
    }
}

package com.hellofit.kidozone.activityService;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.gson.Gson;
import com.hellofit.kidozone.R;
import com.hellofit.kidozone.common.RestClient;
import com.hellofit.kidozone.entity.FoodInfo;
import com.hellofit.kidozone.entity.WasteInfo;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // The list to contain the food entity which using in the game
    private ArrayList<FoodInfo> foodInfos;
    // The list to contain the waste entity which using in the game
    private ArrayList<WasteInfo> wasteInfos;

    MediaPlayer mp;
    int media_length;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        foodInfos = new ArrayList<FoodInfo>();
        wasteInfos = new ArrayList<WasteInfo>();

        mp = MediaPlayer.create(MainActivity.this, R.raw.background_music);
        mp.start();

        Button buttonAboutUs = (Button) findViewById(R.id.aboutUsButton);

        buttonAboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp.pause();
                media_length = mp.getCurrentPosition();
                Intent intent = new Intent(MainActivity.this, AboutUsActivity.class);
                intent.putExtra("mp_length", media_length);
                startActivity(intent);
            }
        });

        Button imageLunch = (Button) findViewById(R.id.imageLunchGame);

        imageLunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.stop();
                Intent intent = new Intent(MainActivity.this, LunchBoxIntroActivity.class);
                SharedPreferences.Editor editor = getSharedPreferences("SystemSP", MODE_PRIVATE).edit();
                Gson gson = new Gson();
                String json = gson.toJson(foodInfos);
                editor.putString("foodList", json);
                editor.commit();
                startActivity(intent);
            }
        });

        Button imageWaste = (Button) findViewById(R.id.imageWasteGame);

        imageWaste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.stop();
                Intent intent = new Intent(MainActivity.this, WasteIntroActivity.class);
                SharedPreferences.Editor editor = getSharedPreferences("SystemSP", MODE_PRIVATE).edit();
                Gson gson = new Gson();
                String json = gson.toJson(wasteInfos);
                editor.putString("wasteList", json);
                editor.commit();
                startActivityForResult(intent, 1);
            }
        });

        Button imagePuzzle = (Button) findViewById(R.id.imagePuzzleGame);

        imagePuzzle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.stop();
                Intent intent = new Intent(MainActivity.this, PuzzleIntroActivity.class);
                startActivity(intent);
            }
        });

        GetFoodEntityAsyncTask gfe = new GetFoodEntityAsyncTask();
        gfe.execute();
        GetWasteEntityAsyncTask gwe = new GetWasteEntityAsyncTask();
        gwe.execute();

    }

    private class GetFoodEntityAsyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            foodInfos = RestClient.parseFoodJson(RestClient.getLunchboxFoodList());
            return null;
        }
    }

    private class GetWasteEntityAsyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            wasteInfos = RestClient.parseWasteJson(RestClient.getRandomWaste());
            return null;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (mp != null) {
            mp.pause();
            media_length = mp.getCurrentPosition();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mp != null) {
            mp.seekTo(media_length);
            mp.start();
        }
    }

}

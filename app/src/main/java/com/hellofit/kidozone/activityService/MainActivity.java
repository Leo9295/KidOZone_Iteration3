package com.hellofit.kidozone.activityService;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.gson.Gson;
import com.hellofit.kidozone.R;
import com.hellofit.kidozone.common.RestClient;
import com.hellofit.kidozone.entity.FoodInfo;
import com.hellofit.kidozone.entity.WasteInfo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
    boolean isMute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        foodInfos = new ArrayList<FoodInfo>();
        wasteInfos = new ArrayList<WasteInfo>();

        mp = MediaPlayer.create(MainActivity.this, R.raw.background_music);
        mp.start();

        isMute = false;

        Button buttonAboutUs = (Button) findViewById(R.id.aboutUsButton);

        buttonAboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp.pause();
                media_length = mp.getCurrentPosition();
                Intent intent = new Intent(MainActivity.this, AboutUsActivity.class);
                intent.putExtra("isMute", isMute);
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

        final Button btn_mute = (Button) findViewById(R.id.btn_main_mute);

        btn_mute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isMute) {
                    mp = MediaPlayer.create(MainActivity.this, R.raw.background_music);
                    mp.start();
                    Glide.with(MainActivity.this).asBitmap().load(R.drawable.btn_main_mute).into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            Drawable drawable = new BitmapDrawable(resource);
                            btn_mute.setBackground(drawable);
                        }
                    });
                    isMute = false;
                } else {
                    mp.stop();
                    Glide.with(MainActivity.this).asBitmap().load(R.drawable.btn_main_muted).into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            Drawable drawable = new BitmapDrawable(resource);
                            btn_mute.setBackground(drawable);
                        }
                    });
                    isMute = true;
                }
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

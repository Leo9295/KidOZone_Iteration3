package com.hellofit.kidozone.activityService;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hellofit.kidozone.R;
import com.hellofit.kidozone.entity.WasteInfo;

import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Waste extends AppCompatActivity {

    private float downX;
    private float downY;
    private int listIndex;
    private int score;

    // The list to contain the waste entity which using in the game
    private ArrayList<WasteInfo> wasteInfos;

    LottieAnimationView lottieAnimationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.waste);
        listIndex = 0;

        // Initialize view component
        Button backButton = (Button) findViewById(R.id.backButton);
        TextView tv_wasteName = (TextView) findViewById(R.id.rubbishName);
        ImageView iv_wasteImge = (ImageView) findViewById(R.id.rubbish);
        TextView tv_userScore = (TextView) findViewById(R.id.wasteScore);

        // Load Waste data from SharedPreferences
        SharedPreferences sp = getSharedPreferences("SystemSP", MODE_PRIVATE);
        String json = sp.getString("wasteList", null);
        if (json != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<WasteInfo>>() {
            }.getType();
            wasteInfos = gson.fromJson(json, type);
        }

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Waste.this, MainActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        tv_wasteName.setText(wasteInfos.get(0).getWasteName());
        Glide.with(this).load(wasteInfos.get(0).getWasteImage()).into(iv_wasteImge);
        Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
        iv_wasteImge.startAnimation(shake);

        score = 100;
        tv_userScore.setText("Score: " + score);

        lottieAnimationView = (LottieAnimationView) findViewById(R.id.animation_view4);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        ImageView iv_wasteImge = (ImageView) findViewById(R.id.rubbish);
        TextView tv_wasteName = (TextView) findViewById(R.id.rubbishName);

        if (listIndex == 0) {
            Glide.with(this).load(R.drawable.fivestar).into(lottieAnimationView);
        }

        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                downX = x;
                downY = y;
                Log.e("Tag", "=======PressX：" + x);
                Log.e("Tag", "=======PressY：" + y);
                break;

            case MotionEvent.ACTION_UP:
                Log.e("Tag", "=======PressX：" + x);
                Log.e("Tag", "=======PressY：" + y);

                float dx = x - downX;
                float dy = y - downY;

                Log.e("Tag", "========X axis Distance：" + dx);
                Log.e("Tag", "========Y axis Distance：" + dy);
                if (Math.abs(dx) > 300 || Math.abs(dy) > 300) {

                    MediaPlayer mp = MediaPlayer.create(Waste.this, R.raw.shoop);
                    mp.start();

                    int orientation = getOrientation(dx, dy);
                    // From 1 - 10 times of playing
                    if (listIndex < wasteInfos.size()) {
                        switch (orientation) {
                            // To right -> Yellow Bin
                            case 'r':
                                if (wasteInfos.get(listIndex).getCategoryName().equals("yellow")) {
                                    MediaPlayer mp1 = MediaPlayer.create(Waste.this, R.raw.great);
                                    mp1.start();
                                    Toast toast = Toast.makeText(Waste.this, "Added to the YellowBin!", Toast.LENGTH_SHORT);
                                    toast.setGravity(Gravity.BOTTOM, 0, 200);
                                    toast.show();
                                    if (listIndex < wasteInfos.size() - 1) {
                                        Glide.with(this).load(wasteInfos.get(listIndex + 1).getWasteImage()).into(iv_wasteImge);
                                        Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
                                        iv_wasteImge.startAnimation(shake);
                                        tv_wasteName.setText(wasteInfos.get(listIndex + 1).getWasteName());
                                        listIndex++;
                                    } else {
                                        listIndex++;
                                        Glide.with(this).load(R.drawable.wastegameend).into(iv_wasteImge);
                                        tv_wasteName.setText("");
                                        break;
                                    }
                                } else {
                                    MediaPlayer mp1 = MediaPlayer.create(Waste.this, R.raw.wrong);
                                    mp1.start();
                                    Toast toast = Toast.makeText(Waste.this, "Not that bin, try again.", Toast.LENGTH_SHORT);
                                    toast.setGravity(Gravity.BOTTOM, 0, 200);
                                    toast.show();
                                    if (score > 0) {
                                        score = score - 5;
                                        TextView tv_userScore = (TextView) findViewById(R.id.wasteScore);
                                        tv_userScore.setText("Score: " + score);
                                        Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake_text);
                                        tv_userScore.startAnimation(shake);
                                        setStarPic(score);
                                    }
                                }
                                break;
                            // To Left -> Red Bin
                            case 'l':
                                if (wasteInfos.get(listIndex).getCategoryName().equals("red")) {
                                    MediaPlayer mp1 = MediaPlayer.create(Waste.this, R.raw.great);
                                    mp1.start();
                                    Toast toast = Toast.makeText(Waste.this, "Added to the RedBin!", Toast.LENGTH_SHORT);
                                    toast.setGravity(Gravity.BOTTOM, 0, 200);
                                    toast.show();
                                    if (listIndex < wasteInfos.size() - 1) {
                                        Glide.with(this).load(wasteInfos.get(listIndex + 1).getWasteImage()).into(iv_wasteImge);
                                        Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
                                        iv_wasteImge.startAnimation(shake);
                                        tv_wasteName.setText(wasteInfos.get(listIndex + 1).getWasteName());
                                        listIndex++;
                                    } else {
                                        listIndex++;
                                        Glide.with(this).load(R.drawable.wastegameend).into(iv_wasteImge);
                                        tv_wasteName.setText("");
                                        break;
                                    }
                                } else {
                                    MediaPlayer mp1 = MediaPlayer.create(Waste.this, R.raw.wrong);
                                    mp1.start();
                                    Toast toast = Toast.makeText(Waste.this, "Not that bin, try again.", Toast.LENGTH_SHORT);
                                    toast.setGravity(Gravity.BOTTOM, 0, 200);
                                    toast.show();
                                    if (score > 0) {
                                        score = score - 5;
                                        TextView tv_userScore = (TextView) findViewById(R.id.wasteScore);
                                        tv_userScore.setText("Score: " + score);
                                        Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake_text);
                                        tv_userScore.startAnimation(shake);
                                        setStarPic(score);
                                    }
                                }
                                break;
                            // To Top -> Green Bin
                            case 't':
                                if (wasteInfos.get(listIndex).getCategoryName().equals("green")) {
                                    MediaPlayer mp1 = MediaPlayer.create(Waste.this, R.raw.great);
                                    mp1.start();
                                    Toast toast = Toast.makeText(Waste.this, "Added to the GreenBin!", Toast.LENGTH_SHORT);
                                    toast.setGravity(Gravity.BOTTOM, 0, 200);
                                    toast.show();
                                    if (listIndex < wasteInfos.size() - 1) {
                                        Glide.with(this).load(wasteInfos.get(listIndex + 1).getWasteImage()).into(iv_wasteImge);
                                        Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
                                        iv_wasteImge.startAnimation(shake);
                                        tv_wasteName.setText(wasteInfos.get(listIndex + 1).getWasteName());
                                        listIndex++;
                                    } else {
                                        listIndex++;
                                        Glide.with(this).load(R.drawable.wastegameend).into(iv_wasteImge);
                                        tv_wasteName.setText("");
                                        break;
                                    }
                                } else {
                                    MediaPlayer mp1 = MediaPlayer.create(Waste.this, R.raw.wrong);
                                    mp1.start();
                                    Toast toast = Toast.makeText(Waste.this, "Not that bin, try again.", Toast.LENGTH_SHORT);
                                    toast.setGravity(Gravity.BOTTOM, 0, 200);
                                    toast.show();
                                    if (score > 0) {
                                        score = score - 5;
                                        TextView tv_userScore = (TextView) findViewById(R.id.wasteScore);
                                        tv_userScore.setText("Score: " + score);
                                        Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake_text);
                                        tv_userScore.startAnimation(shake);
                                        setStarPic(score);
                                    }
                                }
                                break;
                        }
                    } else {
                        Glide.with(this).load(R.drawable.wastegameend).into(iv_wasteImge);
                        tv_wasteName.setText("");
                    }
                    // Display display = getWindowManager().getDefaultDisplay();
                    //   int height = display.getHeight();
                    // Toast toast = Toast.makeText(Waste.this, "", Toast.LENGTH_SHORT);
                    // toast.setGravity(Gravity.TOP, 0, 5 * (height / 8));
                    //  toast.show();
                }
                break;
        }
        return super.onTouchEvent(event);
    }


    private int getOrientation(float dx, float dy) {

        if (Math.abs(dx) > Math.abs(dy)) {
            //X axis move
            return dx > 0 ? 'r' : 'l';
        } else {
            //Y axis move
            return dy > 0 ? 'b' : 't';
        }
    }

    private void setStarPic(int score) {
        if (score >= 80) {
            Glide.with(this).load(R.drawable.fivestar).into(lottieAnimationView);
        }
        if ((score < 80) && (score >= 60)) {
            Glide.with(this).load(R.drawable.fourstar).into(lottieAnimationView);
        }
        if (score < 60 && score >= 40) {
            Glide.with(this).load(R.drawable.threestar).into(lottieAnimationView);
        }
        if (score < 40 && score >= 20) {
            Glide.with(this).load(R.drawable.twostar).into(lottieAnimationView);
        }
        if (score < 20) {
            Glide.with(this).load(R.drawable.onestar).into(lottieAnimationView);
        }
    }

    /**
     * Clear the Waste List
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences.Editor editor = getSharedPreferences("SystemSP", MODE_PRIVATE).edit();
        editor.remove("wasteList");
        editor.commit();
    }

}

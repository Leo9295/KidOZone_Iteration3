package com.hellofit.kidozone.activityService;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hellofit.kidozone.R;
import com.hellofit.kidozone.common.RestClient;
import com.hellofit.kidozone.common.SystemUtil;
import com.hellofit.kidozone.entity.FoodInfo;
import com.hellofit.kidozone.entity.WasteInfo;

import java.lang.reflect.Type;
import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class LunchBox extends AppCompatActivity {


    private float downX;
    private float downY;
    private int listIndex;
    private int count;
    private Bundle bundle = new Bundle();
    private ArrayList<FoodInfo> foodInfos;
    private ArrayList<FoodInfo> pickedList;

    private MediaPlayer mp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lunch_box);

        // Initialize view component
        Button btn_goResult = (Button) findViewById(R.id.finish);
        Button btn_backButton = (Button) findViewById(R.id.backButton);
        ImageView iv_foodImage = (ImageView) findViewById(R.id.Food);
        TextView tv_userPickedSum = (TextView) findViewById(R.id.count);
        TextView tv_foodName = (TextView) findViewById(R.id.foodName);

        foodInfos = new ArrayList<FoodInfo>();
        pickedList = new ArrayList<FoodInfo>();

        mp = MediaPlayer.create(LunchBox.this, R.raw.lunch_box_intro);
        mp.start();

        // Load Waste data from SharedPreferences
        SharedPreferences sp = getSharedPreferences("SystemSP", MODE_PRIVATE);
        String json = sp.getString("foodList", null);
        if (json != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<FoodInfo>>() {
            }.getType();
            foodInfos = gson.fromJson(json, type);
        }

        btn_goResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.stop();
                Intent intent = new Intent(LunchBox.this, LunchBoxResult.class);
                bundle.putSerializable("pickItemList", pickedList);
                intent.putExtras(bundle);
                startActivityForResult(intent, 1);
            }
        });

        btn_backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.stop();
                Intent intent = new Intent(LunchBox.this, MainActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        listIndex = 0;
        count = 0;

        tv_userPickedSum.setText(count + " / 6");
        tv_foodName.setText(foodInfos.get(listIndex).getFoodName());
        Glide.with(this).load(foodInfos.get(listIndex).getFoodImage()).into(iv_foodImage);
        Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
        iv_foodImage.startAnimation(shake);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        ImageView iv_foodImage = (ImageView) findViewById(R.id.Food);
        TextView tv_foodName = (TextView) findViewById(R.id.foodName);

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

                    MediaPlayer mp = MediaPlayer.create(LunchBox.this, R.raw.shoop);
                    mp.start();
                    int orientation = getOrientation(dx, dy);
                    if (listIndex < foodInfos.size() && !pickedList.contains(foodInfos.get(listIndex))) {
                        if (count < 6) {
                            if (orientation == 'r') {
                                Toast toast = Toast.makeText(LunchBox.this, "Added to the lunchBox!", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                                pickedList.add(foodInfos.get(listIndex));
                                if (listIndex < foodInfos.size()) {
                                    listIndex++;
                                }
                                if (listIndex == foodInfos.size()) {
                                    for (int index = 0; index < foodInfos.size(); index++) {
                                        if (!pickedList.contains(foodInfos.get(index))) {
                                            Glide.with(this).load(foodInfos.get(index).getFoodImage()).into(iv_foodImage);
                                            tv_foodName.setText(foodInfos.get(index).getFoodName());
                                            listIndex = index;
                                            break;
                                        }
                                    }
                                } else {
                                    Glide.with(this).load(foodInfos.get(listIndex).getFoodImage()).into(iv_foodImage);
                                    tv_foodName.setText(foodInfos.get(listIndex).getFoodName());
                                }
                                count++;
                                TextView tv_userPickedSum = (TextView) findViewById(R.id.count);
                                tv_userPickedSum.setText(count + " / 6");
                                Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake_text);
                                tv_userPickedSum.startAnimation(shake);

                            } else {
                                Toast toast1 = Toast.makeText(LunchBox.this, "Looks like u dont like it!", Toast.LENGTH_SHORT);
                                toast1.setGravity(Gravity.CENTER, 0, 0);
                                toast1.show();
                                if (listIndex < foodInfos.size()) {
                                    listIndex++;
                                }
                                if (listIndex == foodInfos.size()) {
                                    for (int index = 0; index < foodInfos.size(); index++) {
                                        if (!pickedList.contains(foodInfos.get(index))) {
                                            Glide.with(this).load(foodInfos.get(index).getFoodImage()).into(iv_foodImage);
                                            tv_foodName.setText(foodInfos.get(index).getFoodName());
                                            listIndex = index;
                                            break;
                                        }
                                    }
                                } else {
                                    Glide.with(this).load(foodInfos.get(listIndex).getFoodImage()).into(iv_foodImage);
                                    tv_foodName.setText(foodInfos.get(listIndex).getFoodName());
                                }
                            }
                            // Display display = getWindowManager().getDefaultDisplay();
                            //   int height = display.getHeight();
                            //  Toast toast = Toast.makeText(LunchBox.this, "", Toast.LENGTH_SHORT);
                            // toast.setGravity(Gravity.TOP, 0, 5 * (height / 8));
                            //   toast.show();
                        } else {
                            Toast toast1 = Toast.makeText(LunchBox.this, "You have already picked 6, submit and check the score!", Toast.LENGTH_SHORT);
                            toast1.setGravity(Gravity.CENTER, 0, 0);
                            toast1.show();
                        }
                    } else if (pickedList.contains(foodInfos.get(listIndex))) {
                        listIndex++;
                    } else if (listIndex >= foodInfos.size()) {
                        listIndex = 0;
                    }
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

}


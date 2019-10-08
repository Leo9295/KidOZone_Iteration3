package com.hellofit.kidozone.activityService;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hellofit.kidozone.R;
import com.hellofit.kidozone.entity.FoodInfo;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class LunchBoxMatchActivity extends AppCompatActivity {

    private ArrayList<FoodInfo> foodInfoList;
    private ArrayList<FoodInfo> pickedList;
    private int currentFoodIndex;
    // when question == 0, should be wrong question
    // when question == 1, should be right question
    private int question;
    // when answer == 'l', user make left choice
    // when answer == 'r', user make right choice
    private char answer;

    private MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lunch_box_match);

        // Initialize view component
        final Button btn_backButton = (Button) findViewById(R.id.backButton);
        final Button btn_yes = (Button) findViewById(R.id.btn_lb_match_yes);
        final Button btn_no = (Button) findViewById(R.id.btn_lb_match_no);
        final Button btn_start = (Button) findViewById(R.id.btn_lb_match_start);
        final ImageView iv_foodImage = (ImageView) findViewById(R.id.iv_lb_match_food_pic);
        final ImageView iv_userPickedSum = (ImageView) findViewById(R.id.iv_count_number);
        final ImageView iv_foodType = (ImageView) findViewById(R.id.iv_match_food_type);
        final TextView tv_foodName = (TextView) findViewById(R.id.foodName);

        // Set component invisible at beginning
        btn_yes.setVisibility(View.INVISIBLE);
        btn_no.setVisibility(View.INVISIBLE);
        iv_foodType.setVisibility(View.INVISIBLE);
        iv_foodImage.setVisibility(View.GONE);
        tv_foodName.setText("");

        foodInfoList = new ArrayList<FoodInfo>();
        pickedList = new ArrayList<FoodInfo>();

        mp = MediaPlayer.create(LunchBoxMatchActivity.this, R.raw.lunch_box_intro);
        mp.start();
        final Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);

        // Load Food data from SharedPreferences
        SharedPreferences sp = getSharedPreferences("SystemSP", MODE_PRIVATE);
        String json = sp.getString("foodList", null);
        if (json != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<FoodInfo>>() {}.getType();
            foodInfoList = gson.fromJson(json, type);
        }

        // On click listener setting
        btn_backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.stop();
                Intent intent = new Intent(LunchBoxMatchActivity.this, MainActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_yes.setVisibility(View.VISIBLE);
                btn_no.setVisibility(View.VISIBLE);
                btn_start.setVisibility(View.GONE);
                iv_foodImage.setVisibility(View.VISIBLE);
                iv_foodType.setVisibility(View.VISIBLE);
                // display components
                currentFoodIndex = getRandomNum(0,29);
                setFoodNameAndImage(currentFoodIndex, iv_foodImage, tv_foodName);
                iv_foodImage.startAnimation(shake);
                // answers
                setQuestionImage(currentFoodIndex, iv_foodType);

            }
        });

        // Press the "Yes" button
        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answer = 'r';
                clickButton(iv_foodImage, tv_foodName, iv_foodType, iv_userPickedSum);
                answer = ' ';
            }
        });

        // Press the "No" button
        btn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answer = 'l';
                clickButton(iv_foodImage, tv_foodName, iv_foodType, iv_userPickedSum);
                answer = ' ';
            }
        });
    }

    private void clickButton(ImageView foodImage, TextView foodName, ImageView typeImage, ImageView userPicked) {
        if ((question == 0 && answer == 'l') || (question == 1 && answer == 'r')) {
            //
            if (pickedList.size() < 5) {
                pickedList.add(foodInfoList.get(currentFoodIndex));
                while (true) {
                    currentFoodIndex = getRandomNum(0, 29);
                    if (!pickedList.contains(foodInfoList.get(currentFoodIndex))) {
                        setFoodNameAndImage(currentFoodIndex, foodImage, foodName);
                        setQuestionImage(currentFoodIndex, typeImage);
                        break;
                    }
                }
                setPickedNumImage(userPicked, pickedList.size());
            }
            // Size of pickedList has reached 5, stop the game, going to next stage
            else {
                showAlertDialog("Great Work! Made 6 Correct Now!", "Go Pack Lunch Box", false);
            }
        } else {
            // Size of pickedList is not enough 6, continue the game
            if (pickedList.size() < 6) {
                showAlertDialog("Opps..Think it Carefully", "Try Next One", true);
                while (true) {
                    currentFoodIndex = getRandomNum(0, 29);
                    if (!pickedList.contains(foodInfoList.get(currentFoodIndex))) {
                        setFoodNameAndImage(currentFoodIndex, foodImage, foodName);
                        setQuestionImage(currentFoodIndex, typeImage);
                        break;
                    }
                }
            }
        }
    }

    private void setPickedNumImage(ImageView userPicked, int size) {
        switch (size) {
            case 0:
                Glide.with(LunchBoxMatchActivity.this).load(R.drawable.count_number_0).into(userPicked);
                break;
            case 1:
                Glide.with(LunchBoxMatchActivity.this).load(R.drawable.count_number_1).into(userPicked);
                break;
            case 2:
                Glide.with(LunchBoxMatchActivity.this).load(R.drawable.count_number_2).into(userPicked);
                break;
            case 3:
                Glide.with(LunchBoxMatchActivity.this).load(R.drawable.count_number_3).into(userPicked);
                break;
            case 4:
                Glide.with(LunchBoxMatchActivity.this).load(R.drawable.count_number_4).into(userPicked);
                break;
            case 5:
                Glide.with(LunchBoxMatchActivity.this).load(R.drawable.count_number_5).into(userPicked);
                break;
            case 6:
                Glide.with(LunchBoxMatchActivity.this).load(R.drawable.count_number_6).into(userPicked);
                break;
            default:
                Glide.with(LunchBoxMatchActivity.this).load(R.drawable.count_number_9).into(userPicked);
                break;
        }
    }

    private void setQuestionImage(int foodIndex, ImageView imageView) {
        if (!foodInfoList.get(foodIndex).getCategoryName().equals("junks")) {
            question = getRandomNum(0, 1);
        } else {
            question = 0;
        }
        if (question == 1) {
            setFoodTypeImage(indexOfFoodCategory(foodInfoList.get(foodIndex).getCategoryName()), imageView);
        } else {
            ArrayList<Integer> temp = new ArrayList<Integer>(Arrays.asList(0, 1, 2, 3, 4, 5));
            temp.remove(indexOfFoodCategory(foodInfoList.get(foodIndex).getCategoryName()));
            setFoodTypeImage(temp.get(getRandomNum(0, temp.size() - 1)), imageView);
        }
    }

    private void showAlertDialog(String title, String buttonText, boolean flag) {
        AlertDialog.Builder normalDialog = new AlertDialog.Builder(LunchBoxMatchActivity.this);
//        normalDialog.setIcon(R.drawable.icon_dialog);
        normalDialog.setTitle("").setMessage(title);
        if (flag) {
            normalDialog.setPositiveButton(buttonText, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // ...To-do
                }
            });
        } else {
            normalDialog.setPositiveButton(buttonText, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(LunchBoxMatchActivity.this, LunchBoxSelectActivity.class);
                    startActivity(intent);
                }
            });
        }
        normalDialog.show();
    }

    private void setFoodTypeImage(int typeNum, ImageView imageView) {
        switch (typeNum) {
            case 0:
                Glide.with(this).load(R.drawable.lunchbox_title_fruit).into(imageView);
                break;
            case 1:
                Glide.with(this).load(R.drawable.lunchbox_title_vegetable).into(imageView);
                break;
            case 2:
                Glide.with(this).load(R.drawable.lunchbox_title_diary_product).into(imageView);
                break;
            case 3:
                Glide.with(this).load(R.drawable.lunchbox_title_meat).into(imageView);
                break;
            case 4:
                Glide.with(this).load(R.drawable.lunchbox_title_grain).into(imageView);
                break;
            case 5:
                Glide.with(this).load(R.drawable.lunchbox_title_drink).into(imageView);
                break;
        }
    }

    private int indexOfFoodCategory(String categoryName) {
        switch (categoryName) {
            case "fruit":
                return 0;
            case "vegetable":
                return 1;
            case "diary product":
                return 2;
            case "meat":
                return 3;
            case "grain":
                return 4;
            case "drink":
                return 5;
        }
        return 0;
    }

    /**
     *
     * @param index
     */
    private void setFoodNameAndImage(int index, ImageView iv, TextView tv) {
        Glide.with(this).load(foodInfoList.get(index).getFoodImage()).into(iv);
        tv.setText(foodInfoList.get(index).getFoodName());
    }

    /**
     * Create a random number between min and max
     * Both limitations are inclusive
     *
     * @param min low limitation
     * @param max high limitation
     * @return random number
     */
    private int getRandomNum(int min, int max) {
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }



}


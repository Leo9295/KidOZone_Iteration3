package com.hellofit.kidozone.activityService;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hellofit.kidozone.R;
import com.hellofit.kidozone.entity.FoodInfo;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class LunchBoxSelectActivity extends AppCompatActivity {

    private ArrayList<FoodInfo> foodInfoList = new ArrayList<>();
    private ArrayList<FoodInfo> pickedList = new ArrayList<>();
    private int loopIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lunch_box_select);

        // Load Food data from SharedPreferences
        SharedPreferences sp = getSharedPreferences("SystemSP", MODE_PRIVATE);
        String json = sp.getString("foodList", null);
        if (json != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<FoodInfo>>() {}.getType();
            foodInfoList = gson.fromJson(json, type);
        }

        TextView tv_selectTitle = (TextView) findViewById(R.id.tv_lb_select_title);
        final TextView tv_selectType = (TextView) findViewById(R.id.tv_lb_select_foodType);
        final Button btn_food1 = (Button) findViewById(R.id.btn_lb_select_food1);
        final Button btn_food2 = (Button) findViewById(R.id.btn_lb_select_food2);
        final Button btn_food3 = (Button) findViewById(R.id.btn_lb_select_food3);
        final Button btn_foodCross = (Button) findViewById(R.id.btn_lb_select_food_cross);
        Button btn_back = (Button) findViewById(R.id.backButton);

        ImageView iv = (ImageView) findViewById(R.id.iv_test);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder normalDialog = new AlertDialog.Builder(LunchBoxSelectActivity.this);
//              normalDialog.setIcon(R.drawable.icon_dialog);
                normalDialog.setTitle("").setMessage("You really want to quit now?");
                normalDialog.setPositiveButton("Yes, I'm leaving", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(LunchBoxSelectActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                });
                normalDialog.setNegativeButton("I click wrong button", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                normalDialog.show();
            }
        });

        btn_food1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (loopIndex < 6) {
                    pickedList.add(foodInfoList.get((loopIndex - 1) * 5));
                    madeChoice(tv_selectType, btn_food1, btn_food2, btn_food3);
                }
                else {
                    pickedList.add(foodInfoList.get((loopIndex - 1) * 5));
                    AlertDialog.Builder normalDialog = new AlertDialog.Builder(LunchBoxSelectActivity.this);
//                  normalDialog.setIcon(R.drawable.icon_dialog);
                    normalDialog.setTitle("").setMessage("You've finish all pick! Check the result!");
                    normalDialog.setPositiveButton("Go", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(LunchBoxSelectActivity.this, LunchBoxResult.class);
                            saveListInSP();
                            startActivity(intent);
                        }
                    });
                    normalDialog.show();
                }

            }
        });

        btn_food2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (loopIndex < 6) {
                    pickedList.add(foodInfoList.get(((loopIndex - 1) * 5) + 1));
                    madeChoice(tv_selectType, btn_food1, btn_food2, btn_food3);
                }
                else {
                    pickedList.add(foodInfoList.get((loopIndex - 1) * 5));
                    AlertDialog.Builder normalDialog = new AlertDialog.Builder(LunchBoxSelectActivity.this);
//                  normalDialog.setIcon(R.drawable.icon_dialog);
                    normalDialog.setTitle("").setMessage("You've finish all pick! Check the result!");
                    normalDialog.setPositiveButton("Go", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(LunchBoxSelectActivity.this, LunchBoxResult.class);
                            saveListInSP();
                            startActivity(intent);
                        }
                    });
                    normalDialog.show();
                }
            }
        });

        btn_food3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (loopIndex < 6) {
                    pickedList.add(foodInfoList.get(((loopIndex - 1) * 5) + 2));
                    madeChoice(tv_selectType, btn_food1, btn_food2, btn_food3);
                }
                else {
                    pickedList.add(foodInfoList.get((loopIndex - 1) * 5));
                    AlertDialog.Builder normalDialog = new AlertDialog.Builder(LunchBoxSelectActivity.this);
//                  normalDialog.setIcon(R.drawable.icon_dialog);
                    normalDialog.setTitle("").setMessage("You've finish all pick! Check the result!");
                    normalDialog.setPositiveButton("Go", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(LunchBoxSelectActivity.this, LunchBoxResult.class);
                            saveListInSP();
                            startActivity(intent);
                        }
                    });
                    normalDialog.show();
                }
            }
        });

        btn_foodCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (loopIndex < 6) {
                    madeChoice(tv_selectType, btn_food1, btn_food2, btn_food3);
                }
                else {
                    pickedList.add(foodInfoList.get((loopIndex - 1) * 5));
                    AlertDialog.Builder normalDialog = new AlertDialog.Builder(LunchBoxSelectActivity.this);
//                  normalDialog.setIcon(R.drawable.icon_dialog);
                    normalDialog.setTitle("").setMessage("You've finish all pick! Check the result!");
                    normalDialog.setPositiveButton("Go", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(LunchBoxSelectActivity.this, LunchBoxResult.class);
                            saveListInSP();
                            startActivity(intent);
                        }
                    });
                    normalDialog.show();
                }
            }
        });

        madeChoice(tv_selectType, btn_food1, btn_food2, btn_food3);
    }

    private void madeChoice(TextView textView, final Button btn_food1, final Button btn_food2, final Button btn_food3) {
        switch (loopIndex) {
            case 0:
                textView.setText("Which Fruit Do you want?");
                Glide.with(this).asBitmap().load(foodInfoList.get(0).getFoodImage()).into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        Drawable drawable = new BitmapDrawable(resource);
                        btn_food1.setBackground(drawable);
                    }
                });
                Glide.with(this).asBitmap().load(foodInfoList.get(1).getFoodImage()).into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        Drawable drawable = new BitmapDrawable(resource);
                        btn_food2.setBackground(drawable);
                    }
                });
                Glide.with(this).asBitmap().load(foodInfoList.get(2).getFoodImage()).into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        Drawable drawable = new BitmapDrawable(resource);
                        btn_food3.setBackground(drawable);
                    }
                });
                loopIndex++;
                break;
            case 1:
                textView.setText("Which Vegetable Do you like?");
                Glide.with(this).asBitmap().load(foodInfoList.get(5).getFoodImage()).into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        Drawable drawable = new BitmapDrawable(resource);
                        btn_food1.setBackground(drawable);
                    }
                });
                Glide.with(this).asBitmap().load(foodInfoList.get(6).getFoodImage()).into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        Drawable drawable = new BitmapDrawable(resource);
                        btn_food2.setBackground(drawable);
                    }
                });
                Glide.with(this).asBitmap().load(foodInfoList.get(7).getFoodImage()).into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        Drawable drawable = new BitmapDrawable(resource);
                        btn_food3.setBackground(drawable);
                    }
                });
                loopIndex++;
                break;
            case 2:
                textView.setText("Which Diary Product do you want?");
                Glide.with(this).asBitmap().load(foodInfoList.get(10).getFoodImage()).into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        Drawable drawable = new BitmapDrawable(resource);
                        btn_food1.setBackground(drawable);
                    }
                });
                Glide.with(this).asBitmap().load(foodInfoList.get(11).getFoodImage()).into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        Drawable drawable = new BitmapDrawable(resource);
                        btn_food2.setBackground(drawable);
                    }
                });
                Glide.with(this).asBitmap().load(foodInfoList.get(12).getFoodImage()).into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        Drawable drawable = new BitmapDrawable(resource);
                        btn_food3.setBackground(drawable);
                    }
                });
                loopIndex++;
                break;
            case 3:
                textView.setText("Which kind of Meat do you like?");
                Glide.with(this).asBitmap().load(foodInfoList.get(15).getFoodImage()).into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        Drawable drawable = new BitmapDrawable(resource);
                        btn_food1.setBackground(drawable);
                    }
                });
                Glide.with(this).asBitmap().load(foodInfoList.get(16).getFoodImage()).into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        Drawable drawable = new BitmapDrawable(resource);
                        btn_food2.setBackground(drawable);
                    }
                });
                Glide.with(this).asBitmap().load(foodInfoList.get(17).getFoodImage()).into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        Drawable drawable = new BitmapDrawable(resource);
                        btn_food3.setBackground(drawable);
                    }
                });
                loopIndex++;
                break;
            case 4:
                textView.setText("What kind of Grain do you want?");
                Glide.with(this).asBitmap().load(foodInfoList.get(20).getFoodImage()).into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        Drawable drawable = new BitmapDrawable(resource);
                        btn_food1.setBackground(drawable);
                    }
                });
                Glide.with(this).asBitmap().load(foodInfoList.get(21).getFoodImage()).into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        Drawable drawable = new BitmapDrawable(resource);
                        btn_food2.setBackground(drawable);
                    }
                });
                Glide.with(this).asBitmap().load(foodInfoList.get(22).getFoodImage()).into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        Drawable drawable = new BitmapDrawable(resource);
                        btn_food3.setBackground(drawable);
                    }
                });
                loopIndex++;
                break;
            case 5:
                textView.setText("Which Drink do you want to add?");
                Glide.with(this).asBitmap().load(foodInfoList.get(25).getFoodImage()).into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        Drawable drawable = new BitmapDrawable(resource);
                        btn_food1.setBackground(drawable);
                    }
                });
                Glide.with(this).asBitmap().load(foodInfoList.get(26).getFoodImage()).into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        Drawable drawable = new BitmapDrawable(resource);
                        btn_food2.setBackground(drawable);
                    }
                });
                Glide.with(this).asBitmap().load(foodInfoList.get(27).getFoodImage()).into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        Drawable drawable = new BitmapDrawable(resource);
                        btn_food3.setBackground(drawable);
                    }
                });
                loopIndex++;
                break;
//            default:
//                textView.setText("Want some Junk Food?");
//                Glide.with(this).asBitmap().load(foodInfoList.get(0).getFoodImage()).into(new SimpleTarget<Bitmap>() {
//                    @Override
//                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
//                        Drawable drawable = new BitmapDrawable(resource);
//                        btn_food1.setBackground(drawable);
//                    }
//                });
//                Glide.with(this).asBitmap().load(foodInfoList.get(1).getFoodImage()).into(new SimpleTarget<Bitmap>() {
//                    @Override
//                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
//                        Drawable drawable = new BitmapDrawable(resource);
//                        btn_food2.setBackground(drawable);
//                    }
//                });
//                Glide.with(this).asBitmap().load(foodInfoList.get(2).getFoodImage()).into(new SimpleTarget<Bitmap>() {
//                    @Override
//                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
//                        Drawable drawable = new BitmapDrawable(resource);
//                        btn_food3.setBackground(drawable);
//                    }
//                });
//                loopIndex++;
//                break;
        }
    }

    private void saveListInSP() {
        SharedPreferences.Editor editor = getSharedPreferences("SystemSP", MODE_PRIVATE).edit();
        Gson gson = new Gson();
        String json = gson.toJson(pickedList);
        editor.putString("pickedList", json);
        editor.commit();
    }
}

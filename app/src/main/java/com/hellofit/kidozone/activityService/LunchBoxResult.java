package com.hellofit.kidozone.activityService;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hellofit.kidozone.R;
import com.hellofit.kidozone.common.RestClient;
import com.hellofit.kidozone.common.SystemUtil;
import com.hellofit.kidozone.entity.FoodInfo;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class LunchBoxResult extends AppCompatActivity {

    private int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lunch_result);

        Button backButton = (Button) findViewById(R.id.backButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LunchBoxResult.this, LunchBoxMatchPart.class);
                startActivityForResult(intent, 1);
            }
        });

        ImageView imageView1 = (ImageView) findViewById(R.id.imageFood1);
        ImageView imageView2 = (ImageView) findViewById(R.id.imageFood2);
        ImageView imageView3 = (ImageView) findViewById(R.id.imageFood3);
        ImageView imageView4 = (ImageView) findViewById(R.id.imageFood4);
        ImageView imageView5 = (ImageView) findViewById(R.id.imageFood5);
        ImageView imageView6 = (ImageView) findViewById(R.id.imageFood6);

        Bundle bundle = getIntent().getExtras();
        ArrayList<FoodInfo> resultList = (ArrayList<FoodInfo>) bundle.getSerializable("pickItemList");
        switch (resultList.size()) {
            case 1:
                score = 60;
                Glide.with(this).load(resultList.get(0).getFoodImage()).into(imageView1);
                if (resultList.get(0).getCategoryName().equals("junks")) {
                    score = score - 10;
                }
                break;
            case 2:
                score = 70;
                Glide.with(this).load(resultList.get(0).getFoodImage()).into(imageView1);
                Glide.with(this).load(resultList.get(1).getFoodImage()).into(imageView4);
                for (int i = 0; i < resultList.size(); i++) {
                    if (resultList.get(i).getCategoryName().equals("junks")) {
                        score = score - 10;
                    }
                    for (int j = i + 1; j < resultList.size(); j++) {
                        if (resultList.get(i).getCategoryName().equals(resultList.get(j).getCategoryName())) {
                            score = score - 10;
                        }
                    }
                }
                break;
            case 3:
                score = 80;
                Glide.with(this).load(resultList.get(0).getFoodImage()).into(imageView1);
                Glide.with(this).load(resultList.get(1).getFoodImage()).into(imageView4);
                Glide.with(this).load(resultList.get(2).getFoodImage()).into(imageView2);
                for (int i = 0; i < resultList.size(); i++) {
                    if (resultList.get(i).getCategoryName().equals("junks")) {
                        score = score - 10;
                    }
                    for (int j = i + 1; j < resultList.size(); j++) {
                        if (resultList.get(i).getCategoryName().equals(resultList.get(j).getCategoryName())) {
                            score = score - 10;
                        }
                    }
                }
                break;
            case 4:
                score = 90;
                Glide.with(this).load(resultList.get(0).getFoodImage()).into(imageView1);
                Glide.with(this).load(resultList.get(1).getFoodImage()).into(imageView4);
                Glide.with(this).load(resultList.get(2).getFoodImage()).into(imageView2);
                Glide.with(this).load(resultList.get(3).getFoodImage()).into(imageView5);
                for (int i = 0; i < resultList.size(); i++) {
                    if (resultList.get(i).getCategoryName().equals("junks")) {
                        score = score - 10;
                    }
                    for (int j = i + 1; j < resultList.size(); j++) {
                        if (resultList.get(i).getCategoryName().equals(resultList.get(j).getCategoryName())) {
                            score = score - 10;
                        }
                    }
                }
                break;
            case 5:
                score = 100;
                Glide.with(this).load(resultList.get(0).getFoodImage()).into(imageView1);
                Glide.with(this).load(resultList.get(1).getFoodImage()).into(imageView4);
                Glide.with(this).load(resultList.get(2).getFoodImage()).into(imageView2);
                Glide.with(this).load(resultList.get(3).getFoodImage()).into(imageView5);
                Glide.with(this).load(resultList.get(4).getFoodImage()).into(imageView3);
                for (int i = 0; i < resultList.size(); i++) {
                    if (resultList.get(i).getCategoryName().equals("junks")) {
                        score = score - 10;
                    }
                    for (int j = i + 1; j < resultList.size(); j++) {
                        if (resultList.get(i).getCategoryName().equals(resultList.get(j).getCategoryName())) {
                            score = score - 10;
                        }
                    }
                }
                break;
            case 6:
                score = 100;
                Glide.with(this).load(resultList.get(0).getFoodImage()).into(imageView1);
                Glide.with(this).load(resultList.get(1).getFoodImage()).into(imageView4);
                Glide.with(this).load(resultList.get(2).getFoodImage()).into(imageView2);
                Glide.with(this).load(resultList.get(3).getFoodImage()).into(imageView5);
                Glide.with(this).load(resultList.get(4).getFoodImage()).into(imageView3);
                Glide.with(this).load(resultList.get(5).getFoodImage()).into(imageView6);
                List<String> newList3 = new ArrayList<String>();
                for (int i = 0; i < resultList.size(); i++) {
                    if (resultList.get(i).getCategoryName().equals("junks")) {
                        score = score - 10;
                    }
                    for (int j = i + 1; j < resultList.size(); j++) {
                        if (resultList.get(i).getCategoryName().equals(resultList.get(j).getCategoryName())) {
                            score = score - 10;
                        }
                    }
                }
                break;
        }

        ImageView iv_result = (ImageView) findViewById(R.id.imageResult);
        TextView textView = (TextView) findViewById(R.id.textResultExplain);

        if (score >= 80) {
            Glide.with(this).load(R.drawable.fivestar).into(iv_result);
            textView.setText(("Great lunchbox!"));
        }
        if ((score < 80) && (score >= 60)) {
            Glide.with(this).load(R.drawable.fourstar).into(iv_result);
            textView.setText(("Good, maybe better next time."));
        }
        if (score < 60 && score >= 40) {
            Glide.with(this).load(R.drawable.threestar).into(iv_result);
            textView.setText(("Good, maybe better next time."));
        }
        if (score < 40 && score >= 20) {
            Glide.with(this).load(R.drawable.twostar).into(iv_result);
            textView.setText(("You can have a more healthy lunchbox."));
        }
        if (score < 20) {
            Glide.with(this).load(R.drawable.onestar).into(iv_result);
            textView.setText(("You can have a more healthy lunchbox."));
        }
    }

    private class getFoodPictureAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String result = "";
            try {
                result = RestClient.findFoodPic("35");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                SystemUtil su = new SystemUtil();
                Bitmap bitmap = su.decode(result);
                ImageView iv = (ImageView) findViewById(R.id.imageFood2);
                iv.setImageBitmap(bitmap);
            }
        }
    }


}
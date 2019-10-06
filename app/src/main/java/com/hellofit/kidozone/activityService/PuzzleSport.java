package com.hellofit.kidozone.activityService;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.hellofit.kidozone.R;
import com.hellofit.kidozone.puzzleGame.gameUtils.Utils;
import com.hellofit.kidozone.puzzleGame.dialog.SelectImageDialogSport;
import com.hellofit.kidozone.puzzleGame.dialog.SuccessDialog;
import com.hellofit.kidozone.puzzleGame.game.PuzzleGame;
import com.hellofit.kidozone.puzzleGame.ui.PuzzleLayout;

import androidx.appcompat.app.AppCompatActivity;

public class PuzzleSport extends AppCompatActivity implements PuzzleGame.GameStateListener {

    private PuzzleLayout puzzleLayout;
    private PuzzleGame puzzleGame;
    private ImageView srcImg;
    private TextView tvLevel;
    private SelectImageDialogSport selectImageDialog;
    private MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.puzzle_sport);
        initView();
        initListener();

        mp = MediaPlayer.create(PuzzleSport.this, R.raw.puzzle_sport);
        mp.start();

        Button backButton = (Button) findViewById(R.id.backButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.stop();
                Intent intent = new Intent(PuzzleSport.this, Puzzle.class);
                startActivityForResult(intent, 1);
            }
        });
    }

    private void initView() {
        puzzleLayout = (PuzzleLayout) findViewById(R.id.puzzleLayout);
        puzzleGame = new PuzzleGame(this, puzzleLayout);
        srcImg = (ImageView) findViewById(R.id.ivSrcImg);
        tvLevel = (TextView) findViewById(R.id.tvLevel);
        tvLevel.setText("Level：" + puzzleGame.getLevel());
        srcImg.setImageBitmap(Utils.readBitmap(getApplicationContext(), puzzleLayout.getRes(), 4));
    }

    private void initListener() {
        puzzleGame.addGameStateListener(this);


        if (selectImageDialog == null) {
            selectImageDialog = new SelectImageDialogSport();
            selectImageDialog.addItemClickListener(new SelectImageDialogSport.OnItemClickListener() {
                @Override
                public void itemClick(int postion, int res) {
                    puzzleGame.changeImage(res);
                    srcImg.setImageBitmap(Utils.readBitmap(getApplicationContext(), res, 4));
                }
            });
        }

        srcImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImageDialog.showDialog(getFragmentManager(), "dialog", 0);
            }
        });
    }

    public void addLevel(View view) {
        puzzleGame.addLevel();
    }

    public void reduceLevel(View view) {
        puzzleGame.reduceLevel();
    }

    public void changeImage(View view) {

    }

    @Override
    public void setLevel(int level) {
        tvLevel.setText("Level：" + level);
    }

    @Override
    public void gameSuccess(int level) {
        final SuccessDialog successDialog = new SuccessDialog();
        successDialog.show(getFragmentManager(), "successDialog");
        MediaPlayer mp = MediaPlayer.create(PuzzleSport.this, R.raw.yeah);
        mp.start();
        successDialog.addButtonClickListener(new SuccessDialog.OnButtonClickListener() {
            @Override
            public void nextLevelClick() {
                puzzleGame.addLevel();
                successDialog.dismiss();
            }

            @Override
            public void cancelClick() {
                successDialog.dismiss();
            }
        });

    }
}

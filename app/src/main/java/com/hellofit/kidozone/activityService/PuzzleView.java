package com.hellofit.kidozone.activityService;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.hellofit.kidozone.R;
import com.hellofit.kidozone.puzzleGame.gameUtils.Utils;
import com.hellofit.kidozone.puzzleGame.dialog.SelectImageDialogView;
import com.hellofit.kidozone.puzzleGame.dialog.SuccessDialog;
import com.hellofit.kidozone.puzzleGame.game.PuzzleGame;
import com.hellofit.kidozone.puzzleGame.ui.PuzzleLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class PuzzleView extends AppCompatActivity implements PuzzleGame.GameStateListener {

    private PuzzleLayout puzzleLayout;
    private PuzzleGame puzzleGame;
    private ImageView srcImg;
    private TextView tvLevel;
    private SelectImageDialogView selectImageDialog;
    private MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.puzzle_view);
        initView();
        initListener();

        mp = MediaPlayer.create(PuzzleView.this, R.raw.puzzle_sights);
        mp.start();

        Button backButton = (Button) findViewById(R.id.backButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                AlertDialog.Builder normalDialog = new AlertDialog.Builder(PuzzleView.this);
//              normalDialog.setIcon(R.drawable.icon_dialog);
                normalDialog.setTitle("").setMessage("You really want to quit now?");
                normalDialog.setPositiveButton("Yes, I'm leaving", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mp.stop();
                        Intent intent = new Intent(PuzzleView.this, Puzzle.class);
                        startActivityForResult(intent, 1);
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
            selectImageDialog = new SelectImageDialogView();
            selectImageDialog.addItemClickListener(new SelectImageDialogView.OnItemClickListener() {
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
        MediaPlayer mp = MediaPlayer.create(PuzzleView.this, R.raw.yeah);
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

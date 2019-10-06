package com.hellofit.kidozone.puzzleGame.game;

/***
 *  This class include function methods about the puzzle game
 *  @author Weiqiang Li
 */

import android.content.Context;
import android.widget.Toast;

import com.hellofit.kidozone.puzzleGame.ui.PuzzleLayout;

import androidx.annotation.NonNull;


public class PuzzleGame implements Game, PuzzleLayout.SuccessListener {

    private PuzzleLayout puzzleLayout;
    private GameStateListener stateListener;
    private Context context;

    public void addGameStateListener(GameStateListener stateListener) {
        this.stateListener = stateListener;
    }

    public PuzzleGame(@NonNull Context context, @NonNull PuzzleLayout puzzleLayout) {
        this.context = context.getApplicationContext();
        this.puzzleLayout = puzzleLayout;
        puzzleLayout.addSuccessListener(this);
    }

    private boolean checkNull() {
        return puzzleLayout == null;
    }


    @Override
    public void addLevel() {
        if (checkNull()) {
            return;
        }
        if (!puzzleLayout.addCount()) {
            Toast.makeText(context, "Is the highest level.", Toast.LENGTH_SHORT).show();
        }
        if (stateListener != null) {
            stateListener.setLevel(getLevel());
        }
    }

    @Override
    public void reduceLevel() {
        if (checkNull()) {
            return;
        }
        if (!puzzleLayout.reduceCount()) {
            Toast.makeText(context, "Is the lowest level.", Toast.LENGTH_SHORT).show();
        }
        if (stateListener != null) {
            stateListener.setLevel(getLevel());
        }
    }

    @Override
    public void changeImage(int res) {
        puzzleLayout.changeRes(res);
    }

    public int getLevel() {
        if (checkNull()) {
            return 0;
        }
        int count = puzzleLayout.getCount();
        return count - 3 + 1;
    }


    @Override
    public void success() {
        if (stateListener != null) {
            stateListener.gameSuccess(getLevel());
        }
    }

    public interface GameStateListener {
        public void setLevel(int level);

        public void gameSuccess(int level);
    }
}

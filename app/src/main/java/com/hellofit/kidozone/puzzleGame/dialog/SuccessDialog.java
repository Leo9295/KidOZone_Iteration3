package com.hellofit.kidozone.puzzleGame.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;


public class SuccessDialog extends DialogFragment {

    public OnButtonClickListener buttonClickListener;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle("Success!");
       // MediaPlayer mp = MediaPlayer.create(SuccessDialog.this, R.raw.wowauido);
       // mp.start();
        builder.setMessage("Do u want challenge next level?").
                setPositiveButton("Next Level", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (buttonClickListener != null) {
                            buttonClickListener.nextLevelClick();
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (buttonClickListener != null) {
                            buttonClickListener.cancelClick();
                        }
                    }
                });

        return builder.create();

    }

    public void addButtonClickListener(OnButtonClickListener listener) {
        this.buttonClickListener = listener;
    }

    public interface OnButtonClickListener {
        public void nextLevelClick();

        public void cancelClick();
    }
}

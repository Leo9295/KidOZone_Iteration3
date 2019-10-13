package com.hellofit.kidozone.puzzleGame.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

/***
 *  This class is the dialog when the user success finish the puzzle
 *
 *  Created by Weiqiang Li on 09/13/19.
 *  Copyright @ 2019 Weiqiang Li. All right reserved
 *
 *  @author Weiqiang Li
 *  @version 3.1
 */

public class SuccessDialog extends DialogFragment {

    public OnButtonClickListener buttonClickListener;

    /**
     * Create the dialog and set the some attributes like message
     * @param savedInstanceState
     * @return
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle("Success!");
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
